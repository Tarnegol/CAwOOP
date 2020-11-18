public class Cell {
    public int state; // State of the cell.
    int[] neighbours; // Array of states of the cell's neighbours.

    public Cell(int state) // init function to create Cell object.
    {
        this.state = state;
    }
}
