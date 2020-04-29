package com.joshvm.test;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

public class ResourceUtil {
	public static IResource getResourceByFile(File file){
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		return workspaceRoot.getFileForLocation(new Path(file.getAbsolutePath()));
	}
	
	public static void main(String[] args) {
		System.out.println(getResourceByFile(new File("C:\\work\\deployment\\workspaces\\JoshVMStudioCode\\com.joshvm.test\\src\\com\\joshvm\\test\\ResourceUtil.java")));
	}
}
