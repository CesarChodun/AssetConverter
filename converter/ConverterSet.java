package converter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 *	Data structure containing loaded converters.
 */
public class ConverterSet {
	
	protected HashMap<String, FileConvertPlugin> converters = new HashMap<String, FileConvertPlugin>();

	public ConverterSet() {
		
	}
	
	/**
	 *	Adds a converter to the set.
	 */
	public void addConverter(FileConvertPlugin plugin) {
		List<String> ext = plugin.getInputExtensions();
		for(String e : ext)
			converters.put(e, plugin);
	}
	
	public void addConverters(File f) {
		
		if(f.isFile())
			addConverter(f);
	}
	
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
	
	public void convert(File source, String dest) {
		if(source.isFile()) {
			convertSingleFile(source, dest);
			return;
		}
		
		for(File f : source.listFiles())
			convertSingleFile(f, dest);
	}
}
