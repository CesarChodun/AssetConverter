package core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginLoader<T extends Plugin> {
	
	public static String PLUGIN_INFO_LOCATION = "info";
	public static String PLUGIN_INFO_NAME = "PluginInfo";
	
	private HashMap<String, T> plugins = new HashMap<String, T>();
	
	private String createKey(T p) {
		if(p.getName() == null || p.getPath() == null)
			return null;
		return p.getPath();
	}
	
	/**
	 * Loads the class from <b>file</b>.
	 * @param file - File to load the class from.
	 * @param className - Path to the class within the <b>file</b>.
	 * @return The class obtained from <b>file</b>.
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	protected static Class<?> loadClass(File file, String className) throws MalformedURLException, ClassNotFoundException {
		return URLClassLoader.newInstance(new URL[] { file.toURI().toURL() }).loadClass(className);
	}
	
	protected String addPlugin(T p) {
		String k = createKey(p);
		plugins.put(k, p);
		return k;
	}
	
	protected T getPlugin(String path) {
		return plugins.get(path);
	}
	
	protected boolean containsPlugin(String path) {
		return plugins.containsKey(path);
	}
	
	/**
	 * Jar file which contains plugin classes.
	 */
	protected File source = null;
	
	/**
	 * Creates <code>PluginLoader</code> instance, 
	 * that will read classes from <b>source</b> file.
	 * @param source - Jar file that contains the plugin classes
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
	public boolean isPluginLoaded(String classPath) {
		return containsPlugin(classPath);
	}
	
	/**
	 * Obtains previously loaded class.
	 * @param name - Path of the class(Within <b>source</b> file).
	 * @return <b>Class</b> if it have been loaded, and null otherwise.
	 */
	public T getLoadedPlugin(String classPath){
		return getPlugin(classPath);
	}
	
	/**
	 * Loads the class. This method can be invoked if 
	 * the class have been loaded previously.
	 * @param name - Class path within <b>source</b> file.
	 * @return	Class obtained from <b>source</b> file.
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public T loadPlugin(String classPath) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		if(isPluginLoaded(classPath))
			return getLoadedPlugin(classPath);
		addPlugin((T) loadClass(source, classPath).newInstance());
		return getPlugin(classPath);
	}
	
	protected void loadPlugins(PluginSet set) {
		List<String> pluginPaths = set.getPlugins();
		
		for(String path : pluginPaths) {
			try {
				@SuppressWarnings("unchecked")
				T p = (T) loadClass(source, path).newInstance();

				if(p.getClass().isAssignableFrom(PluginSet.class))
					loadPlugins((PluginSet) p);
				else
					addPlugin(p);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		}
	}
	
	/**
	 * Loads all plugins from the file(assuming file contains file info class)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 */
	public void loadPlugins() throws InstantiationException, IllegalAccessException, MalformedURLException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		T p = (T) loadClass(source, PLUGIN_INFO_LOCATION + "." + PLUGIN_INFO_NAME).newInstance();
		
		System.out.println(PluginSet.class.isAssignableFrom(p.getClass()));
		
		if(PluginSet.class.isAssignableFrom(p.getClass()))
			loadPlugins((PluginSet) p);
		else
			addPlugin(p);
	}
	
	/**
	 * Returns the list of plugins that meets the requirements of the given {@linkplain PluginFilter}
	 * @param filter - filter that will be applied to the plugins
	 * @return List of the plugins that meet the requirements.
	 */
	public List<T> filterPlugins(PluginFilter filter){
		List<T> out = new ArrayList<T>();
		
		for(T p : plugins.values())
			if(filter.filter(p.getName(), p.getName(), p.getVersion()))
				out.add(p);
		
		return out;
	}
	
}
