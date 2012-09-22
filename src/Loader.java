import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.ImageIcon;

/**
 * Static helper class for loading resources
 * 
 * @author Joss
 * 
 */
public class Loader {

	// Constants
	
	// Members

	/**
	 * Gets the image at the given file path
	 * 
	 * @param dir
	 *            The path to the desired file
	 * @return Returns the id of the loaded image
	 */
	public static BufferedImage loadImage(String dir) {
		try {
			Image i = (Image) new ImageIcon(dir)
					.getImage();
			int w = i.getWidth(null);
			int h = i.getHeight(null);

			BufferedImage ii = new BufferedImage((int) w, (int) h,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = ii.createGraphics();
			g.setRenderingHints(Game.getRH());
			g.drawImage(i, 0, 0, w, h, null);

			//System.out.println("Loading \""+dir+"\" [Success]");
			return ii;
		} catch (Exception ex) {
			//ex.printStackTrace();
			//System.out.println("Loading \""+dir+"\" [Fail]");
		}
		return null;
	}
	
	public static void resize(int u, int v) {
		
		BufferedImage raw = loadImage(Stitch.getURL(u, v, 0));
		
		if (raw == null) {
			return;
		}
		
		RenderingHints rh = GPanel.getRH();
		
		for (int i = 1; i < Stitch.TILES.length; i++) {
			BufferedImage rs = new BufferedImage(Stitch.TILES[i],Stitch.TILES[i],BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = rs.createGraphics();
			g.setRenderingHints(rh);
			g.drawImage(raw,0,0,Stitch.TILES[i],Stitch.TILES[i],null);
			g.dispose();
			
			try {
				File dir = new File(Stitch.getURL(u, v, i));				
				if (!dir.exists()) {
					ImageIO.write(rs, "png", dir);
				} else {
					return;
				}
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
