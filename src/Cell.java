class Cell {
    int state; // State of the cell.
    Cell[] cellNeighbours; // Array of the cell's neighbours.
    int nulls;
    int[] pos;

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
