import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Tile extends DrawableImage {

	// Constants
	private static final int TILE_2048 = 0, TILE_1024 = 1, TILE_512 = 2, TILE_128 = 3, TILE_64 = 4, TILE_32 = 5, TILE_16 = 6;
		
	// Members
	private float m_scale = 1.0f, m_dx = 0, m_dy = 0;
	private boolean m_loaded = false;
	private int m_image = TILE_2048, m_u, m_v;
	
	public Tile(int u, int v, float x, float y, float w, float h) {
		super(null, x, y, w, h);
		m_u = u;
		m_v = v;
		
		if (Stitch.REBUILD) {
			Loader.resize(u,v);
		}
	}
	
	public void load() {
		
		
		if (!m_loaded || (m_loaded && (getSWidth() >= Stitch.TILES[m_image] || getSWidth() < Stitch.TILES[m_image+1]))) {
			
			if (m_image-1 >= 0 && getSWidth() >= Stitch.TILES[m_image]) {
				m_image--;
			}
			if (m_image+1 < Stitch.TILES.length-1 && getSWidth() < Stitch.TILES[m_image+1]) {
				m_image++;
			}
			
			if (m_image >= Stitch.TILES.length) {
				m_image = Stitch.TILES.length - 1;
			} else if (m_image < 0) {
				m_image = 0;
			}
			
			setImg(Loader.loadImage(Stitch.getURL(m_u, m_v, m_image)));
			m_loaded = true;
		}
	}
	
	public void unload() {
		setImg(null);
		m_loaded = false;
	}
	
	public void print() {
		
	}
	
	public int getU() {
		return m_u;
	}
	
	public int getV() {
		return m_v;
	}
	
	public void translate(float dx, float dy) {
		m_dx = dx;
		m_dy = dy;
	}
	
	public float getLeft() {
		return (m_x * m_scale) - (m_ox * m_scale) + (m_dx * m_scale);
	}
	
	public float getRight() {
		return ((m_x * m_scale) - (m_ox * m_scale) + (m_dx * m_scale)) + (m_w * m_scale);
	}
	
	public float getTop() {
		return (m_y * m_scale) - (m_oy * m_scale) + (m_dy * m_scale);
	}
	
	public float getBottom() {
		return ((m_y * m_scale) - (m_oy * m_scale) + (m_dy * m_scale)) + (m_h * m_scale);
	}
	
	public void setScale(float v) {
		m_scale = v;
	}
	
	public float getSWidth() {
		return m_w * m_scale;
	}
	
	public float getSHeight() {
		return m_h * m_scale;
	}
	
	protected void drawContent(Graphics2D g) {
		
		g.drawImage(getImg(), (int) Math.floor((m_x * m_scale) - (m_ox * m_scale) + (m_dx * m_scale) + (Stitch.RES_WIDTH / 2.0f)), (int) Math.floor((m_y * m_scale) - (m_oy * m_scale) + (m_dy * m_scale) + (Stitch.RES_HEIGHT / 2.0f)), (int) Math.ceil((m_w + 1) * m_scale),
				(int) Math.ceil((m_h + 1) * m_scale), null);
		
		//g.setColor(Color.GREEN);
		//g.drawString("Tile " + (m_v>=0?(m_v+1)+"s":-m_v+"n")+(m_u>=0?(m_u+1)+"e":-m_u+"w"),(int) ((m_x * m_scale) - (m_ox * m_scale) + (m_dx * m_scale)), (int) ((m_y * m_scale) - (m_oy * m_scale) + (m_dy * m_scale)));
		
		if (Stitch.OUTLINE) {
			g.setColor(Color.GREEN);
			g.drawRect((int) Math.floor((m_x * m_scale) - (m_ox * m_scale) + (m_dx * m_scale) + (Stitch.RES_WIDTH / 2.0f)), (int) Math.floor((m_y * m_scale) - (m_oy * m_scale) + (m_dy * m_scale) + (Stitch.RES_HEIGHT / 2.0f)), (int) Math.ceil((m_w + 1) * m_scale),
					(int) Math.ceil((m_h + 1) * m_scale));
		}
		
	}

}
