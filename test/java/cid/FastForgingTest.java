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

package cid;

import org.junit.Test;

import java.util.Properties;

public class FastForgingTest extends AbstractForgingTest {

    @Test
    public void fastForgingTest() {
        Properties properties = FastForgingTest.newTestProperties();
        properties.setProperty("cid.disableGenerateBlocksThread", "false");
        properties.setProperty("cid.enableFakeForging", "false");
        properties.setProperty("cid.timeMultiplier", "1000");
        AbstractForgingTest.init(properties);
        forgeTo(startHeight + 10, testForgingSecretPhrase);
        AbstractForgingTest.shutdown();
    }

}
