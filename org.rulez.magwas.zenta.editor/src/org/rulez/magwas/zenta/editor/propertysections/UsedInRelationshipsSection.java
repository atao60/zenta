/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.propertysections;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.editor.diagram.editparts.IZentamateEditPart;
import org.rulez.magwas.zenta.editor.ui.ZentamateLabelProvider;
import org.rulez.magwas.zenta.editor.ui.services.ViewManager;
import org.rulez.magwas.zenta.editor.views.tree.ITreeModelView;
import org.rulez.magwas.zenta.model.IZentamateElement;
import org.rulez.magwas.zenta.model.IRelationship;
import org.rulez.magwas.zenta.model.util.ZentamateModelUtils;



/**
 * Property Section for "Model Relations"
 * 
 * @author Phillip Beauvoir
 */
public class UsedInRelationshipsSection extends AbstractZentamatePropertySection {
    
    private static final String HELP_ID = "org.rulez.magwas.zenta.help.usedInRelationshipsSection"; //$NON-NLS-1$
    
    /**
     * Filter to show or reject this section depending on input value
     */
    public static class Filter implements IFilter {
        @Override
        public boolean select(Object object) {
            return !(object instanceof IRelationship) &&
                        (object instanceof IZentamateElement || object instanceof IZentamateEditPart);
        }
    }

    private IZentamateElement fZentamateElement;
    
    private TableViewer fTableViewer;
    private UpdatingTableColumnLayout fTableLayout;
    
    @Override
    protected void createControls(Composite parent) {
        createTableControl(parent);
    }
    
    private void createTableControl(Composite parent) {
        createLabel(parent, Messages.UsedInRelationshipsSection_0, ITabbedLayoutConstants.STANDARD_LABEL_WIDTH, SWT.NONE);
        
        // Table
        Composite tableComp = createTableComposite(parent, SWT.NONE);
        fTableLayout = (UpdatingTableColumnLayout)tableComp.getLayout();
        fTableViewer = new TableViewer(tableComp, SWT.BORDER | SWT.FULL_SELECTION);
        
        // Column
        TableViewerColumn column = new TableViewerColumn(fTableViewer, SWT.NONE, 0);
        fTableLayout.setColumnData(column.getColumn(), new ColumnWeightData(100, false));
        
        // On Mac shows alternate table row colours
        fTableViewer.getTable().setLinesVisible(true);
        
        // Help ID
        PlatformUI.getWorkbench().getHelpSystem().setHelp(fTableViewer.getTable(), HELP_ID);

        fTableViewer.setContentProvider(new IStructuredContentProvider() {
            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }
            
            public void dispose() {
            }
            
            public Object[] getElements(Object inputElement) {
                return ZentamateModelUtils.getRelationships((IZentamateElement)inputElement).toArray();
            }
        });
        
        fTableViewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                IRelationship relationship = (IRelationship)element;
                String name = ZentamateLabelProvider.INSTANCE.getLabel(relationship) + " ("; //$NON-NLS-1$
                name += ZentamateLabelProvider.INSTANCE.getLabel(relationship.getSource());
                name += " - "; //$NON-NLS-1$
                name += ZentamateLabelProvider.INSTANCE.getLabel(relationship.getTarget());
                name += ")"; //$NON-NLS-1$
                return name;
            }
            
            @Override
            public Image getImage(Object element) {
                return ZentamateLabelProvider.INSTANCE.getImage(element);
            }
        });
        
        fTableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                if(isAlive()) {
                    Object o = ((IStructuredSelection)event.getSelection()).getFirstElement();
                    if(o instanceof IZentamateElement) {
                        IRelationship relation = (IRelationship)o;
                        ITreeModelView view = (ITreeModelView)ViewManager.findViewPart(ITreeModelView.ID);
                        if(view != null) {
                            view.getViewer().setSelection(new StructuredSelection(relation), true);
                        }
                    }
                }
            }
        });
        
        fTableViewer.setSorter(new ViewerSorter());
    }
    
    @Override
    protected void setElement(Object element) {
        if(element instanceof IZentamateElement) {
            fZentamateElement = (IZentamateElement)element;
        }
        else if(element instanceof IAdaptable) {
            fZentamateElement = (IZentamateElement)((IAdaptable)element).getAdapter(IZentamateElement.class);
        }
        else {
            System.err.println("UsedInRelationshipsSection wants to display for " + element); //$NON-NLS-1$
        }
        
        refreshControls();
    }
    
    protected void refreshControls() {
        fTableViewer.setInput(fZentamateElement);
        fTableLayout.doRelayout();
    }
    
    @Override
    protected Adapter getECoreAdapter() {
        return null;
    }

    @Override
    protected EObject getEObject() {
        return fZentamateElement;
    }
    
    @Override
    public boolean shouldUseExtraSpace() {
        return true;
    }
}
