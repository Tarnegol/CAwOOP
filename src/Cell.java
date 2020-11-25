public class Cell {
    int state; // State of the cell.
    Cell[] cellNeighbours; // Array of the cell's neighbours.
    int nulls;
    int[] pos;

    Cell(int state) {
        this.state = state;
    }

    Cell(Cell cell) {
        state = cell.state;
        cellNeighbours = cell.cellNeighbours;
        nulls = cell.nulls;
        pos = cell.pos;
    }
}
