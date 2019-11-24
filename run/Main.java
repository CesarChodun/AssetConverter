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
		
		// The destination folder
		String output_folder = "";
		
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals(CONVERTERS_LOCATION_FLAG)) {
				// Adds converters to the converter set
				cs.addConverters(new File(args[i + 1]));
				i++;
			}
			else if(args[i].equals(OUTPUT_FOLDER_LOCATION_FLAG)) {
				// Sets the output folder
				output_folder = args[i + 1];
				i++;
			}
			else {
				// Converts the folder using loaded converters
				cs.convert(new File(args[i]), output_folder);
			}
		}
	}

}
