package core;

public abstract class Plugin {

	public Plugin() {}
	
	/**
	 * Name of the plugin(null for one time load plugin)
	 * @return Name of the plugin or null
	 */
	public abstract String getName();
	
	/**
	 * Type of the plugin(null for opaque type)
	 * @return Type of the Plugin or null
	 */
	public abstract String getType();
	
	/**
	 * Version of the plugin(null for opaque version)
	 * @return Version of the plugin or null
	 */
	public abstract String getVersion();
	
	/**
	 * Path to the plugin class(within the file) 
	 * @return Path to the plugin class
	 */
	public abstract String getPath();
}
