package core;

public abstract class PluginFilter extends Plugin{

	/**
	 * Returns true if plugin meets the requirements and false otherwise
	 * @param name - name of the plugin
	 * @param type - type of the plugin
	 * @param version - version of the plugin
	 * @return true if the plugin meets requirements
	 * @see {@link Plugin}
	 */
	public abstract boolean filter(String name, String type, String version);
}
