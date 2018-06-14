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

package cid.db;

import cid.Constants;
import cid.Cid;
import cid.util.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PrunableDbTable<T> extends PersistentDbTable<T> {

    protected PrunableDbTable(String table, DbKey.Factory<T> dbKeyFactory) {
        super(table, dbKeyFactory);
    }

    protected PrunableDbTable(String table, DbKey.Factory<T> dbKeyFactory, String fullTextSearchColumns) {
        super(table, dbKeyFactory, fullTextSearchColumns);
    }

    PrunableDbTable(String table, DbKey.Factory<T> dbKeyFactory, boolean multiversion, String fullTextSearchColumns) {
        super(table, dbKeyFactory, multiversion, fullTextSearchColumns);
    }

    @Override
    public final void trim(int height) {
        prune();
        super.trim(height);
    }

    protected void prune() {
        if (Constants.ENABLE_PRUNING) {
            try (Connection con = db.getConnection();
                 PreparedStatement pstmt = con.prepareStatement("DELETE FROM " + table + " WHERE transaction_timestamp < ? LIMIT " + Constants.BATCH_COMMIT_SIZE)) {
                pstmt.setInt(1, Cid.getEpochTime() - Constants.MAX_PRUNABLE_LIFETIME);
                int deleted;
                do {
                    deleted = pstmt.executeUpdate();
                    if (deleted > 0) {
                        Logger.logDebugMessage("Deleted " + deleted + " expired prunable data from " + table);
                    }
                    db.commitTransaction();
                } while (deleted >= Constants.BATCH_COMMIT_SIZE);
            } catch (SQLException e) {
                throw new RuntimeException(e.toString(), e);
            }
        }
    }

}
