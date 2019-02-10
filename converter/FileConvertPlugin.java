package converter;

import java.io.File;
import java.util.List;

import core.Plugin;

public abstract class FileConvertPlugin extends Plugin{

	/**
	 * A list of compatible file extensions.
	 * @return A list of extensions(eg. {"txt", "obj", "jpg"})
	 */
	public abstract List<String> getInputExtensions();
	
	/**
	 * Converts file(<b>source</b>) and saves it to the <b>destination</b>.
	 * @param source - File to be converted
	 * @param destination - Path to the folder where the converted file should be saved
	 */
	public abstract void convert(File source, String destination);
	
}
