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

package cid.peer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;

final class GetPeers extends PeerServlet.PeerRequestHandler {

    static final GetPeers instance = new GetPeers();

    private GetPeers() {}

    @Override
    JSONStreamAware processRequest(JSONObject request, Peer peer) {
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONArray services = new JSONArray();
        Peers.getAllPeers().forEach(otherPeer -> {
            if (!otherPeer.isBlacklisted() && otherPeer.getAnnouncedAddress() != null
                    && otherPeer.getState() == Peer.State.CONNECTED && otherPeer.shareAddress()) {
                jsonArray.add(otherPeer.getAnnouncedAddress());
                services.add(Long.toUnsignedString(((PeerImpl)otherPeer).getServices()));
            }
        });
        response.put("peers", jsonArray);
        response.put("services", services);         // Separate array for backwards compatibility
        return response;
    }

    @Override
    boolean rejectWhileDownloading() {
        return false;
    }

}
