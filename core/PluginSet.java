package core;

import java.util.List;

public abstract class PluginSet extends Plugin{
	
	/**
	 * List of paths to other plugins
	 * @return A list of paths within the file
	 */
	public abstract List<String> getPlugins();
	
}
