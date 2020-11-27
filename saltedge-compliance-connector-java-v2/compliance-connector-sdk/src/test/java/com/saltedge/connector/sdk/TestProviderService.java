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
package com.saltedge.connector.sdk;

import com.saltedge.connector.sdk.api.models.*;
import com.saltedge.connector.sdk.models.CardTransactionsPage;
import com.saltedge.connector.sdk.models.TransactionsPage;
import com.saltedge.connector.sdk.provider.ProviderServiceAbs;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TestProviderService implements ProviderServiceAbs {
    @Override
    public String getAccountInformationAuthorizationPageUrl(
            String sessionSecret,
            boolean userConsentIsRequired
    ) {
        return null;
    }

    @Override
    public List<AuthorizationType> getAuthorizationTypes() {
        return null;
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return null;
    }

    @Override
    public List<Account> getAccountsOfUser(@NotNull String userId) {
        return null;
    }

    @Override
    public TransactionsPage getTransactionsOfAccount(String userId, String accountId, @NotNull LocalDate fromDate, @NotNull LocalDate toDate, String fromId) {
        return null;
    }

    @Override
    public List<CardAccount> getCardAccountsOfUser(@NotEmpty String userId) {
        return null;
    }

    @Override
    public CardTransactionsPage getTransactionsOfCardAccount(@NotEmpty String userId, @NotEmpty String accountId, @NotNull LocalDate fromDate, @NotNull LocalDate toDate, String fromId) {
        return null;
    }

    @Override
    public String createPayment(@NotEmpty String paymentProduct, @NotEmpty String creditorIban, String creditorBic, @NotEmpty String creditorName, ParticipantAddress creditorAddress, @NotEmpty String debtorIban, String debtorBic, @NotEmpty String amount, @NotEmpty String currency, String description, @NotNull Map<String, String> extraData) {
        return null;
    }

    @Override
    public String createFPSPayment(@NotEmpty String paymentProduct, @NotEmpty String creditorBban, @NotEmpty String creditorSortCode, @NotEmpty String creditorName, ParticipantAddress creditorAddress, @NotEmpty String debtorBban, @NotEmpty String debtorSortCode, @NotEmpty String amount, @NotEmpty String currency, String description, @NotNull Map<String, String> extraData) {
        return null;
    }

    @Override
    public String getPaymentAuthorizationPageUrl(@NotEmpty String paymentId) {
        return null;
    }
}