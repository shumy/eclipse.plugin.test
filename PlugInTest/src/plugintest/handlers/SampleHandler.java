package plugintest.handlers;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {		
		test();
		return null;
	}
	
	private void test() {
		System.out.println("PERSPECTIVE");
		// Get the root of the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		// Loop over all projects
		for (IProject project : projects) {
			try {
				printProjectInfo(project);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void printProjectInfo(IProject project) throws CoreException, JavaModelException {
		System.out.println("Working in project " + project.getName());
		// Check if we have a Java project
		if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
			IJavaProject javaProject = JavaCore.create(project);
			printPackageInfos(javaProject);
		}
	}

	private void printPackageInfos(IJavaProject javaProject) throws JavaModelException {
		IPackageFragment[] packages = javaProject.getPackageFragments();
		for (IPackageFragment mypackage : packages) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				System.out.println("Package " + mypackage.getElementName());
				printICompilationUnitInfo(mypackage);

			}
		}
	}

	private void printICompilationUnitInfo(IPackageFragment mypackage) throws JavaModelException {
		System.out.println(mypackage);

		for(IClassFile file : mypackage.getClassFiles()) {
			System.out.println("Class File: " + file.getElementName());
		}
		
		for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
			System.out.println("Source file " + unit.getElementName());
			
			IJavaProject jProject = unit.getJavaProject();
			
			StringBuilder sb = new StringBuilder();
			sb.append(jProject.getOutputLocation().toString());
			sb.append(".");
			sb.append(mypackage.getElementName().replace('.', '/'));
			
			//for any ICompilationUnit => unit
			try {
				String[] classPaths = JavaRuntime.computeDefaultRuntimeClassPath(unit.getJavaProject());
				URL[] urls = new URL[classPaths.length];
				for (int i = 0; i < classPaths.length; i++){
					String path = "file:///" + classPaths[i];
					if(!path.endsWith(File.separator))
						path += File.separator;
					
					System.out.println("adding to URL classpath: " + path);
					urls[i] = new URL(path);
				}
				
				ClassLoader loader = new URLClassLoader(urls);
				
				String className = mypackage.getElementName() + "." + unit.getElementName().split("\\.")[0];
				
				System.out.println("loading class: " + className);
				Class<?> clazz = loader.loadClass(className);
				
				//test the new loaded class with Java reflection...
				Object classInstance = clazz.newInstance();		//invoke default constructor
				Method testMethod = clazz.getMethod("test");	//get method 'test' with no args
				testMethod.invoke(classInstance);				//in my test this invocation will return 'Hello World'
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			printIMethods(unit);
		}
	}
	
	

	private void printIMethods(ICompilationUnit unit) throws JavaModelException {
		IType[] allTypes = unit.getAllTypes();
		for (IType type : allTypes) {
			IMethod[] methods = type.getMethods();
			for (IMethod method : methods) {

				System.out.println("Method name " + method.getElementName());
				System.out.println("Signature " + method.getSignature());
				System.out.println("Return Type " + method.getReturnType());

			}
		}
	}
}
