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

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class APIProxyServletTest {

    @Test
    public void passwordFinder() {
        ByteBuffer postData = ByteBuffer.wrap("abcsecretPhrase=def".getBytes());
        Assert.assertEquals(3, APIProxyServlet.PasswordFinder.process(postData, new String[] { "secretPhrase=" }));
        postData.rewind();
        Assert.assertEquals(-1, APIProxyServlet.PasswordFinder.process(postData, new String[] { "mySecret=" }));
        postData.rewind();
        Assert.assertEquals(3, APIProxyServlet.PasswordFinder.process(postData, new String[] { "mySecret=", "secretPhrase=" }));
        postData.rewind();
        Assert.assertEquals(0, APIProxyServlet.PasswordFinder.process(postData, new String[] { "secretPhrase=", "abc" }));
        postData.rewind();
        Assert.assertEquals(16, APIProxyServlet.PasswordFinder.process(postData, new String[] { "def" }));
    }

}
