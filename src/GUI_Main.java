import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Main GUI window
 * 
 * @author Joss
 * 
 */
public class GUI_Main {

	// Constants

	// Members
	private JFrame m_frame;
	private GPanel m_canvas;
	private static boolean m_running = false;
	private long m_startTime, m_accTime, m_currentTime;
	int m_wait, m_timePassed;

	/**
	 * Create the application.
	 */
	public GUI_Main() {
		initialize();

		try {
			gameLoop();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			// Oh well... guess it will have to be ugly
		}
		
		m_frame = new JFrame();
		m_frame.setFocusable(false);
		m_frame.setResizable(false);
		m_frame.setLayout(null);
		m_frame.setPreferredSize(new Dimension(Stitch.RES_WIDTH, Stitch.RES_HEIGHT));
		m_frame.setBounds(100, 100, Stitch.RES_WIDTH, Stitch.RES_HEIGHT);
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setLocationRelativeTo(null);
		m_frame.setFont(new Font("Arial", Font.PLAIN, 24));
		m_frame.setTitle("xkcd");
		/*m_frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));*/

		m_running = true;

		m_canvas = new Game();
		m_wait = m_canvas.getWait();
		m_frame.add(m_canvas);
		m_canvas.grabFocus();
		
		m_frame.setVisible(true);
	}

	/**
	 * Game update loop
	 */
	private void gameLoop() {
		m_startTime = System.currentTimeMillis();
		m_accTime = m_startTime;

		while (m_running) {
			m_currentTime = System.currentTimeMillis();
			m_timePassed = (int) (m_currentTime - m_accTime);

			m_accTime += m_timePassed;

			m_canvas.tick(m_timePassed);

			try {
				int sleep = m_wait - (int) (System.currentTimeMillis() - m_currentTime);
				if (sleep > 0) {
					Thread.sleep(sleep);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * Forces a break from the game loop
	 */
	public static void stop() {
		m_running = false;
	}

}
