package core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class PluginLoader {
	
	private static String PATH_TO_CLASS_SEPARATOR = "/#/";
	
	private static HashMap<String, Class<?>> loadedClasses = new HashMap<String, Class<?>>();
	
	/**
	 * Creates unique name for the class.
	 * @param file	- File from which the class is obtained.
	 * @param name	- Path to the class within <b>file</b>.
	 * @return Unique name for the class.
	 */
	private static String getFullName(File file, String name) {
		return file.toString() + PATH_TO_CLASS_SEPARATOR + name;
	}
	
	/**
	 * Loads the plugin class from <b>file</b>.
	 * @param file - File to load the class from.
	 * @param className - Path to the class within the <b>file</b>.
	 * @return The class obtained from <b>file</b>.
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	protected static Class<?> loadPluginClass(File file, String className) throws MalformedURLException, ClassNotFoundException {
		return URLClassLoader.newInstance(new URL[] { file.toURI().toURL() }).loadClass(className);
	}
	
	/**
	 * Loads the class from <b>file</b>. Also saves it for future use.
	 * @param file - File to obtain class from.
	 * @param className - Path to the class within the <b>file</b>.
	 * @return The class obtained from <b>file</b>.
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	private static Class<?> loadClassFromFile(File file, String className) throws MalformedURLException, ClassNotFoundException{
		Class<?> out = loadPluginClass(file, className);
		
		loadedClasses.put(getFullName(file, className), out);
		return out;
	}
	
	/**
	 * Jar file containing the plugin classes.
	 */
	protected File source = null;
	
	/**
	 * Creates <code>PluginLoader</code> instance, 
	 * that will read classes from <b>source</b> file.
	 * @param source - Jar file that contains the plugin classes.
	 */
	public PluginLoader(File source) {
		this.source = source;
	}
	
	/**
	 * Tells whether class have been loaded from the 
	 * <b>source</b> file or not.
	 * @param name - Path to the class within <b>source</b> file.
	 * @return <b>True</b> if the class have been loaded 
	 * 	and <b>false</b> otherwise.
	 */
	public boolean isClassLoaded(String name) {
		return loadedClasses.containsKey(getFullName(source, name));
	}
	
	/**
	 * Obtains previously loaded class.
	 * @param name - Path of the class(Within <b>source</b> file).
	 * @return <b>Class</b> if it have been loaded, and null otherwise.
	 */
	public Class<?> getLoadedClass(String name){
		return loadedClasses.get(getFullName(source, name));
	}
	
	/**
	 * Loads the class. This method can be invoked if 
	 * the class have been loaded previously.
	 * @param name - Class path within <b>source</b> file.
	 * @return	Class obtained from <b>source</b> file.
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException
	 */
	public Class<?> loadClass(String name) throws MalformedURLException, ClassNotFoundException{
		if(isClassLoaded(name))
			return getLoadedClass(name);
		return loadClassFromFile(source, name);
	}
}
