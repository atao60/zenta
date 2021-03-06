/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.propertysections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.editor.diagram.commands.ConnectionLineColorCommand;
import org.rulez.magwas.zenta.editor.diagram.editparts.connections.IDiagramConnectionEditPart;
import org.rulez.magwas.zenta.editor.ui.ColorFactory;
import org.rulez.magwas.zenta.model.IZentaPackage;
import org.rulez.magwas.zenta.model.IDiagramModelConnection;
import org.rulez.magwas.zenta.model.ILockable;



/**
 * Property Section for a Line Color
 * 
 * @author Phillip Beauvoir
 */
public class LineColorSection extends AbstractZentaPropertySection {
    
    private static final String HELP_ID = "org.rulez.magwas.zenta.help.elementPropertySection"; //$NON-NLS-1$
    
    /*
     * Adapter to listen to changes made elsewhere (including Undo/Redo commands)
     */
    private Adapter eAdapter = new AdapterImpl() {
        @Override
        public void notifyChanged(Notification msg) {
            Object feature = msg.getFeature();
            // Color event (From Undo/Redo and here)
            if(feature == IZentaPackage.Literals.DIAGRAM_MODEL_COMPONENT__LINE_COLOR ||
                    feature == IZentaPackage.Literals.LOCKABLE__LOCKED) {
                refreshControls();
            }
        }
    };
    
    /**
     * Color listener
     */
    private IPropertyChangeListener colorListener = new IPropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent event) {
            if(isAlive()) {
                RGB rgb = fColorSelector.getColorValue();
                String newColor = ColorFactory.convertRGBToString(rgb);
                if(!newColor.equals(fDiagramModelConnection.getLineColor())) {
                    getCommandStack().execute(new ConnectionLineColorCommand(fDiagramModelConnection, newColor));
                }
            }
        }
    };
    
    private IDiagramModelConnection fDiagramModelConnection;

    private ColorSelector fColorSelector;
    private Button fDefaultColorButton;
    
    @Override
    protected void createControls(Composite parent) {
        createColorControl(parent);
        
        // Help ID
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);
    }
    
    private void createColorControl(Composite parent) {
        createLabel(parent, Messages.LineColorSection_0, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.CENTER);
        
        Composite client = createComposite(parent, 2);

        fColorSelector = new ColorSelector(client);
        GridData gd = new GridData(SWT.NONE, SWT.NONE, false, false);
        gd.widthHint = ITabbedLayoutConstants.BUTTON_WIDTH;
        fColorSelector.getButton().setLayoutData(gd);
        getWidgetFactory().adapt(fColorSelector.getButton(), true, true);
        fColorSelector.addListener(colorListener);

        fDefaultColorButton = new Button(client, SWT.PUSH);
        fDefaultColorButton.setText(Messages.LineColorSection_1);
        gd = new GridData(SWT.NONE, SWT.NONE, true, false);
        gd.minimumWidth = ITabbedLayoutConstants.BUTTON_WIDTH;
        fDefaultColorButton.setLayoutData(gd);
        getWidgetFactory().adapt(fDefaultColorButton, true, true); // Need to do it this way for Mac
        fDefaultColorButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(isAlive()) {
                    getCommandStack().execute(new ConnectionLineColorCommand(fDiagramModelConnection, null));
                }
            }
        });
    }
    
    @Override
    protected void setElement(Object element) {
        if(element instanceof IDiagramConnectionEditPart) {
            fDiagramModelConnection = (IDiagramModelConnection)((IDiagramConnectionEditPart)element).getModel();
            if(fDiagramModelConnection == null) {
                throw new RuntimeException("Diagram Connection Object was null"); //$NON-NLS-1$
            }
        }
        else {
            throw new RuntimeException("Should have been an IColoredEditPart"); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        if(fDiagramModelConnection == null) {
            return;
        }
        
        String colorValue = fDiagramModelConnection.getLineColor();
        RGB rgb = ColorFactory.convertStringToRGB(colorValue);
        if(rgb != null) {
            fColorSelector.setColorValue(rgb);
        }
        else {
            // Default color
            fColorSelector.setColorValue(new RGB(0, 0, 0));
        }
        
        boolean enabled = fDiagramModelConnection instanceof ILockable ? !((ILockable)fDiagramModelConnection).isLocked() : true;
        fColorSelector.setEnabled(enabled);
        fDefaultColorButton.setEnabled(colorValue != null && enabled);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        
        if(fColorSelector != null) {
            fColorSelector.removeListener(colorListener);
        }
    }

    @Override
    protected Adapter getECoreAdapter() {
        return eAdapter;
    }

    @Override
    protected EObject getEObject() {
        return fDiagramModelConnection;
    }
}
