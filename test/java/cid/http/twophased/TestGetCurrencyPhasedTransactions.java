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

import cid.BlockchainTest;
import cid.Constants;
import cid.VoteWeighting;
import cid.http.APICall;
import cid.util.Convert;
import cid.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TestGetCurrencyPhasedTransactions extends BlockchainTest {
    private static String currency = "17287739300802062230";

    static APICall phasedTransactionsApiCall() {
        return new APICall.Builder("getCurrencyPhasedTransactions")
                .param("currency", currency)
                .param("firstIndex", 0)
                .param("lastIndex", 20)
                .build();
    }

    private APICall byCurrencyApiCall(){
        return new TestCreateTwoPhased.TwoPhasedMoneyTransferBuilder()
                .votingModel(VoteWeighting.VotingModel.CURRENCY.getCode())
                .holding(Convert.parseUnsignedLong(currency))
                .minBalance(1, VoteWeighting.MinBalanceModel.CURRENCY.getCode())
                .fee(21 * Constants.ONE_CID)
                .build();
    }

    @Test
    public void simpleTransactionLookup() {
        JSONObject transactionJSON = TestCreateTwoPhased.issueCreateTwoPhased(byCurrencyApiCall(), false);
        JSONObject response = phasedTransactionsApiCall().invoke();
        Logger.logMessage("getCurrencyPhasedTransactionsResponse:" + response.toJSONString());
        JSONArray transactionsJson = (JSONArray) response.get("transactions");
        Assert.assertTrue(TwoPhasedSuite.searchForTransactionId(transactionsJson, (String) transactionJSON.get("transaction")));
    }

    @Test
    public void sorting() {
        for (int i = 0; i < 15; i++) {
            TestCreateTwoPhased.issueCreateTwoPhased(byCurrencyApiCall(), false);
        }

        JSONObject response = phasedTransactionsApiCall().invoke();
        Logger.logMessage("getCurrencyPhasedTransactionsResponse:" + response.toJSONString());
        JSONArray transactionsJson = (JSONArray) response.get("transactions");

        //sorting check
        int prevHeight = Integer.MAX_VALUE;
        for (Object transactionsJsonObj : transactionsJson) {
            JSONObject transactionObject = (JSONObject) transactionsJsonObj;
            int height = ((Long) transactionObject.get("height")).intValue();
            Assert.assertTrue(height <= prevHeight);
            prevHeight = height;
        }
    }

}
