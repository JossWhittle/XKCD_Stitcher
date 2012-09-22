import java.io.File;


public class Stitch {
	
	// Constants
	public static int MAP_W = 100, MAP_H = 100, MAP_X = -50, MAP_Y = -50, RES_WIDTH = 1920, RES_HEIGHT = 1080;
	public static String DIR = "";
	public static boolean REBUILD = false, OUTLINE = false, CROSS;
	
	// Members

	public static void main(String[] args) {
		new Stitch(args);
	}
	
	public Stitch(String[] args) {
		
		// Command Line Args
		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option help = parser.addBooleanOption('h', "help");
		CmdLineParser.Option path = parser.addStringOption('p', "path");
		CmdLineParser.Option xx = parser.addIntegerOption('x', "x");
		CmdLineParser.Option yy = parser.addIntegerOption('y', "y");
		CmdLineParser.Option width = parser.addIntegerOption('w', "width");
		CmdLineParser.Option height = parser.addIntegerOption('h', "height");
		CmdLineParser.Option rebuild = parser.addBooleanOption('r', "rebuild");
		CmdLineParser.Option outline = parser.addBooleanOption('o', "outline");
		CmdLineParser.Option cross = parser.addBooleanOption('c', "cross");

		// Parse Arguments
		try {
			parser.parse(args);
		} catch (CmdLineParser.OptionException e) {
			System.err.println(e.getMessage());
			//printUsage();
			System.exit(2);
		}

		// Retrieve Values

		// If -h || --help show help and exit
		if ((Boolean) parser.getOptionValue(help, Boolean.FALSE)) {
			//printUsage();
			System.exit(2);
		}
		
		MAP_X = (Integer)parser.getOptionValue(xx, MAP_X);
		MAP_Y = (Integer)parser.getOptionValue(yy, MAP_Y);
		MAP_W = (Integer)parser.getOptionValue(width, MAP_W);
		MAP_H = (Integer)parser.getOptionValue(height, MAP_H);
		REBUILD = (Boolean)parser.getOptionValue(rebuild, REBUILD);
		OUTLINE = (Boolean)parser.getOptionValue(outline, OUTLINE);
		CROSS = (Boolean)parser.getOptionValue(cross, CROSS);
		
		DIR = (String) parser.getOptionValue(path, null);
		if (DIR != null && MAP_W > 0 && MAP_H > 0) {
			
			for (int i = 1; i < TILES.length; i++) {
				File tmp = new File(DIR + "\\small\\" + TILES[i] + "\\");
				if (!tmp.exists()) {
					tmp.mkdirs();
				}
			}
			
			init();
		}
	}
	
	private void init() {
		System.out.println(MAP_X);
		System.out.println(MAP_Y);
		System.out.println(MAP_W);
		System.out.println(MAP_H);
		System.out.println(DIR);
		
		new GUI_Main();
	}
	
	public static final int[] TILES = {2048,1024,512,128,64,32,16};
	public static String getURL(int u, int v, int s) {
		return DIR + "\\" + (s > 0 ? "small\\" + TILES[s] + "\\" : "") + (v>=0?(v+1)+"s":-v+"n")+(u>=0?(u+1)+"e":-u+"w") + ".png";
	}
}
