package com.joshvm.test.handler;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.commands.ExpressionContext;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mtj.core.MTJCore;
import org.eclipse.mtj.core.persistence.PersistenceException;
import org.eclipse.mtj.core.sdk.device.IDevice;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.texteditor.ITextEditor;
import org.osgi.framework.Version;

import com.joshvm.test.Activator;
import com.joshvm.test.JdtAstUtil;

@SuppressWarnings({ "unused", "restriction" })
public class TestHandler2 extends AbstractHandler {
	public static final String COMMAND_ID = "com.joshvm.test.command2";
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// testDevice();
		// testPluginPreference();
		// testAst(event);
//		testExtension();
		// testInstallLocation();
		// testNLS();
		// IStructuredSelection selection = (IStructuredSelection)
		// HandlerUtil.getCurrentSelection(event);
		// Object object = selection.getFirstElement();
		// if(object instanceof ICompilationUnit){
		// ICompilationUnit compilationUnit = (ICompilationUnit) object;
		// try {
		// IPackageDeclaration[] packageDeclarations =
		// compilationUnit.getPackageDeclarations();
		// System.out.println(packageDeclarations[0].getElementName());
		// } catch (JavaModelException e) {
		// e.printStackTrace();
		// }
		// }
		testEditorPart(event);
		System.out.println("TestHandler2.execute()");
		return null;
	}
	
	public static final String HANDLER2_ENABLE_VARIABLE = "handler2_enable";
	
	public void setEnabled(Object evaluationContext) {
		if(evaluationContext instanceof ExpressionContext) {
			ExpressionContext expressionContext = (ExpressionContext) evaluationContext;
			Object expression = expressionContext.getVariable(HANDLER2_ENABLE_VARIABLE); 
			if(expression instanceof Boolean) {
				Boolean handler2_enable = (Boolean) expressionContext.getVariable(HANDLER2_ENABLE_VARIABLE);
				setBaseEnabled(handler2_enable);
//				throw new RuntimeException("--------");
			}
		}
	}

	@Override
	protected void fireHandlerChanged(HandlerEvent handlerEvent) {
		super.fireHandlerChanged(handlerEvent);
	}
	
	@Override
	public boolean isEnabled() {
		return super.isEnabled();
	}
	
	private void testEditorPart(ExecutionEvent event){
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		if(editorPart instanceof ITextEditor){
			ITextEditor textEditor = (ITextEditor) editorPart;
			org.eclipse.jface.text.TextSelection selection=  (TextSelection) textEditor.getSelectionProvider().getSelection();
			System.out.println(selection.getOffset());
		}
	}
	
	private void testExtension() {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = null;

		String extensionId = "org.eclipse.debug.core.launchConfigurationTypes";
		// elements =
		// extensionRegistry.getConfigurationElementsFor(extensionId);
		// for (IConfigurationElement element : elements) {
		// System.out.println(element.getAttribute("id") + " : " +
		// element.getNamespaceIdentifier());
		// }

		// extensionId = "org.eclipse.debug.ui.launchShortcuts";
		// elements =
		// Platform.getExtensionRegistry().getConfigurationElementsFor(extensionId);
		// for (IConfigurationElement element : elements) {
		// System.out.println(element.getAttribute("id") + " : " +
		// element.getNamespaceIdentifier());
		// }

		extensionId = "org.eclipse.debug.ui.launchShortcuts";
		elements = extensionRegistry.getConfigurationElementsFor("org.eclipse.debug.core.launchConfigurationTypes");
		Arrays.stream(elements).forEach((ele)->{
			System.out.println(ele.getAttribute("id")+" : "+ ele.getNamespaceIdentifier());
		});

	}

	private void testInstallLocation() {
		try {
			Location installLocation = Platform.getInstallLocation();
			URL url = installLocation.getURL();
			URI uri = url.toURI();
			System.out.println(new File(uri).exists());
		} catch (Exception e) {
		}
	}

	private void testNLS() {
		@SuppressWarnings("static-access")
		ImageDescriptor imageDescriptor = Activator.getDefault().imageDescriptorFromPlugin(Activator.PLUGIN_ID, "$nl$/icons/main.png");
		System.out.println(imageDescriptor);
		System.out.println(imageDescriptor.getImageData().width);
	}

	private void testAst(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		IAdaptable adaptable = (IAdaptable) ((IStructuredSelection) selection).getFirstElement();
		ICompilationUnit compilationUnit = (ICompilationUnit) adaptable;

		IProgressService progressService = HandlerUtil.getActiveWorkbenchWindow(event).getWorkbench().getProgressService();
		ProgressMonitorDialog monitorDialog = new ProgressMonitorDialog(HandlerUtil.getActiveShell(event));
		try {
			monitorDialog.run(true, true, (monitor) -> {
				try {
					IDocument document = new Document(compilationUnit.getSource());
					System.out.println("getNumberOfLines" + document.getNumberOfLines(57, 29));
					;
					CompilationUnit astUnit = JdtAstUtil.getCompilationUnit(compilationUnit, monitor);
					System.out.println(astUnit.getCommentList());
					astUnit.accept(new ASTVisitorDemo());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void testPluginPreference() {
		Activator activator = Activator.getDefault();
		// System.out.println(activator.getPreferenceStore());
		// System.out.println(activator.getStateLocation());
		// System.out.println(Platform.getConfigurationLocation().getURL());
		// System.out.println(Arrays.toString(Platform.getCommandLineArgs()));
		// System.out.println(Platform.getNLExtensions());
		// System.out.println(Platform.getNL());
		// System.out.println(Platform.getOS());
		// System.out.println(Platform.getOSArch());
		// System.out.println(Platform.getWS());
		// System.out.println(Platform.getLocation());
	}

	private void testDevice() {
		try {
			for (IDevice device : MTJCore.getDeviceRegistry().getAllDevices()) {
				System.out.println("TestHandler.testDevice()" + device.getIdentifier() + " -> " + device.getClasspath());
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}

	private static class ASTVisitorDemo extends ASTVisitor {
		public boolean visit(Javadoc node) {
			System.out.println(node.getStartPosition());
			System.out.println(node.getLength());
			return super.visit(node);
		}

		public boolean visit(BlockComment node) {
			System.out.println(node);
			return super.visit(node);
		}

		public boolean visit(LineComment node) {
			System.out.println(node);
			return super.visit(node);
		}
	}

	private static class CommentASTVisitor extends ASTVisitor {
		public boolean visit(BlockComment node) {
			System.out.println(node);
			return super.visit(node);
		}

		public boolean visit(LineComment node) {
			System.out.println(node);
			return super.visit(node);
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ASTParser parser = ASTParser.newParser(AST.JLS8); // 设置Java语言规范版本
		parser.setKind(ASTParser.K_COMPILATION_UNIT);

		Map<String, String> compilerOptions = JavaCore.getOptions();
		compilerOptions.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8); // 设置Java语言版本
		compilerOptions.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
		compilerOptions.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
		parser.setCompilerOptions(compilerOptions); // 设置编译选项

		char[] src = "class A { void method1(int b){;} }".toCharArray();
		parser.setSource(src);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null); // 这个参数是IProgessMonitor,用于GUI的进度显示,我们不需要，填个null.
																		// 返回值是AST的根结点

		System.out.println(cu); // 把AST直接输出看看啥样

		System.out.println(Version.valueOf("2.0.27").compareTo(Version.valueOf("1.0.1")));
	}
}
