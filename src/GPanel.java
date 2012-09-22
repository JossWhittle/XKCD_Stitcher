import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 * Abstract class for Game. Handles all the grunt work to ship each update and draw
 * @author Joss
 *
 */
public abstract class GPanel extends JPanel implements MouseListener,
		MouseMotionListener, KeyListener {

	// Constants
	public static final float SECOND = 1000, FPS = 30;

	public static final int FPS_X = 10, FPS_Y = 30;
	public static final Color BG = Color.gray, FG = Color.black;

	// Members
	protected static int WIDTH = Stitch.RES_WIDTH, HEIGHT = Stitch.RES_HEIGHT;
	protected static Rectangle SCREEN = new Rectangle(0, 0, WIDTH, HEIGHT);
	
	private int m_acc = 0, m_timeSec = 0, m_fCount = 0, m_fps = 0, m_wait;
	private boolean m_drawing = false, m_doneDrawing = true;

	private static RenderingHints m_rh;

	/**
	 * Initializes the panel
	 */
	protected void init() {
		m_wait = (int) (SECOND / FPS);
		buildRH();
		
		setIgnoreRepaint(true);
		
		// Positioning
		setLayout(null);
		setSize(WIDTH, HEIGHT);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBounds(0, 0, WIDTH, HEIGHT);
		
		// Attach Listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		// Render Settings
		setBackground(BG);
		setForeground(FG);
		setFocusable(true);
		setDoubleBuffered(true);
		grabFocus();          
        
	}

	/**
	 * Called from parent. Updates the game by the difference in time
	 * 
	 * @param timePassed
	 *            The amount of time since the last call
	 */
	public void tick(int timePassed) {
		if (m_doneDrawing) {
			m_doneDrawing = false;
			
			// Sync frames 
			timePassed += m_acc;
			m_acc = 0;

			// Calculate FPS
			m_timeSec += timePassed;
			m_fCount++;
			if (m_timeSec >= SECOND) {
				m_fps = m_fCount;
				m_fCount = 0;
				m_timeSec = 0;
			}

			// Update Game
			update(timePassed);

			// Redraw Game
			m_drawing = true;
			repaint();
		} else {
			// Keep track of overlap
			m_acc += timePassed;
		}
	}

	/**
	 * The update method to be implemented in each game
	 * 
	 * @param timePassed
	 *            The amount of time since the last call
	 */
	protected abstract void update(int timePassed);

	/**
	 * Overrides the default JPanel draw method
	 * 
	 * @param g
	 *            The graphics object passed by the system
	 */
	public void paint(Graphics g) {
		if (m_drawing) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHints(m_rh);

			// Clear Screen
			g.setColor(BG);
			g2.fill(SCREEN);

			// Draw Game
			draw(g2);

			// Draw FPS
			g2.setColor(FG);
			g2.drawString("FPS: " + getFPS(), FPS_X, FPS_Y);

			// Tidy Up
			g2.dispose();
			g.dispose();
			m_drawing = false;
			m_doneDrawing = true;
		}
	}

	/**
	 * The draw method to be implemented in each game
	 * 
	 * @param g
	 *            The graphics2d object passed by the system
	 */
	protected abstract void draw(Graphics2D g);

	/**
	 * Builds and sets the rendering hints for the engine
	 * 
	 * @return The rendering hints
	 */
	private static void buildRH() {
		m_rh = new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		m_rh.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON); 
		m_rh.put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		m_rh.put(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		m_rh.put(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	}

	/**
	 * Grabs the rendering hints
	 * 
	 * @return The render hints
	 */
	public static RenderingHints getRH() {
		return m_rh;
	}

	/**
	 * Gets the current FPS
	 * 
	 * @return The fps as an int
	 */
	protected int getFPS() {
		return m_fps;
	}

	/**
	 * Gets the wait time between frames in milliseconds
	 * 
	 * @return The wait time
	 */
	public int getWait() {
		return m_wait;
	}

	// Keyboard Handlers - Override them as needed

	public void keyReleased(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	// Mouse Handlers - Override them as needed

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {

	}
}
