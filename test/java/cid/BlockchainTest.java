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

import cid.util.Logger;
import cid.util.Time;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.Properties;

public abstract class BlockchainTest extends AbstractBlockchainTest {

    protected static Tester FORGY;
    protected static Tester ALICE;
    protected static Tester BOB;
    protected static Tester CHUCK;
    protected static Tester DAVE;

    protected static int baseHeight;

    protected static String forgerSecretPhrase = "aSykrgKGZNlSVOMDxkZZgbTvQqJPGtsBggb";
    protected static final String forgerAccountId = "CID-9KZM-KNYY-QBXZ-5TD8V";

    public static final String aliceSecretPhrase = "hope peace happen touch easy pretend worthless talk them indeed wheel state";
    private static final String bobSecretPhrase2 = "rshw9abtpsa2";
    private static final String chuckSecretPhrase = "eOdBVLMgySFvyiTy8xMuRXDTr45oTzB7L5J";
    private static final String daveSecretPhrase = "t9G2ymCmDsQij7VtYinqrbGCOAtDDA3WiNr";

    protected static boolean isCidInitialized = false;

    public static void initCid() {
        if (!isCidInitialized) {
            Properties properties = ManualForgingTest.newTestProperties();
            properties.setProperty("cid.isTestnet", "true");
            properties.setProperty("cid.isOffline", "true");
            properties.setProperty("cid.enableFakeForging", "true");
            properties.setProperty("cid.fakeForgingAccount", forgerAccountId);
            properties.setProperty("cid.timeMultiplier", "1");
            properties.setProperty("cid.testnetGuaranteedBalanceConfirmations", "1");
            properties.setProperty("cid.testnetLeasingDelay", "1");
            properties.setProperty("cid.disableProcessTransactionsThread", "true");
            properties.setProperty("cid.deleteFinishedShufflings", "false");
            properties.setProperty("cid.disableSecurityPolicy", "true");
            properties.setProperty("cid.disableAdminPassword", "true");
            AbstractBlockchainTest.init(properties);
            isCidInitialized = true;
        }
    }
    
    @BeforeClass
    public static void init() {
        initCid();
        Cid.setTime(new Time.CounterTime(Cid.getEpochTime()));
        baseHeight = blockchain.getHeight();
        Logger.logMessage("baseHeight: " + baseHeight);
        FORGY = new Tester(forgerSecretPhrase);
        ALICE = new Tester(aliceSecretPhrase);
        BOB = new Tester(bobSecretPhrase2);
        CHUCK = new Tester(chuckSecretPhrase);
        DAVE = new Tester(daveSecretPhrase);
    }

    @After
    public void destroy() {
        TransactionProcessorImpl.getInstance().clearUnconfirmedTransactions();
        blockchainProcessor.popOffTo(baseHeight);
    }

    public static void generateBlock() {
        try {
            blockchainProcessor.generateBlock(forgerSecretPhrase, Cid.getEpochTime());
        } catch (BlockchainProcessor.BlockNotAcceptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    public static void generateBlocks(int howMany) {
        for (int i = 0; i < howMany; i++) {
            generateBlock();
        }
    }
}
