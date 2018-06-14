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


import cid.BlockchainProcessor;
import cid.BlockchainTest;
import cid.Helper;
import cid.Cid;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

public abstract class AbstractHttpApiSuite {
    @BeforeClass
    public static void init() {
        BlockchainTest.initCid();
        Cid.getTransactionProcessor().clearUnconfirmedTransactions();
        Cid.getBlockchainProcessor().addListener(new Helper.BlockListener(), BlockchainProcessor.Event.BLOCK_GENERATED);
        Assert.assertEquals(0, Helper.getCount("unconfirmed_transaction"));
    }

    @AfterClass
    public static void shutdown() {
        Assert.assertEquals(0, Helper.getCount("unconfirmed_transaction"));
        Cid.shutdown();
    }
}
