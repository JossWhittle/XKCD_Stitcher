import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Tile extends DrawableImage {

	// Constants
	//private static final int TILE_2048 = 0, TILE_1024 = 1, TILE_512 = 2, TILE_128 = 3, TILE_64 = 4, TILE_32 = 5, TILE_16 = 6;
	private static final int TILE_2048 = 0, TILE_512 = 1, TILE_64 = 2, TILE_16 = 3;
	private static final Color[] COLOURS = {Color.GREEN, Color.BLUE, Color.ORANGE, Color.RED};
		
	// Members
	private BufferedImage[] CACHE;
	
	private float m_scale = 1.0f, m_dx = 0, m_dy = 0, m_left = 0, m_right = 0, m_top = 0, m_bottom = 0, m_sw = 0, m_sh = 0;
	private boolean m_loaded = false;
	private int m_image = TILE_2048, m_u, m_v;
	
	public Tile(int u, int v, float x, float y, float w, float h) {
		super(null, x, y, w, h);
		m_u = u;
		m_v = v;
		
		if (Stitch.REBUILD) {
			Loader.resize(u,v);
		}
		
		if (Stitch.CACHE > 0) {
			CACHE = new BufferedImage[Stitch.CACHE];
			int c = 0;
			for (int i = Stitch.CACHE - 1; i >= 0; i--) {
				int j = Stitch.TILES.length - 1 - i;
				CACHE[c] = Loader.loadImage(Stitch.getURL(u, v, j));
				c++;
			}
			
		}
	}
	
	public void load() {
		
		
		if (!m_loaded || (m_loaded && ((m_image-1 >= 0 && getSWidth() > Stitch.TILES[m_image]) || (m_image+1 < Stitch.TILES.length && getSWidth() < Stitch.TILES[m_image+1])))) {
			
			if (m_image-1 >= 0 && getSWidth() > Stitch.TILES[m_image]) {
				m_image--;
			} else if (m_image+1 < Stitch.TILES.length && getSWidth() < Stitch.TILES[m_image+1]) {
				m_image++;
			}
			
			if (m_image >= Stitch.TILES.length) {
				m_image = Stitch.TILES.length - 1;
			} else if (m_image < 0) {
				m_image = 0;
			}
			
			if (Stitch.CACHE > 0 && m_image >= (Stitch.TILES.length - Stitch.CACHE)) {
				setImg(CACHE[m_image - (Stitch.TILES.length - Stitch.CACHE)]);
			} else {
				setImg(Loader.loadImage(Stitch.getURL(m_u, m_v, m_image)));
			}
			
			m_loaded = true;
		}
	}
	
	public void unload() {
		setImg(null);
		m_loaded = false;
	}
	
	public int getU() {
		return m_u;
	}
	
	public int getV() {
		return m_v;
	}
	
	public void update(float dx, float dy, float scale) {
		m_dx = dx;
		m_dy = dy;
		m_scale = scale;
		
		m_sw = (m_w * m_scale);
		m_sh = (m_h * m_scale);
		
		m_left = (m_x * m_scale) + (m_dx * m_scale);
		m_right = (m_left) + (m_sw);
		m_top = (m_y * m_scale) + (m_dy * m_scale);
		m_bottom = (m_top) + (m_sh);
	}
	
	public float getLeft() {
		return m_left;
	}
	
	public float getRight() {
		return m_right;
	}
	
	public float getTop() {
		return m_top;
	}
	
	public float getBottom() {
		return m_bottom;
	}
	
	public float getSWidth() {
		return m_sw;
	}
	
	public float getSHeight() {
		return m_sh;
	}
	
	protected void drawContent(Graphics2D g) {
		
		if (m_loaded) {
			g.drawImage(
					getImg(), 
					(int) Math.floor(m_left + (Stitch.RES_WIDTH / 2.0f)), 
					(int) Math.floor(m_top + (Stitch.RES_HEIGHT / 2.0f)), 
					(int) Math.ceil((m_w + 1) * m_scale),
					(int) Math.ceil((m_h + 1) * m_scale), 
					null);
		}
		
		if (Stitch.OUTLINE) {
			if (m_loaded) {
				g.setColor(COLOURS[m_image]);
			} else {
				g.setColor(Color.pink);
			}
			g.drawRect(
					(int) Math.floor(m_left + (Stitch.RES_WIDTH / 2.0f)), 
					(int) Math.floor(m_top + (Stitch.RES_HEIGHT / 2.0f)), 
					(int) Math.ceil((m_w + 1) * m_scale),
					(int) Math.ceil((m_h + 1) * m_scale));
		}
		
	}

}
