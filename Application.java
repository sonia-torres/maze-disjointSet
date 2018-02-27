import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Maze frame. Creates a maze with the given number of rows and columns. Uses a
 * disjoint set.
 * 
 * @author Torre_000
 *
 */
public class Application extends JFrame implements ActionListener {
	private JPanel contentPanel;
	private MenuPanel menuPanel;
	private MazePanel mazePanel;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Application frame = new Application();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Application() {
		setTitle("Maze");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 700, 300);
		setSize(1000, 800);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPanel);

		/*
		 * Menu
		 */
		menuPanel = new MenuPanel();
		contentPanel.add(menuPanel, BorderLayout.NORTH);
		menuPanel.buttonCreateMaze.addActionListener(this);

		/*
		 * Maze
		 */
		mazePanel = new MazePanel();
		JScrollPane scrollPane = new JScrollPane(mazePanel);
		contentPanel.add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Actions when button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuPanel.buttonCreateMaze) {
			mazePanel.maze = new Maze(menuPanel.getNumRows(), menuPanel.getNumCol());
			mazePanel.setPreferredSize(mazePanel.maze.windowSize());
			mazePanel.started = true;
			mazePanel.redraw();
		}
	}
}
