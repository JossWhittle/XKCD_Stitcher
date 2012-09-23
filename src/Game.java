import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * This class represent the implementation of the game
 * 
 * @author Joss
 * 
 */
public class Game extends GPanel implements MouseWheelListener {

	// Constants
	private static final Color FLOOR = Color.BLACK, CEILING = Color.WHITE; // new Color(13,10,10)
	
	private static int[][] TILES;
	
	private static final float TILE_W = 2048, TILE_H = 2048, SPEED = 0.5f, SCALE_SPEED = 0.05f, SPEED_OUT = 0.45f, SPEED_IN = 0.01f, ZOOM_D = 500;
	
	// Members
	private boolean SPRINT = false, FORWARD = false, BACK = false, LEFT = false, RIGHT = false, CTRL = false, SHIFT = false, ZOOM = false, UPDATED = false;
	private int MOUSE_X = 50, MOUSE_Y = 0, m_rendered = 0,m_rx = 0, m_ry = 0, m_zt = 0;
	
	private ArrayList<Tile> m_tiles;
	private Tile m_selected = null;
	private float m_x = -1, m_y = 3, m_scale = 0.05f, m_zs = 0, o_x = 0, o_y = 0, o_scale = 0, m_zxs = 0, m_zxt = 0, m_zys = 0, m_zyt = 0, m_zst = 0;
	private String m_sel = "";
	
	public Game() {
		init();
		addMouseWheelListener(this);

		m_tiles = new ArrayList<Tile>();
		for (int v = -20; v < 20; v++) {
			for (int u = -50; u < 50; u++) {
				int x = (u * 2048);
				int y = (v * 2048);
				
				if (new File(Stitch.getURL(u,v,0)).exists()) {
					m_tiles.add(new Tile(u,v,x,y,2048,2048));
				}
			}
		}
	}

	protected void update(int timePassed) {
		// Update code HERE
		float dx = 0, dy = 0, ds = 1;
		
		if (ZOOM) {
			m_zt += timePassed;
			m_scale = Ease.linear(m_zt, m_zs, m_zst-m_zs, ZOOM_D);
			m_x = (Ease.linear(m_zt, m_zxs, m_zxt-m_zxs, ZOOM_D));
			m_y = (Ease.linear(m_zt, m_zys, m_zyt-m_zys, ZOOM_D));
			
			if (m_zt >= ZOOM_D) {
				m_scale = m_zst;
				m_x = m_zxt;
				m_y = m_zyt;
				ZOOM = false;
			}
		}
		
		if (CTRL || SCROLL_Y > 0) {
			ds -= SCALE_SPEED * (SCROLL_Y != 0 ? 2 : 1);
			SCROLL_Y = 0;
		}
		if (SHIFT || SCROLL_Y < 0) {
			ds += SCALE_SPEED * (SCROLL_Y != 0 ? 2 : 1);
			SCROLL_Y = 0;
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
		
		if (LEFT_MOUSE) {
			m_x += (MOUSE_DX / TILE_W) / m_scale;
			m_y += (MOUSE_DY / TILE_H) / m_scale;
			MOUSE_SX = MOUSE_X;
			MOUSE_SY = MOUSE_Y;
			MOUSE_DX = 0;
			MOUSE_DY = 0;
		}
		
		m_rendered = 0;
		m_sel = "";
		m_selected = null;
		
		float x = (m_x*TILE_W), y = (m_y*TILE_H);
		
		if (x != o_x || y != o_y || m_scale != o_scale) {
			UPDATED = true;
			
			for (int i = 0; i < m_tiles.size(); i++) {
				Tile t = m_tiles.get(i);
		
				t.update(x,y, m_scale);
							
				if ((t.getLeft() < (WIDTH / 2.0f) && t.getRight() >= -(WIDTH / 2.0f)) && (t.getTop() < (HEIGHT / 2.0f) && t.getBottom() >= -(HEIGHT / 2.0f))) {
					t.load();
					m_rendered++;
				} else {
					t.unload();
				}
				
				if (t.intesect(MOUSE_X, MOUSE_Y)) {
					m_sel = Stitch.uvCoord(t.getU(),t.getV());
					m_selected = t;
				}
			}
			
			m_rx = (int) Math.max((int) ((int)(WIDTH / (TILE_W * (m_scale))) * TILE_W), TILE_W);
			m_ry = (int) Math.max((int) ((int)(HEIGHT / (TILE_H * (m_scale))) * TILE_H), TILE_H);
			
			o_x = x;
			o_y = y;
			o_scale = m_scale;
		} else {
			UPDATED = false;
			
			for (int i = 0; i < m_tiles.size(); i++) {
				Tile t = m_tiles.get(i);
				if (t.intesect(MOUSE_X, MOUSE_Y)) {
					m_sel = Stitch.uvCoord(t.getU(),t.getV());
					m_selected = t;
				}
			}
		}
		
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
		g.drawString("CACHE: " + (Stitch.CACHE > 0 ? "ON" : "OFF"), FPS_X, FPS_Y + 120);
		g.drawString("Tile Width: " + m_tiles.get(0).getSWidth(), FPS_X, FPS_Y + 140);
		g.drawString("Updated: " + UPDATED, FPS_X, FPS_Y + 160);
		g.drawString("Selected: " + m_sel, FPS_X, FPS_Y + 180);
		
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
			m_zst = 1f;
			m_zxs = m_x;
			m_zys = m_y;
			m_zxt = m_x;
			m_zyt = m_y;
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
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			ZOOM = true;
			m_zt = 0;
			m_zs = m_scale;
			m_zst = 1f;
			m_zxs = m_x;
			m_zys = m_y;
			m_zxt = -(m_selected.getU() + ((MOUSE_X - (m_selected.getLeft() + (WIDTH / 2.0f))) / m_selected.getSWidth()));
			m_zyt = -(m_selected.getV() + ((MOUSE_Y - (m_selected.getTop() + (HEIGHT / 2.0f))) / m_selected.getSHeight()));
		}
	}
	
	private boolean LEFT_MOUSE = false;
	private float MOUSE_DX = 0, MOUSE_DY = 0, MOUSE_SX = 0, MOUSE_SY = 0;
	public void mousePressed(MouseEvent e) {
		LEFT_MOUSE = true;
		MOUSE_SX = e.getX();
		MOUSE_SY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		LEFT_MOUSE = false;
	}

	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
		MOUSE_DX = MOUSE_X - MOUSE_SX; 
		MOUSE_DY = MOUSE_Y - MOUSE_SY;
	}

	public void mouseMoved(MouseEvent e) {
		MOUSE_X = e.getX();
		MOUSE_Y = e.getY();
	}

	private int SCROLL_Y = 0;
	public void mouseWheelMoved(MouseWheelEvent e) {
		SCROLL_Y = e.getWheelRotation();
	}

}
