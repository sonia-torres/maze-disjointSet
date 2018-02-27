import javax.swing.*;

public class MenuPanel extends JPanel {
	public JTextField numRows;
	public JTextField numCol;
	public JButton buttonCreateMaze;

	public MenuPanel() {
		JLabel lblDimensionX = new JLabel("Number of Rows:");
		this.add(lblDimensionX, "1, 4");

		numRows = new JTextField(6);
		this.add(numRows, "2, 4, fill, fill");

		JLabel lblDimensionY = new JLabel("Number of Columns:");
		this.add(lblDimensionY, "1, 4");

		numCol = new JTextField(6);
		this.add(numCol, "2, 4, fill, fill");

		buttonCreateMaze = new JButton("Create Maze");
		this.add(buttonCreateMaze);

	}

	public int getNumRows() {
		if (numRows.getText().isEmpty())
			return 0;

		return Integer.parseInt(numRows.getText());
	}

	public int getNumCol() {
		if (numCol.getText().isEmpty())
			return 0;
		return Integer.parseInt(numCol.getText());
	}
}
