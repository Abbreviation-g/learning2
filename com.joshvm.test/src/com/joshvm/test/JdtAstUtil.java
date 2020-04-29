package com.joshvm.test;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class JdtAstUtil {

	/**
	 * 
	 * @param compilationUnit
	 * @param monitor
	 * @return
	 * @throws JavaModelException
	 */
	public static CompilationUnit getCompilationUnit(ICompilationUnit compilationUnit,IProgressMonitor monitor) throws JavaModelException {
		ASTParser astParser = ASTParser.newParser(AST.JLS8);
		astParser.setSource(compilationUnit);
		astParser.setKind(ASTParser.K_COMPILATION_UNIT);
		CompilationUnit result = (CompilationUnit) (astParser.createAST(monitor));
		return result;
	}
	
	
}
