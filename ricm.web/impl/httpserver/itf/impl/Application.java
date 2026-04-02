package httpserver.itf.impl;

import httpserver.itf.HttpRicmlet;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Application {
	private static final String posJarFiles = "APPS/";
	private static final String jarExtension = ".jar";
	private static int nInstances = 0;

	protected Application() {
		if (nInstances == 0) {
			nInstances++;
		} else {
			throw new IllegalStateException("only one instance of Application is allowed");
		}
	}

	private String nameToJar(String name) {
		return posJarFiles + name + jarExtension;
	}

	public HttpRicmlet getInstance(String className, String appName, ClassLoader parent)
			throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
			InstantiationException, IllegalAccessException {
		URLClassLoader ricmletLoader = new URLClassLoader(
				new URL[]{
						(new File(nameToJar(appName))).toURI().toURL()
				}, parent);
		try {
			Class ricmletClass = ricmletLoader.loadClass(className);
			Constructor ricmletConstructor = ricmletClass.getConstructor(new Class[]{});
			return (HttpRicmlet) ricmletConstructor.newInstance();
		} catch (ClassNotFoundException e) {
			throw e;
		}
	}
}
