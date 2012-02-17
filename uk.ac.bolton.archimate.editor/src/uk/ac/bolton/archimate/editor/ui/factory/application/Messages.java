/*******************************************************************************
 * Copyright (c) 2010-12 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor.ui.factory.application;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

    private static final String BUNDLE_NAME = "uk.ac.bolton.archimate.editor.ui.factory.application.messages"; //$NON-NLS-1$

    public static String ApplicationCollaborationUIProvider_0;

    public static String ApplicationCollaborationUIProvider_1;

    public static String ApplicationComponentUIProvider_0;

    public static String ApplicationComponentUIProvider_1;

    public static String ApplicationDataObjectUIProvider_0;

    public static String ApplicationFunctionUIProvider_0;

    public static String ApplicationFunctionUIProvider_1;

    public static String ApplicationInteractionUIProvider_0;

    public static String ApplicationInteractionUIProvider_1;

    public static String ApplicationInterfaceUIProvider_0;

    public static String ApplicationInterfaceUIProvider_1;

    public static String ApplicationServiceUIProvider_0;

    public static String ApplicationServiceUIProvider_1;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
