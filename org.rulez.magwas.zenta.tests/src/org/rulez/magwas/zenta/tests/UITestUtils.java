package org.rulez.magwas.zenta.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.rulez.magwas.zenta.editor.model.IEditorModelManager;
import org.rulez.magwas.zenta.editor.ui.services.ViewManager;
import org.rulez.magwas.zenta.editor.views.tree.ITreeModelView;
import org.rulez.magwas.zenta.model.IZentaModel;

public class UITestUtils {

	public static IZentaModel openTestModel(String modelName) {
		File file = new File("resources",modelName);
		IEditorModelManager.INSTANCE.openModel(file);
		return IEditorModelManager.INSTANCE.getModels().get(0);
	}

	public static void focusOnElement(EObject element) {
		List<Object> elements = new ArrayList<Object>();
		elements.add(element);
		ITreeModelView treeView = (ITreeModelView)ViewManager.showViewPart(ITreeModelView.ID, false);
		System.out.printf("view = %s\n", treeView);
        if(treeView != null) {
            treeView.getViewer().setSelection(new StructuredSelection(elements), true);
        }
	}

	public static IWorkbenchWindow getWorkbenchWindow() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		return workbench.getActiveWorkbenchWindow();
	}
	
	public static IViewPart openView(String viewId) throws WorkbenchException,
			PartInitException {
		String perspectiveId = "org.rulez.magwas.zenta.editor.perspectiveMain";
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = workbench.getActiveWorkbenchWindow();
		workbench.showPerspective(perspectiveId, wbw);
		IViewPart view = wbw.getActivePage().showView(viewId);
		return view;
	}

	public static IEditorPart openEditor(String editorId, IEditorInput input) throws WorkbenchException {
		String perspectiveId = "org.rulez.magwas.zenta.editor.perspectiveMain";
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow wbw = workbench.getActiveWorkbenchWindow();
		workbench.showPerspective(perspectiveId, wbw);
		IEditorPart view = wbw.getActivePage().openEditor(input, editorId);
		return view;
	}

}
