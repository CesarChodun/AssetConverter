# Asset Converter

Utility for automatic conversion of common asset files.

With the program you can create your file converters for sepcific file formats.
For example you can automate shader compilation across your project.

## Usage

<p>
The programm takes three directories <b>source</b>, <b>destination</b>, and <b>converters</b>. Then for every file in the source folder it applys a converter(acording to the file extension) from the <b>converters</b> package and writes the output to the <b>destination</b> folder.
It maintains the structure of the <b>source</b> folder when transferring files to the <b>destination</b>. And every file that does not have a corresponding converter is transferred to the <b>destination</b> folder as is. 
  
 The converters are loaded directly from the <b>converters</b> folder and must extend the 
<a href="https://github.com/CesarChodun/AssetConverter/blob/master/converter/FileConvertPlugin.java">FileConvertPlugin.java</a> class.

</p>
