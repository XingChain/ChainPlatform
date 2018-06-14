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

import cid.crypto.Crypto;
import cid.util.Time;
import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class ManualForgingTest extends AbstractForgingTest {

    @Test
    public void manualForgingTest() {
        Properties properties = ManualForgingTest.newTestProperties();
        properties.setProperty("cid.enableFakeForging", "true");
        properties.setProperty("cid.timeMultiplier", "1");
        AbstractForgingTest.init(properties);
        Assert.assertTrue("cid.fakeForgingAccount must be defined in cid.properties", Cid.getStringProperty("cid.fakeForgingAccount") != null);
        final byte[] testPublicKey = Crypto.getPublicKey(testForgingSecretPhrase);
        Cid.setTime(new Time.CounterTime(Cid.getEpochTime()));
        try {
            for (int i = 0; i < 10; i++) {
                blockchainProcessor.generateBlock(testForgingSecretPhrase, Cid.getEpochTime());
                Assert.assertArrayEquals(testPublicKey, blockchain.getLastBlock().getGeneratorPublicKey());
            }
        } catch (BlockchainProcessor.BlockNotAcceptedException e) {
            throw new RuntimeException(e.toString(), e);
        }
        Assert.assertEquals(startHeight + 10, blockchain.getHeight());
        AbstractForgingTest.shutdown();
    }

}
