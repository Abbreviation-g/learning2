package com.joshvm.test;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class TestStartup implements IStartup {

	@Override
	public void earlyStartup() {
		Display.getDefault().asyncExec(() -> {
			Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(new PartListener());
		});
		
		
//		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener, eventMask);;
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		registry.removeExtension(extension, token)
	}
	
	

	private class PartListener implements IPartListener {
		private IWorkbenchPart deactivatedPart;

		@Override
		public void partActivated(IWorkbenchPart activatedPart) {
			if (!(activatedPart instanceof IEditorPart) || !(deactivatedPart instanceof IEditorPart)) {
				return;
			}
			IEditorPart deactivatedEditorPart = (IEditorPart) deactivatedPart;
			if (!deactivatedEditorPart.isDirty()) {
				return;
			}
			Shell shell = activatedPart.getSite().getShell();
			if (MessageDialog.openConfirm(shell, "编辑器" + deactivatedEditorPart.getTitle() + "未保存", "是否保存")) {
				try {
					new ProgressMonitorDialog(shell).run(true, false, (monitor) -> shell.getDisplay().asyncExec(() -> deactivatedEditorPart.doSave(monitor)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {

		}

		@Override
		public void partClosed(IWorkbenchPart part) {

		}

		@Override
		public void partDeactivated(IWorkbenchPart deactivatedPart) {
			this.deactivatedPart = deactivatedPart;
		}

		@Override
		public void partOpened(IWorkbenchPart part) {

		}
	}
}
