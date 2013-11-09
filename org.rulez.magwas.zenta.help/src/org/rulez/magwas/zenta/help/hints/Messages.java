/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.help.hints;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "org.rulez.magwas.zenta.help.hints.messages"; //$NON-NLS-1$

    public static String HintsView_0;

    public static String HintsView_1;

    public static String HintsView_2;

    public static String HintsView_3;

    public static String HintsView_4;

    public static String IHintsView_0;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
