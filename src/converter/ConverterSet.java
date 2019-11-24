package converter;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import core.PluginLoader;

/**
 *	Data structure containing loaded converters.
 */
public class ConverterSet {
	
	protected HashMap<String, FileConvertPlugin> converters = new HashMap<String, FileConvertPlugin>();
	
	/**
	 * 
	 * @param plugin
	 */
	public void addConverter(FileConvertPlugin plugin) {
		List<String> ext = plugin.getInputExtensions();
		for(String e : ext)
			converters.put(e, plugin);
	}
	
	/**
	 * Adds a converter from a .class file.
	 * 
	 * @param file
	 */
	private void addConverterFromFile(File file) {
		PluginLoader<FileConvertPlugin> loader = new PluginLoader<>(file);
		
		String clasName = file.getName().split(".class")[0];
		
		try {
			loader.loadPlugin(clasName);
		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return;
		}
		
		addConverter(loader.getLoadedPlugin(clasName));
	}
	
	/**
	 * Performs a recursive search and loads any found plugins.
	 * 
	 * @param file
	 */
	public void addConverters(File file) {
		
		for (File f : file.listFiles())
			if (f.isFile())
				addConverterFromFile(f);
			else
				addConverters(f);
	}
	
	/**
	 * Removes an extension from the list of extensions to convert.
	 * 
	 * @param ext
	 */
	public void skipExtension(String ext) {
		converters.remove(ext);
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return "";
	    }
	    return name.substring(lastIndexOf);
	}
	
	private void convertSingleFile(File f, String d) {
		String ext = getFileExtension(f);
		FileConvertPlugin plugin = converters.get(ext);
		if(plugin == null)
			plugin = converters.get("");
		if(plugin != null)
			plugin.convert(f, d);
	}
	
	/**
	 * Converts a file using loaded converters.
	 * 
	 * @param source
	 * @param dest
	 */
	public void convert(File source, String dest) {
		if(source.isFile()) {
			convertSingleFile(source, dest);
			return;
		}
		
		for(File f : source.listFiles())
			convertSingleFile(f, dest);
	}
}
