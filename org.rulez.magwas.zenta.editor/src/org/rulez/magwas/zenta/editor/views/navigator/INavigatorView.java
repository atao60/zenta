/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.views.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.rulez.magwas.zenta.editor.ZentamateEditorPlugin;
import org.rulez.magwas.zenta.editor.ui.IZentamateImages;
import org.rulez.magwas.zenta.editor.views.IModelView;


/**
 * Interface for Navigator View
 * 
 * @author Phillip Beauvoir
 */
public interface INavigatorView extends IModelView {

    String ID = ZentamateEditorPlugin.PLUGIN_ID + ".navigatorView"; //$NON-NLS-1$
    String HELP_ID = "org.rulez.magwas.zenta.help.navigatorViewHelp"; //$NON-NLS-1$
    String NAME = Messages.INavigatorView_0;
    ImageDescriptor IMAGE_DESCRIPTOR = IZentamateImages.ImageFactory.getImageDescriptor(IZentamateImages.ICON_NAVIGATOR_16);
    
}
