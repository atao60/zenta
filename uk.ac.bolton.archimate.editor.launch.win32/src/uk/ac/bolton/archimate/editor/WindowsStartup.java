/*******************************************************************************
 * Copyright (c) 2010 Bolton University, UK.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 *******************************************************************************/
package uk.ac.bolton.archimate.editor;

import java.lang.reflect.Field;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Windows Startup code
 * 
 * @author Phillip Beauvoir
 */
public class WindowsStartup implements IStartup {

    @Override
    public void earlyStartup() {
        final IWorkbench workbench = PlatformUI.getWorkbench();
        final Display display = workbench.getDisplay();
        display.asyncExec(new Runnable() {
            public void run() {
                IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
                if(window != null) {
                    hookWindow(window);
                }
            }
        });
    }
    
    private void hookWindow(IWorkbenchWindow window) {
        Shell shell = window.getShell();
        if(shell != null && !shell.isDisposed()) {
            int hWnd = getShellWindowHandle(shell);
            logPrimaryWindow(hWnd);
            shell.addDisposeListener(new DisposeListener() {
                public void widgetDisposed(DisposeEvent e) {
                    WindowState.get(WindowState.WINDOW).delete();
                    e.display.asyncExec(new Runnable() {
                        public void run() {
                            checkRemainingWindow();
                        }
                    });
                }
            });
        }
    }
    
    private int getShellWindowHandle(Shell shell) {
        // This is the Win32 SWT specific field
        // return shell.handle;
        
        // We'll get it by reflection...
        try {
            Field f = Control.class.getDeclaredField("handle"); //$NON-NLS-1$
            return (Integer)f.get(shell);
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    private void checkRemainingWindow() {
        if(!PlatformUI.isWorkbenchRunning()) {
            return;
        }
        
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if(window == null) {
            IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
            if(windows == null || windows.length <= 0) {
                return;
            }
            window = windows[0];
        }
        hookWindow(window);
    }

    /**
     * Log the window that we are using
     * @param hWnd
     */
    private void logPrimaryWindow(int hWnd) {
        WindowState state = WindowState.get(WindowState.WINDOW);
        if(state.exists()) {
            state.delete();
        }
        state.getProperties().setProperty(WindowState.K_PRIMARY_WINDOW, String.valueOf(hWnd));
        state.saveProperties();
    }
}
