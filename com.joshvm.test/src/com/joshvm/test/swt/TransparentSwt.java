package com.joshvm.test.swt;

import java.io.File;
import java.net.MalformedURLException;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TransparentSwt {
	public static void main(String[] args) throws MalformedURLException {
		Display display = new Display();
		Shell shell = new Shell();
		shell.setLayout(new GridLayout());
		shell.setSize(200, 100);
		
		Composite mainComposite = new Composite(shell, SWT.NONE);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainComposite.setLayout(new GridLayout());
		System.out.println(new File("C:\\Users\\guo\\Pictures\\commodity.jpg").toURI());
		System.out.println(new File("C:\\Users\\guo\\Pictures\\commodity.jpg").toURI().toURL());
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(new File("C:\\Users\\guo\\Pictures\\commodity.jpg").toURI().toURL());
		shell.setBackgroundImage(imageDescriptor.createImage());
		shell.setBackgroundMode(SWT.INHERIT_FORCE);
		
		Label label = new Label(mainComposite, SWT.None);
		label.setText("Label");
		GridData gridData = new GridData();
		label.setLayoutData(gridData);
		
		shell.open();
		while(!shell.isDisposed()) {
			if(display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
