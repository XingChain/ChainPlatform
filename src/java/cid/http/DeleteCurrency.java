/*
 * Copyright © 2013-2016 The Cid Core Developers.
 * Copyright © 2018  Xing Chain DevOps.
 *
 * See the LICENSE.txt file at the top-level directory of this distribution
 * for licensing information.
 *
 * Unless otherwise agreed in a custom licensing agreement with XingChain B.V.,
 * no part of the Cid software, including this file, may be copied, modified,
 * propagated, or distributed except according to the terms contained in the
 * LICENSE.txt file.
 *
 * Removal or modification of this copyright notice is prohibited.
 *
 */

package cid.http;

import cid.Account;
import cid.Attachment;
import cid.Currency;
import cid.CidException;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class DeleteCurrency extends CreateTransaction {

    static final DeleteCurrency instance = new DeleteCurrency();

    private DeleteCurrency() {
        super(new APITag[] {APITag.MS, APITag.CREATE_TRANSACTION}, "currency");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) throws CidException {
        Currency currency = ParameterParser.getCurrency(req);
        Account account = ParameterParser.getSenderAccount(req);
        if (!currency.canBeDeletedBy(account.getId())) {
            return JSONResponses.CANNOT_DELETE_CURRENCY;
        }
        Attachment attachment = new Attachment.MonetarySystemCurrencyDeletion(currency.getId());
        return createTransaction(req, account, attachment);
    }
}
