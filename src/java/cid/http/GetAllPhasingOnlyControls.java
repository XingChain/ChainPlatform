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

import cid.AccountRestrictions.PhasingOnly;
import cid.db.DbIterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

import javax.servlet.http.HttpServletRequest;

public final class GetAllPhasingOnlyControls extends APIServlet.APIRequestHandler {

    static final GetAllPhasingOnlyControls instance = new GetAllPhasingOnlyControls();

    private GetAllPhasingOnlyControls() {
        super(new APITag[] {APITag.ACCOUNT_CONTROL}, "firstIndex", "lastIndex");
    }

    @Override
    protected JSONStreamAware processRequest(HttpServletRequest req) {
        int firstIndex = ParameterParser.getFirstIndex(req);
        int lastIndex = ParameterParser.getLastIndex(req);
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try (DbIterator<PhasingOnly> iterator = PhasingOnly.getAll(firstIndex, lastIndex)) {
            for (PhasingOnly phasingOnly : iterator) {
                jsonArray.add(JSONData.phasingOnly(phasingOnly));
            }
        }
        response.put("phasingOnlyControls", jsonArray);
        return response;
    }

}
