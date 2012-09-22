import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public abstract class Drawable extends SimpleDrawable {

	// Constants
	protected static final float DEFAULT_ALPHA = 1.0f, DEFAULT_ROTATION = 0.0f,
			DEFAULT_ORIGIN = 0.0f;
	protected static final AlphaComposite NULL_ALPHA = AlphaComposite
			.getInstance(AlphaComposite.SRC_OVER, 1.0f);

	// Members
	protected float m_rot, m_alpha;

	/**
	 * Abstract constructor
	 * 
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
	protected Drawable(float x, float y, float w, float h, float ox,
			float oy, float rot, float alpha) {
		super(x, y, w, h, ox, oy);
		m_rot = rot;
		m_alpha = alpha;
	}

	/**
	 * Intercepts the draw call to add functionality for rotation and alpha
	 * compositing
	 * 
	 * @param g
	 *            The graphics object to draw on
	 */
	public void draw(Graphics2D g) {
		if (m_rot != 0.0) {
			AffineTransform t = new AffineTransform();
			t.rotate(Math.toRadians(m_rot), m_x, m_y);
			g.setTransform(t);
		}
		if (m_alpha < 1.0) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) m_alpha));
		}

		drawContent(g);

		g.setTransform(NULL_TRANSFORM);
		g.setComposite(NULL_ALPHA);
	}

	/**
	 * All drawable objects must accept a draw call
	 * 
	 * @param g
	 *            The graphics object to draw on
	 */
	protected abstract void drawContent(Graphics2D g);

	/**
	 * Augments the alpha value by a given amount
	 * 
	 * @param v
	 *            The amount to change by
	 */
	public void fade(float v) {
		m_alpha += v;
		if (m_alpha > 1.0f) {
			m_alpha = 1.0f;
		} else if (m_alpha < 0.0f) {
			m_alpha = 0.0f;
		}
	}

	/**
	 * Rotates the sprite clockwise in degrees
	 * 
	 * @param r
	 *            The angle to rotate by
	 */
	public void rotCW(float r) {
		m_rot += r;
		if (m_rot > 360.0f)
			m_rot -= 360.0f;
	}

	/**
	 * Rotates the sprite counter-clockwise in degrees
	 * 
	 * @param r
	 *            The angle to rotate by
	 */
	public void rotCCW(float r) {
		m_rot -= r;
		if (m_rot < 0.0f)
			m_rot += 360.0f;
	}

	/**
	 * Sets Rot
	 * 
	 * @param v
	 *            The desired value
	 */
	public void setRot(float v) {
		m_rot = v;
	}

	/**
	 * Gets Rot
	 * 
	 * @return The value
	 */
	public float getRot() {
		return m_rot;
	}

	/**
	 * Sets Alpha
	 * 
	 * @param v
	 *            The desired value
	 */
	public void setAlpha(float v) {
		m_alpha = v;
	}

	/**
	 * Gets Alpha
	 * 
	 * @return The value
	 */
	public float getAlpha() {
		return m_alpha;
	}

}
