/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package org.rulez.magwas.zenta.editor.model.compatibility;

/**
 * Incompatible Model Version Exception
 * 
 * @author Phillip Beauvoir
 */
public class IncompatibleModelException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompatibleModelException(String message) {
        super(message);
    }
}