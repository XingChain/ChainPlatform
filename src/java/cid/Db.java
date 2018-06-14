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

import cid.db.BasicDb;
import cid.db.TransactionalDb;

public final class Db {

    public static final String PREFIX = Constants.isTestnet ? "cid.testDb" : "cid.db";
    public static final TransactionalDb db = new TransactionalDb(new BasicDb.DbProperties()
            .maxCacheSize(Cid.getIntProperty("cid.dbCacheKB"))
            .dbUrl(Cid.getStringProperty(PREFIX + "Url"))
            .dbType(Cid.getStringProperty(PREFIX + "Type"))
            .dbDir(Cid.getStringProperty(PREFIX + "Dir"))
            .dbParams(Cid.getStringProperty(PREFIX + "Params"))
            .dbUsername(Cid.getStringProperty(PREFIX + "Username"))
            .dbPassword(Cid.getStringProperty(PREFIX + "Password", null, true))
            .maxConnections(Cid.getIntProperty("cid.maxDbConnections"))
            .loginTimeout(Cid.getIntProperty("cid.dbLoginTimeout"))
            .defaultLockTimeout(Cid.getIntProperty("cid.dbDefaultLockTimeout") * 1000)
            .maxMemoryRows(Cid.getIntProperty("cid.dbMaxMemoryRows"))
    );

    public static void init() {
        db.init(new CidDbVersion());
    }

    static void shutdown() {
        db.shutdown();
    }

    private Db() {} // never

}
