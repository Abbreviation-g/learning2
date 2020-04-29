package com.joshvm.test.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.ListView;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.part.ViewPart;

@SuppressWarnings("restriction")
public class JoshTestViewPart extends ViewPart implements ISelectionListener, ISelectionChangedListener{

	public JoshTestViewPart() {
	}

	private List<Button> buttons = new ArrayList<Button>();
	private ListViewer listViewer;
	public void selectionChanged(SelectionChangedEvent event) {
		System.out.println("JoshTestViewPart.selectionChanged()");
	}

	@Override
	public void createPartControl(Composite parent) {
//		ISelectionProvider provider = getSite().getSelectionProvider();
//		if (provider == null) {
//			WorkbenchPage page = (WorkbenchPage) getSite().getPage();
//			MPart part = page.findPart(this);
//			if (part != null) {
//				part.getContext().get(ESelectionService.class)
//						.setSelection(StructuredSelection.EMPTY);
//			}
//		} 
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(listener);
		getSite().getPage().addSelectionListener(this);
		Group group = new Group(parent, SWT.NONE);
		group.setText("Test Group");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		group.setLayout(new GridLayout());
		
		Button button1 = new Button(group, SWT.PUSH);
		button1.setText("button1");
		button1.setLayoutData(new GridData(GridData.FILL_BOTH));
		Button button2 = new Button(group, SWT.PUSH);
		button2.setText("button2");
		button2.setLayoutData(new GridData(GridData.FILL_BOTH));
		Button button3 = new Button(group, SWT.PUSH);
		button3.setText("button3");
		button3.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		
		listViewer = new ListViewer(group);
		listViewer.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
		listViewer.setContentProvider(new ArrayContentProvider());
		listViewer.setLabelProvider(new LabelProvider());
		listViewer.setInput(new String[]{"1","2","3","4"});
		
		getSite().setSelectionProvider(listViewer);
	}

	@Override
	public void setFocus() {
		
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		System.out.println("JoshTestViewPart.selectionChanged()");
	}
	
//	private void pageSelectionChanged(SelectionChangedEvent event) {
//		// forward this change from a page to our site's selection provider
//		ListViewer provider = (ListViewer) getSite()
//				.getSelectionProvider();
//		provider.setsele
////		if (provider != null) {
////			provider.selectionChanged(event);
////		}
//	}
}
