package CAwOOP;

class Cell {
    protected int state; // State of the cell.
    protected Cell[] cellNeighbours; // Array of the cell's neighbours.
    protected int nulls;
    protected int[] pos;

    /**
     * Constructor of a Cell, defining it's state.
     *
     * @param state the state of the Cell.
     */
    Cell(int state) {
        this.state = state;
    }

    /**
     * Copy constructor of Cell.
     *
     * @param cell the cell to copy.
     */
    Cell(Cell cell) {
        state = cell.state;
        cellNeighbours = cell.cellNeighbours;
        nulls = cell.nulls;
        pos = cell.pos;
    }
}
