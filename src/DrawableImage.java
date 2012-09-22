import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Handles drawing an image to the screen
 * 
 * @author Joss
 * 
 */
public class DrawableImage extends Drawable {

	// Constants

	// Memebers
	private BufferedImage m_img;

	/**
	 * All drawable objects must accept a draw call
	 * 
	 * @param g
	 *            The graphics object to draw on
	 */
	protected void drawContent(Graphics2D g) {
		g.drawImage(m_img, (int) (m_x - m_ox), (int) (m_y - m_oy), (int) m_w,
				(int) m_h, null);
	}

	/**
	 * Abstract constructor
	 * 
	 * @param img
	 *            The image to render
	 * @param x
	 *            The x val
	 * @param y
	 *            The y val
	 * @param w
	 *            The width
	 * @param h
	 *            The height
	 * @param ox
	 *            The x origin
	 * @param oy
	 *            The y origin
	 * @param rot
	 *            The rotation in degrees
	 * @param alpha
	 *            The alpha value
	 */
	public DrawableImage(BufferedImage img, float x, float y, float w,
			float h, float ox, float oy, float rot, float alpha) {
		super(x, y, w, h, ox, oy, rot, alpha);
		m_img = img;
	}
	
	/**
	 * Abstract constructor
	 * 
	 * @param img
	 *            The image to render
	 * @param x
	 *            The x val
	 * @param y
	 *            The y val
	 * @param w
	 *            The width
	 * @param h
	 *            The height
	 * @param ox
	 *            The x origin
	 * @param oy
	 *            The y origin
	 */
	public DrawableImage(BufferedImage img, float x, float y, float w,
			float h, float ox, float oy) {
		this(img, x, y, w, h, ox, oy, DEFAULT_ROTATION, DEFAULT_ALPHA);
	}
	
	/**
	 * Abstract constructor
	 * 
	 * @param img
	 *            The image to render
	 * @param x
	 *            The x val
	 * @param y
	 *            The y val
	 * @param w
	 *            The width
	 * @param h
	 *            The height
	 * @param ox
	 *            The x origin
	 * @param oy
	 *            The y origin
	 */
	public DrawableImage(BufferedImage img, float x, float y, float w,
			float h) {
		this(img, x, y, w, h, DEFAULT_ORIGIN, DEFAULT_ORIGIN);
	}

	/**
	 * Gets Img
	 * 
	 * @return The value
	 */
	public BufferedImage getImg() {
		return m_img;
	}

	/**
	 * Sets Img
	 * 
	 * @param v
	 *            The desired value
	 */
	public void setImg(BufferedImage v) {
		m_img = v;
	}

}
