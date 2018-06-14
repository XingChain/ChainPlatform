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

package cid.http.twophased;

import cid.http.AbstractHttpApiSuite;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestCreateTwoPhased.class,
        TestGetVoterPhasedTransactions.class,
        TestApproveTransaction.class,
        TestGetPhasingPoll.class,
        TestGetAccountPhasedTransactions.class,
        TestGetAssetPhasedTransactions.class,
        TestGetCurrencyPhasedTransactions.class,
        TestTrustlessAssetSwap.class
})

public class TwoPhasedSuite extends AbstractHttpApiSuite {
    static boolean searchForTransactionId(JSONArray transactionsJson, String transactionId) {
        boolean found = false;
        for (Object transactionsJsonObj : transactionsJson) {
            JSONObject transactionObject = (JSONObject) transactionsJsonObj;
            String iteratedTransactionId = (String) transactionObject.get("transaction");
            if (iteratedTransactionId.equals(transactionId)) {
                found = true;
                break;
            }
        }
        return found;
    }
}

