import java.awt.Graphics;
import javax.swing.*;

public class MazePanel extends JPanel {
	public Maze maze;
	public boolean started;

	public MazePanel() {
		started = false;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(this.getGraphics());
		redraw();
	}

	/**
	 * calls the drawMaze method to paint the lines of the maze
	 */
	public void redraw() {
		if (started == false)
			return;
		super.paint(this.getGraphics());
		maze.drawMaze(this.getGraphics());
	}
}
