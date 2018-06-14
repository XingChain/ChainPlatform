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

package cid.http.votingsystem;

import cid.BlockchainTest;
import cid.Constants;
import cid.http.APICall;
import cid.http.votingsystem.TestCreatePoll.CreatePollBuilder;
import cid.util.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class TestCastVote extends BlockchainTest {
    private String getResult(JSONArray results, int index) {
        return (String) ((JSONObject) results.get(index)).get("result");
    }
    @Test
    public void validVoteCasting() {
        APICall apiCall = new CreatePollBuilder().build();
        String poll = TestCreatePoll.issueCreatePoll(apiCall, false);
        generateBlock();

        apiCall = new APICall.Builder("castVote")
                .param("secretPhrase", ALICE.getSecretPhrase())
                .param("poll", poll)
                .param("vote00", 1)
                .param("vote01", 0)
                .param("feeNQT", Constants.ONE_CID)
                .build();

        JSONObject response = apiCall.invoke();
        Logger.logMessage("voteCasting:" + response.toJSONString());
        Assert.assertNull(response.get("error"));
        generateBlock();

        apiCall = new APICall.Builder("getPollResult").param("poll", poll).build();
        JSONObject getPollResponse = apiCall.invoke();
        Logger.logMessage("getPollResultResponse:" + getPollResponse.toJSONString());
        JSONArray results = (JSONArray)getPollResponse.get("results");

        long ringoResult = Long.parseLong(getResult(results, 0));
        Assert.assertEquals(1, ringoResult);

        long paulResult = Long.parseLong(getResult(results, 1));
        Assert.assertEquals(0, paulResult);

        //John's result is empty by spec
        Assert.assertEquals("", getResult(results, 2));
    }

    @Test
    public void invalidVoteCasting() {
        APICall apiCall = new CreatePollBuilder().build();
        String poll = TestCreatePoll.issueCreatePoll(apiCall, false);
        generateBlock();

        apiCall = new APICall.Builder("castVote")
                .param("secretPhrase", ALICE.getSecretPhrase())
                .param("poll", poll)
                .param("vote1", 1)
                .param("vote2", 1)
                .param("vote3", 1)
                .param("feeNQT", Constants.ONE_CID)
                .build();

        JSONObject response = apiCall.invoke();
        Logger.logMessage("voteCasting:" + response.toJSONString());
        Assert.assertNotNull(response.get("error"));
    }


}