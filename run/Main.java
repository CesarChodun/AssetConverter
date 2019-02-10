package run;

import java.io.File;
import java.net.MalformedURLException;

import converter.ConverterSet;
import core.Plugin;
import core.PluginLoader;
import core.PluginSet;

public class Main {
	
	public static final String CONVERTERS_LOCATION_FLAG = "-c";
	public static final String OUTPUT_FOLDER_LOCATION_FLAG = "-o";
	
	

	public static void main(String[] args) {
		
		ConverterSet cs = new ConverterSet();
		
		String output_folder = "";
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals(CONVERTERS_LOCATION_FLAG)) {
				cs.addConverters(new File(args[i+1]));
				i++;
			}
			else if(args[i].equals(OUTPUT_FOLDER_LOCATION_FLAG)) {
				output_folder = args[i+1];
				i++;
			}
			else {
				cs.convert(new File(args[i]), output_folder);
			}
		}
		
//		PluginLoader loader = new PluginLoader(new File("testPlugin.jar"));
//		
//		try {
//			loader.loadPlugins();
//			Plugin set = loader.getLoadedPlugin("info.A");
//			System.out.println(set.getName());
//		} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		System.out.println("Success!");
	}

}
