import java.util.*;
import java.util.stream.IntStream;

public class Universe {
    public final int size; // Dimensions of the universe's grid.
    public final int states; // Number of possible cell states.

    public Cell[][][] universe; /// 3d array to record the universe over time.
    // 1st dimension is time, other 2 represent 2d universe grid.

    public boolean borders; // static borders or cyclic canvas
    public boolean moore; // Type of neighborhood - Moore or Von Neumann
    public int radius; // Radius of neighborhood

    public int timeSteps; // Maximum time steps

    public Canvas can; // Canvas used in function animate

    public Universe(int size, int states, boolean borders, int timeSteps) // init function to create Universe object.
    {
        this.size = size;
        this.states = states;
        this.timeSteps = timeSteps;
        this.borders = borders;
        this.universe = new Cell[timeSteps][size][size];

        can = new Canvas(0, this, 0);
        can.dispose();
    }

    public int[][][] toArray() // Convert Universe object to array of arrays of states.
    {
        int[][][] arr = new int[timeSteps][size][size];
        for (int t = 0; t < timeSteps; t++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    arr[t][i][j] = universe[t][i][j].state;
                }
            }
        }
        return arr;
    }

    public void setNeighborhood(String neighborhood, int radius)
    // Defines this.moore and this.radius - the neighborhood conditions of the universe.
    {
        if (neighborhood.equals("Von Neumann") || neighborhood.equals("von neumann")
                || neighborhood.equals("VN") || neighborhood.equals("vn")) {
            this.moore = false;
        } else if (neighborhood.equals("Moore") || neighborhood.equals("moore")
                || neighborhood.equals("M") || neighborhood.equals("m")) {
            this.moore = true;
        }

        this.radius = radius;
    }

    public void initialize() // Randomly generates an initial universe.
    {
        int[] stateArr = IntStream.rangeClosed(0, states - 1).toArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[0][i][j] = new Cell(stateArr[new Random().nextInt(stateArr.length)]);
            }
        }
    }

    public void initialize(int[] init)
    // Randomly generates an initial universe using only the states in state array init.
    // Allows user to randomly generate an initial universe using only specific starting states.
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[0][i][j] = new Cell(init[new Random().nextInt(init.length)]);
            }
        }
    }

    public void initialize(int[][] init) // Create a custom initial universe using 2d states array init.
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[0][i][j] = new Cell(init[i][j]);
            }
        }
    }

    public int[] getCellNeighbors(int time, int i, int j)
    // Returns the neighbors of cell (i, j) at time time using this.moore and this.radius defined by setNeighborhood.
    // Also sets the Cell.neighbours attribute to be the result.
    {
        int[] neighbors;
        int count = 0;
        if (moore) { // moore neighborhood search
            neighbors = new int[(2 * radius + 1) * (2 * radius + 1)];
            int k = -radius;
            if (borders) {
                int x;
                int y;
                for (int I = 0; I < (2 * radius + 1) * (2 * radius + 1); I++) {
                    int index = I % (2 * radius + 1);
                    if (index == 0 && I > 0) {
                        k += 1;
                    }
                    x = k + i;
                    y = index + j - radius;
                    if ((index == radius && k == 0) || ((x < 0) || (x >= size) || (y < 0) || (y >= size))) {
                        neighbors[I] = -1;
                        count++;
                        continue;
                    }
                    neighbors[I] = universe[time][x][y].state;
                }
            } else {
                for (int I = 0; I < (2 * radius + 1) * (2 * radius + 1); I++) {
                    int index = I % (2 * radius + 1);
                    if (index == 0 && I > 0) {
                        k += 1;
                    }
                    if (index == radius && k == 0) {
                        neighbors[I] = -1;
                        count++;
                        continue;
                    }
                    neighbors[I] = universe[time][(size + k + i) % size][(size + index + j - radius) % size].state;
                }
            }
        } else { // von neumann neighborhood search
            neighbors = new int[(radius + 1) * (radius + 1) + radius * radius];
            int m = 0;
            int I = 0;
            if (borders) {
                int x;
                int y;
                for (int k = -radius; k <= radius; k++) {
                    for (int l = -m; l <= m; l++) {
                        x = k + i;
                        y = j + l;
                        if ((k == 0 && l == 0) || ((x < 0) || (x >= size) || (y < 0) || (y >= size))) {
                            neighbors[I++] = -1;
                            count++;
                            continue;
                        }
                        neighbors[I++] = universe[time][(x) % size][(y) % size].state;
                    }
                    if (k < 0) {
                        m++;
                    } else {
                        m--;
                    }
                }
            } else {
                for (int k = -radius; k <= radius; k++) {
                    for (int l = -m; l <= m; l++) {
                        if (k == 0 && l == 0) {
                            neighbors[I++] = -1;
                            count++;
                            continue;
                        }
                        neighbors[I++] = universe[time][(size + i + k) % size][(size + j + l) % size].state;
                    }
                    if (k < 0) {
                        m++;
                    } else {
                        m--;
                    }
                }
            }
        }

        int[] neighbours = new int[neighbors.length - count];
        int n = 0;
        for (int neighbor : neighbors) {
            if (neighbor != -1) {
                neighbours[n] = neighbor;
                n++;
            }
        }
        universe[time][i][j].neighbours = neighbours;
        return neighbours;
    }

    public void advance(Rule rule, int time)
    // Creates the universe at time (time + 1) based on the universe at time time, using rule.
    {
    }

    public void animate(int size, int time) // Graphs/displays Universe at time time on can.
    {
        can.dispose();
        can = new Canvas(size, this, time);
    }

    public void animate(int size, int time, int[][] colors)
    // Same as other animate function, but with a custom color scheme.
    {
        can.dispose();
        can = new Canvas(size, this, time, colors);
    }

    public void create()
    // Generates the complete universe over time, based on the initial universe (created by the initialize function).
    {
    }

}
