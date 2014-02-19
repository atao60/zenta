package org.rulez.magwas.zenta.model;

import org.eclipse.jdt.annotation.Nullable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.rulez.magwas.zenta.model.handmade.util.Util;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static boolean running = false;

	@SuppressWarnings("null")
	static BundleContext getContext() {
		if(! running)
			throw new AssertionError();
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(@Nullable BundleContext bundleContext) throws Exception {
		Util.verifyNonNull(bundleContext);
		Activator.context = bundleContext;
		running = true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(@Nullable BundleContext bundleContext) throws Exception {
		running = false;
	}

}
