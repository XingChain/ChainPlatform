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

package cid.util;

import cid.http.APIEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.EnumSet;

public class APISetTest {
    @Test
    public void testBase64() {
        Logger.logMessage("empty enum: " + APIEnum.enumSetToBase64String(EnumSet.noneOf(APIEnum.class)));

        EnumSet<APIEnum> set = EnumSet.of(APIEnum.SET_API_PROXY_PEER, APIEnum.GET_ASSET_DIVIDENDS);
        String base64String = APIEnum.enumSetToBase64String(set);
        Logger.logMessage("base64String: " + base64String);

        set = APIEnum.base64StringToEnumSet(base64String);
        Assert.assertTrue(set.contains(APIEnum.SET_API_PROXY_PEER));
        for (APIEnum api : APIEnum.values()) {
            if (api != APIEnum.SET_API_PROXY_PEER && api != APIEnum.GET_ASSET_DIVIDENDS) {
                Assert.assertFalse(set.contains(api));
            }
        }
    }

}
