import java.io.File;


public class Stitch {
	
	// Constants
	public static int MAP_W = 82, RES_WIDTH = 1920, RES_HEIGHT = 1080, CACHE = 0;
	
	private static int[] WIDTHS = {1920,1280,800,600}, HEIGHTS = {1080,720,600,480};
	
	public static String DIR = "";
	public static boolean REBUILD = false, OUTLINE = false, CROSS = false;
	
	// Members

	public static void main(String[] args) {
		new Stitch(args);
	}
	
	public Stitch(String[] args) {
		
		// Command Line Args
		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option help = parser.addBooleanOption('h', "help");
		CmdLineParser.Option path = parser.addStringOption('p', "path");
		CmdLineParser.Option rebuild = parser.addBooleanOption('r', "rebuild");
		CmdLineParser.Option outline = parser.addBooleanOption('o', "outline");
		CmdLineParser.Option cross = parser.addBooleanOption('c', "cross");
		CmdLineParser.Option cache = parser.addIntegerOption('l', "load");
		
		CmdLineParser.Option[] res = {
				parser.addBooleanOption('1', "1920"),
				parser.addBooleanOption('2', "1280"),
				parser.addBooleanOption('3', "800"),
				parser.addBooleanOption('4', "640")
		};

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
		
		REBUILD = (Boolean)parser.getOptionValue(rebuild, REBUILD);
		OUTLINE = (Boolean)parser.getOptionValue(outline, OUTLINE);
		CROSS = (Boolean)parser.getOptionValue(cross, CROSS);
		CACHE = (Integer)parser.getOptionValue(cache, CACHE);
		
		for (int i = 0; i < res.length; i++) {
			if ((Boolean)parser.getOptionValue(res[i], false) || (i == res.length - 1)) {
				RES_WIDTH = WIDTHS[i];
				RES_HEIGHT = HEIGHTS[i];
				break;
			}
		}
		
		DIR = (String) parser.getOptionValue(path, null);
		if (DIR != null) {
			
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
		System.out.println(DIR);
		
		new GUI_Main();
	}
	
	public static final int[] TILES = {2048,512,64,16};
	public static String getURL(int u, int v, int s) {
		return DIR + "\\" + (s > 0 ? "small\\" + TILES[s] + "\\" : "") + (v>=0?(v+1)+"s":-v+"n")+(u>=0?(u+1)+"e":-u+"w") + ".png";
	}
}
