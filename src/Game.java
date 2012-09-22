import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * This class represent the implementation of the game
 * 
 * @author Joss
 * 
 */
public class Game extends GPanel {

	// Constants
	private static final Color FLOOR = Color.BLACK, CEILING = Color.WHITE; // new Color(13,10,10)
	
	private static int[][] TILES;
	
	private static final float TILE_W = 2048, TILE_H = 2048, SPEED = 0.5f, SCALE_SPEED = 0.05f, SPEED_OUT = 0.45f, SPEED_IN = 0.01f;
	
	// Members
	private boolean SPRINT = false, FORWARD = false, BACK = false, LEFT = false, RIGHT = false, CTRL = false, SHIFT = false, ZOOM = false;
	private int MOUSE_X = 50, MOUSE_Y = 0, m_rendered = 0,m_rx = 0, m_ry = 0, m_zt = 0;
	
	private ArrayList<Tile> m_tiles;
	private float m_x = -1, m_y = 3, m_scale = 0.3f, m_zs = 0;
	
	public Game() {
		init();

		//TILES = Loader.getTiles();
		
		m_tiles = new ArrayList<Tile>();
		for (int v = -20; v < 20; v++) {
			for (int u = -50; u < 50; u++) {
				int x = (u * 2048);
				int y = (v * 2048);
				
				if (new File(Stitch.getURL(u,v,0)).exists()) {
					m_tiles.add(new Tile(u,v,x,y,2049,2049));
				}
			}
		}
	}

	protected void update(int timePassed) {
		// Update code HERE
		float dx = 0, dy = 0, ds = 1;
		
		if (ZOOM) {
			m_zt += timePassed;
			m_scale = Ease.quintInOut(m_zt, m_zs, 1-m_zs, 2000);
			
			if (m_scale >= 1) {
				m_scale = 1;
				ZOOM = false;
			}
		}
		
		if (CTRL) {
			ds -= SCALE_SPEED;
		}
		if (SHIFT) {
			ds += SCALE_SPEED;
		}
		
		m_scale *= ds;
		
		if (m_scale > 1.25f) {
			m_scale = 1.25f;
		} else if (m_scale < (float)((float)WIDTH / (float)(Stitch.MAP_W * TILE_W))) {
			m_scale = (float)((float)WIDTH / (float)(Stitch.MAP_W * TILE_W));
		}
		
		float s = Ease.expoOut(m_scale, SPEED_OUT, SPEED_IN-SPEED_OUT, 1.25f);
		
		if (FORWARD) {
			dy += s; //SPEED;
		}
		if (BACK) {
			dy -= s;
		}
		
		if (LEFT) {
			dx += s;
		}
		if (RIGHT) {
			dx -= s;
		}
		
		m_x += dx;
		m_y += dy;
		
		//System.out.println(sx + ", " + sy + ", " + sx2 + ", " + sy2);
		
		m_rendered = 0;
		
		for (int i = 0; i < m_tiles.size(); i++) {
			Tile t = m_tiles.get(i);
			t.translate((m_x*TILE_W),(m_y*TILE_H));
			t.setScale(m_scale);
			
			if ((t.getLeft() < (WIDTH / 2.0f) && t.getRight() >= -(WIDTH / 2.0f)) && (t.getTop() < (HEIGHT / 2.0f) && t.getBottom() >= -(HEIGHT / 2.0f))) {
				t.load();
				//t.print();
				m_rendered++;
				
			} else {
				t.unload();
			}
		}
		
		m_rx = (int) Math.max((int) ((int)(WIDTH / (TILE_W * (m_scale))) * TILE_W), TILE_W);
		m_ry = (int) Math.max((int) ((int)(HEIGHT / (TILE_H * (m_scale))) * TILE_H), TILE_H);
		
		//System.out.println(c);
 		
	}

	protected void draw(Graphics2D g) {
		// Draw code HERE (No update calls!)
		g.setColor(CEILING);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(FLOOR);
		g.fillRect(0, 
				(int) Math.floor((m_y * TILE_H * m_scale) + (HEIGHT / 2.0f)), 
				WIDTH,
				(int) Math.ceil(HEIGHT - ((m_y * TILE_H * m_scale)  + (HEIGHT / 2.0f))));
		
		
		
		for (int i = 0; i < m_tiles.size(); i++) {
			m_tiles.get(i).draw(g);
		}
		
		if (Stitch.CROSS) {
			g.setColor(Color.orange);
			g.drawLine((int)(WIDTH/2.0f)-20, (int)(HEIGHT/2.0f), (int)(WIDTH/2.0f)+20, (int)(HEIGHT/2.0f));
			g.drawLine((int)(WIDTH/2.0f), (int)(HEIGHT/2.0f)-20, (int)(WIDTH/2.0f), (int)(HEIGHT/2.0f)+20);
		}
		
		g.setColor(GPanel.FG);
		g.drawString("Tiles being rendered: " + m_rendered, FPS_X, FPS_Y + 20);
		g.drawString("Approximate Resolution: " + m_rx + " x " + m_ry, FPS_X, FPS_Y + 40);
		g.drawString("Zoom: " + (int)(m_scale * 100) + "%", FPS_X, FPS_Y + 60);
		g.drawString("Outline (O): " + (Stitch.OUTLINE ? "ON" : "OFF"), FPS_X, FPS_Y + 80);
		g.drawString("Crosshair (C): " + (Stitch.CROSS ? "ON" : "OFF"), FPS_X, FPS_Y + 100);
	}
	
	/*
	 * Keyboard Handlers
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			SPRINT = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			FORWARD = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			BACK = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			LEFT = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			RIGHT = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			CTRL = false;
			ZOOM = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			SHIFT = false;
			ZOOM = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_O) {
			Stitch.OUTLINE = !Stitch.OUTLINE;
		}
		if (e.getKeyCode() == KeyEvent.VK_C) {
			Stitch.CROSS = !Stitch.CROSS;
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			ZOOM = true;
			m_zt = 0;
			m_zs = m_scale;
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			SPRINT = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			FORWARD = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			BACK = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			LEFT = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			RIGHT = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
			CTRL = true;
			ZOOM = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			SHIFT = true;
			ZOOM = false;
		}
	}
	
	private boolean LEFT_MOUSE = false;
	public void mousePressed(MouseEvent e) {
		LEFT_MOUSE = true;
	}

	public void mouseReleased(MouseEvent e) {
		LEFT_MOUSE = false;
	}

	private float MOUSE_MENU_X = 0, MOUSE_MENU_Y = 0;
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public void mouseMoved(MouseEvent e) {
		MOUSE_X = e.getX();
		MOUSE_Y = e.getY();
	}

}
