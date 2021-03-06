/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.canvas;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.rulez.magwas.zenta.model.util.LogUtil;

/**
 * Activator
 * 
 * @author Phillip Beauvoir
 */
public class CanvasEditorPlugin extends AbstractUIPlugin {
    
    public static final String PLUGIN_ID = "org.rulez.magwas.zenta.canvas"; //$NON-NLS-1$

    /**
     * The shared instance
     */
    public static CanvasEditorPlugin INSTANCE;
    
    /**
     * The File location of this plugin folder
     */
    private static File fPluginFolder;

    public CanvasEditorPlugin() {
        INSTANCE = this;
    }
    
    /**
     * @return The templates folder
     */
    public File getTemplatesFolder() {
        URL url = FileLocator.find(getBundle(), new Path("$nl$/templates"), null); //$NON-NLS-1$
        try {
            url = FileLocator.resolve(url);
        }
        catch(IOException ex) {
			LogUtil.logException(ex);
        }
        return new File(url.getPath()); 
    }
    
    /**
     * @return The File Location of this plugin
     */
    public File getPluginFolder() {
        if(fPluginFolder == null) {
            URL url = getBundle().getEntry("/"); //$NON-NLS-1$
            try {
                url = FileLocator.resolve(url);
            }
            catch(IOException ex) {
    			LogUtil.logException(ex);
            }
            fPluginFolder = new File(url.getPath());
        }
        
        return fPluginFolder;
    }

}
