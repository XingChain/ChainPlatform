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

package cid.env.service;

import cid.Cid;
import cid.env.LookAndFeel;

import javax.swing.*;

@SuppressWarnings("UnusedDeclaration")
public class CidService_ServiceManagement {

    public static boolean serviceInit() {
        LookAndFeel.init();
        new Thread(() -> {
            String[] args = {};
            Cid.main(args);
        }).start();
        return true;
    }

    // Invoked when registering the service
    public static String[] serviceGetInfo() {
        return new String[]{
                Cid.APPLICATION + " Server", // Long name
                "Manages the " + Cid.APPLICATION + " cryptographic currency protocol", // Description
                "true", // IsAutomatic
                "true", // IsAcceptStop
                "", // failure exe
                "", // args failure
                "", // dependencies
                "NONE/NONE/NONE", // ACTION = NONE | REBOOT | RESTART | RUN
                "0/0/0", // ActionDelay in seconds
                "-1", // Reset time in seconds
                "", // Boot Message
                "false" // IsAutomatic Delayed
        };
    }

    public static boolean serviceIsCreate() {
        return JOptionPane.showConfirmDialog(null, "Do you want to install the " + Cid.APPLICATION + " service ?", "Create Service", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean serviceIsLaunch() {
        return true;
    }

    public static boolean serviceIsDelete() {
        return JOptionPane.showConfirmDialog(null, "This " + Cid.APPLICATION + " service is already installed. Do you want to delete it ?", "Delete Service", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static boolean serviceControl_Pause() {
        return false;
    }

    public static boolean serviceControl_Continue() {
        return false;
    }

    public static boolean serviceControl_Stop() {
        return true;
    }

    public static boolean serviceControl_Shutdown() {
        return true;
    }

    public static void serviceFinish() {
        System.exit(0);
    }

}
