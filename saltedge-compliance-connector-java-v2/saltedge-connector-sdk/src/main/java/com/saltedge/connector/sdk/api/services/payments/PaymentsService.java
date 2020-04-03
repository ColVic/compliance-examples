/*
 * @author Constantin Chelban (constantink@saltedge.com)
 * Copyright (c) 2020 Salt Edge.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.saltedge.connector.sdk.api.services.payments;

import com.saltedge.connector.sdk.SDKConstants;
import com.saltedge.connector.sdk.api.err.HttpErrorParams;
import com.saltedge.connector.sdk.api.err.NotFound;
import com.saltedge.connector.sdk.api.mapping.CreatePaymentRequest;
import com.saltedge.connector.sdk.api.services.BaseService;
import com.saltedge.connector.sdk.callback.mapping.SessionUpdateCallbackRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PaymentsService extends BaseService {
    private static Logger log = LoggerFactory.getLogger(PaymentsService.class);

    @Async
    public void createPayment(@NotNull CreatePaymentRequest paymentRequest) {
        try {
            String paymentId = providerService.createPayment(
                    paymentRequest.paymentOrder.creditorAccount.iban,
                    paymentRequest.paymentOrder.creditorName,
                    paymentRequest.paymentOrder.debtorAccount.iban,
                    paymentRequest.paymentOrder.instructedAmount.amount,
                    paymentRequest.paymentOrder.instructedAmount.currency,
                    paymentRequest.paymentOrder.remittanceInformationUnstructured,
                    createExtraData(
                            paymentRequest.sessionSecret,
                            paymentRequest.returnToUrl,
                            paymentRequest.paymentOrder.endToEndIdentification
                    )
            );
            if (StringUtils.isEmpty(paymentId)) {
                callbackService.sendFailCallback(paymentRequest.sessionSecret, new NotFound.PaymentNotCreated());
            } else {
                SessionUpdateCallbackRequest params = new SessionUpdateCallbackRequest(
                        providerService.getPaymentAuthorizationPageUrl(paymentId),
                        SDKConstants.STATUS_REDIRECT
                );
                callbackService.sendUpdateCallback(paymentRequest.sessionSecret, params);
            }
        } catch (Exception e) {
            log.error("PaymentsService.createPayment:", e);
            RuntimeException failException = (e instanceof HttpErrorParams) ? (RuntimeException) e : new NotFound.PaymentNotCreated();
            callbackService.sendFailCallback(paymentRequest.sessionSecret, failException);
        }
    }

    private Map<String, String> createExtraData(
            @NotEmpty String sessionSecret,
            String returnToUrl,
            String endToEndIdentification) {
        HashMap<String, String> result = new HashMap<>();
        result.put(SDKConstants.KEY_SESSION_SECRET, sessionSecret);
        if (!StringUtils.isEmpty(returnToUrl)) result.put(SDKConstants.KEY_RETURN_TO_URL, returnToUrl);
        if (!StringUtils.isEmpty(endToEndIdentification)) result.put(SDKConstants.KEY_END_TO_END_IDENTIFICATION, endToEndIdentification);
        return result;
    }
}