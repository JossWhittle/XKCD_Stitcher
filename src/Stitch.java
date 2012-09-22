import java.io.File;


public class Stitch {
	
	// Constants
	public static int MAP_W = 82, RES_WIDTH = 1920, RES_HEIGHT = 1080;
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
		
		REBUILD = (Boolean)parser.getOptionValue(rebuild, REBUILD);
		OUTLINE = (Boolean)parser.getOptionValue(outline, OUTLINE);
		CROSS = (Boolean)parser.getOptionValue(cross, CROSS);
		
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
	
	public static final int[] TILES = {2048,1024,512,128,64,32,16};
	public static String getURL(int u, int v, int s) {
		return DIR + "\\" + (s > 0 ? "small\\" + TILES[s] + "\\" : "") + (v>=0?(v+1)+"s":-v+"n")+(u>=0?(u+1)+"e":-u+"w") + ".png";
	}
}
