/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.rulez.magwas.zenta.editor.ui.IZentamateImages;


/**
 * Diagram Figures Preferences Page
 * 
 * @author Phillip Beauvoir
 */
public class DiagramFiguresPreferencePage
extends PreferencePage
implements IWorkbenchPreferencePage, IPreferenceConstants
{
    private static String HELP_ID = "org.rulez.magwas.zenta.help.prefsFigures"; //$NON-NLS-1$
    
    private List<ImageChoice> fChoices = new ArrayList<ImageChoice>();
    
    private TableViewer fTableViewer;
    
    private class ImageChoice {
        String name;
        String key;
        int type = 0;
        Image img1, img2;
        
        ImageChoice(String name, String key, String image1, String image2) {
            this.name = name;
            this.key = key;
            
            img1 = IZentamateImages.ImageFactory.getImage(image1);
            img2 = IZentamateImages.ImageFactory.getImage(image2);
        }
        
        Image getImage() {
            return type == 0 ? img1 : img2;
        }
        
        void flip() {
            type = (type == 0) ? 1 : 0;
        }
    }
    
	/**
	 * Constructor
	 */
	public DiagramFiguresPreferencePage() {
		setPreferenceStore(Preferences.STORE);
		setDescription(Messages.DiagramFiguresPreferencePage_0);
	}

    @Override
    protected Control createContents(Composite parent) {
        // Help
        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, HELP_ID);
        
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        
        Composite client = new Composite(parent, SWT.NULL);
        client.setLayout(gridLayout);

        Composite client2 = new Composite(client, SWT.NULL);
        client2.setLayout(new TableColumnLayout());
        
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 80; // need this to set a smaller height
        gd.widthHint = 80;  // need this to stop it getting larger when the splitter is resized in the Prefs dialog
        client2.setLayoutData(gd);
        
        createTable(client2);
        
        setValues();
        
        fTableViewer.setInput(fChoices);
        
        return client;
    }
    
    
    private void createTable(Composite parent) {
        fTableViewer = new TableViewer(parent, SWT.BORDER);
        
        TableColumnLayout layout = (TableColumnLayout)parent.getLayout();
        TableViewerColumn column = new TableViewerColumn(fTableViewer, SWT.NONE);
        layout.setColumnData(column.getColumn(), new ColumnWeightData(100, false));
        
        fTableViewer.setContentProvider(new IStructuredContentProvider() {
            @Override
            public void dispose() {
            }

            @Override
            public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
            }

            @Override
            public Object[] getElements(Object inputElement) {
                return ((List<?>)inputElement).toArray();
            }
        });
        
        class TableLabelProvider extends LabelProvider {
            @Override
            public Image getImage(Object element) {
                return ((ImageChoice)element).getImage();
            }

            @Override
            public String getText(Object element) {
                return ((ImageChoice)element).name;
            }
        }
        
        fTableViewer.setLabelProvider(new TableLabelProvider());
        
        fTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                ImageChoice ic = (ImageChoice)((IStructuredSelection)event.getSelection()).getFirstElement();
                if(ic != null) {
                    ic.flip();
                    fTableViewer.update(ic, null);
                }
            }
        });
    }
    
    private void setValues() {
        for(ImageChoice choice : fChoices) {
            choice.type = getPreferenceStore().getInt(choice.key);
        }
    }
    
    @Override
    public boolean performOk() {
        for(ImageChoice choice : fChoices) {
            getPreferenceStore().setValue(choice.key, choice.type);
        }

        return true;
    }
    
    @Override
    protected void performDefaults() {
        for(ImageChoice choice : fChoices) {
            choice.type = 0;
        }
        
        fTableViewer.refresh();
        
        super.performDefaults();
    }

    public void init(IWorkbench workbench) {
    }
}
