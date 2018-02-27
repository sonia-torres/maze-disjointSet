import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

public class Maze {
	private DisjSets set;
	private int rows;
	private int columns;
	private Cell[][] grid;
	private int size;
	private Random random;
	public static final int CELL_WIDTH = 20; // maze square size
	public static final int MARGIN = 30; // buffer between window edge and maze

	private enum Direction {
		NORTH(0), SOUTH(1), EAST(2), WEST(3);

		private final int value;

		private Direction(int value) {
			this.value = value;
		}
	}

	/**
	 * Maze constructor.
	 * 
	 * @param rows
	 * @param columns
	 */
	public Maze(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		this.size = rows * columns;
		this.grid = new Cell[columns][rows];
		this.random = new Random();
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				grid[i][j] = new Cell();
			}
		}

		if (size > 0) {
			buildWalls();
			destroyWalls();
		}

	}

	/**
	 * Sets the initial values on each enclosure. All enclosures will have 4
	 * walls if wall = -1 then it is a border. if wall = size then it doesn't
	 * exist.
	 */
	public void buildWalls() {
		int counter = 0;
		// build all four walls on each enclosure
		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < columns; i++) {
				grid[i][j].walls[Direction.NORTH.value] = counter - columns;

				grid[i][j].walls[Direction.WEST.value] = counter - 1;

				grid[i][j].walls[Direction.SOUTH.value] = counter + columns;

				grid[i][j].walls[Direction.EAST.value] = counter + 1;
				counter++;
				/*
				 * Get rid of the borders by setting them equal to -1
				 */
				if (i == 0 && j == 0) {
					grid[i][j].walls[Direction.NORTH.value] = -1;
					grid[i][j].walls[Direction.WEST.value] = size;
				} else if (i == columns - 1 && j == 0) {
					grid[i][j].walls[Direction.NORTH.value] = -1;
					grid[i][j].walls[Direction.EAST.value] = -1;
				} else if (j == rows - 1 && i == 0) {
					grid[i][j].walls[Direction.WEST.value] = -1;
					grid[i][j].walls[Direction.SOUTH.value] = -1;
				} else if (j == rows - 1 && i == columns - 1) {
					grid[i][j].walls[Direction.EAST.value] = size;
					grid[i][j].walls[Direction.SOUTH.value] = -1;
				} else if (j == 0) {
					grid[i][j].walls[Direction.NORTH.value] = -1;
				} else if (i == 0) {
					grid[i][j].walls[Direction.WEST.value] = -1;
				} else if (j == rows - 1) {
					grid[i][j].walls[Direction.SOUTH.value] = -1;
				} else if (i == columns - 1) {
					grid[i][j].walls[Direction.EAST.value] = -1;
				}
			}
		}
	}

	/**
	 * Destroys a shared wall by randomly selecting two cells
	 */
	public void destroyWalls() {

		set = new DisjSets(size);

		for (int i = 0; i < size; i++) {
			set.find(i);
		}

		while (set.find(0) != set.find(size - 1)) // will stop when there is a
													// path from beginning to
													// end
		{
			int randRow = random.nextInt(rows);
			int randCol = random.nextInt(columns);
			int wall = random.nextInt(4);
			int randCell1 = (randRow * columns) + randCol;
			int randCell2 = grid[randCol][randRow].walls[wall];

			// share wall
			if (randCell2 > -1 && randCell2 < size) {
				// not same set
				if (set.find(randCell1) != set.find(randCell2)) {
					// destroy wall on cell1
					grid[randCol][randRow].walls[wall] = size;

					// destroy wall on cell2
					if (wall == Direction.NORTH.value || wall == Direction.EAST.value) {
						grid[randCell2 % columns][randCell2 / columns].walls[wall + 1] = size;
					}
					if (wall == Direction.SOUTH.value || wall == Direction.WEST.value) {
						grid[randCell2 % columns][randCell2 / columns].walls[wall - 1] = size;
					}

					// now cells are in the same set
					set.union(set.find(randCell1), set.find(randCell2));
				}
			}
		}

	}

	/**
	 * Draws each wall of each cell in the grid
	 * 
	 * @param g
	 *            Graphics
	 */
	public void drawMaze(Graphics g) {

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if (grid[i][j].walls[Direction.NORTH.value] < size) {
					g.drawLine((i * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN), ((i + 1) * CELL_WIDTH + MARGIN),
							(j * CELL_WIDTH + MARGIN));
				}

				if (grid[i][j].walls[Direction.SOUTH.value] < size) {
					g.drawLine(i * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH + MARGIN, (i + 1) * CELL_WIDTH + MARGIN,
							(j + 1) * CELL_WIDTH + MARGIN);
				}

				if (grid[i][j].walls[Direction.EAST.value] < size) {
					g.drawLine((i + 1) * CELL_WIDTH + MARGIN, j * CELL_WIDTH + MARGIN, (i + 1) * CELL_WIDTH + MARGIN,
							(j + 1) * CELL_WIDTH + MARGIN);
				}

				if (grid[i][j].walls[Direction.WEST.value] < size) {
					g.drawLine(i * CELL_WIDTH + MARGIN, j * CELL_WIDTH + MARGIN, i * CELL_WIDTH + MARGIN,
							(j + 1) * CELL_WIDTH + MARGIN);
				}
			}
		}
	}

	public Dimension windowSize() {
		return new Dimension(columns * CELL_WIDTH + MARGIN * 2, rows * CELL_WIDTH + MARGIN * 2);
	}
}
