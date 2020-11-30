package CAwOOP;

import java.util.*;

public class Universe {
    protected final int size; // Size of the Universe.
    protected final int states; // Number of possible cell states.

    protected Cell[][][] universe; /// 3d Cell array to record the universe (2d) over time.

    private final boolean borders; // Determines if the Universe is cyclic or has borders.
    private Neighborhood neighborhood; // The Universe's neighborhood.
    private int radius; // Radius of the neighborhood.

    private Rule rule; // The Universe's advancement rule.

    protected final int timeSteps; // The number of time steps to generate.

    private CanvasFrame frame;

    /**
     * Constructor with random conditions.
     */
    public Universe() {
        Random random = new Random();
        this.size = 10 + random.nextInt(100);
        this.states = 2 + random.nextInt(5);
        this.timeSteps = 100;
        this.borders = true;
        this.universe = new Cell[timeSteps][size][size];
    }

    /**
     * Constructor with custom conditions.
     *
     * @param size      the size of the universe.
     * @param states    the number of possible states for cells in the universe.
     * @param borders   boolean to decide if the universe is cyclic or has borders.
     * @param timeSteps the number of time steps to calculate the advancement of the universe.
     */
    public Universe(int size, int states, boolean borders, int timeSteps) {
        this.size = size;
        this.states = states;
        this.timeSteps = timeSteps;
        this.borders = borders;
        this.universe = new Cell[timeSteps][size][size];
    }

    /**
     * Randomly generates the starting state of the Universe.
     */
    public void initialize() {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[0][i][j] = new Cell(rand.nextInt(states));
                universe[0][i][j].pos = new int[]{0, i, j};
            }
        }
    }

    /**
     * Randomly generates the starting state of the Universe using only certain states.
     *
     * @param init int array of the states used in the generation.
     */
    public void initialize(int[] init) {
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[0][i][j] = new Cell(init[rand.nextInt(init.length)]);
                universe[0][i][j].pos = new int[]{0, i, j};
            }
        }
    }

    /**
     * Generates a custom starting state of the Universe.
     *
     * @param init 2d int array of states.
     */
    public void initialize(int[][] init) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                universe[0][i][j] = new Cell(init[i][j]);
                universe[0][i][j].pos = new int[]{0, i, j};
            }
        }
    }

    /**
     * @return the Universe represented as a 3d int array of states.
     */
    public int[][][] toArray() {
        int[][][] arr = new int[timeSteps][size][size];
        for (int t = 0; t < timeSteps; t++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (universe[t][i][j] != null) {
                        arr[t][i][j] = universe[t][i][j].state;
                    }
                }
            }
        }
        return arr;
    }

    /**
     * Randomly determine the neighborhood conditions of the Universe.
     */
    public void setNeighborhood() {
        Random random = new Random();
        this.neighborhood = Neighborhood.MOORE;
        this.radius = 1 + random.nextInt(10);
    }

    /**
     * Manually determine the neighborhood conditions of the Universe.
     *
     * @param neighborhood the neighborhood type of the Universe: VN or MOORE.
     * @param radius       the Universe's neighborhood radius.
     */
    public void setNeighborhood(Neighborhood neighborhood, int radius) {
        this.neighborhood = neighborhood;
        this.radius = radius;
    }

    /**
     * Returns the neighbours of the Cell cell according to the neighborhood rules of the Universe.
     *
     * @param cell the Cell of which the neighbours are returned.
     * @return Cells array of the neighbours.
     */
    private Cell[] getCellNeighbours(Cell cell) {
        switch (neighborhood) {
            case MOORE:
                if (borders) {
                    bordersMoore(cell);
                } else {
                    cyclicMoore(cell);
                }
                break;
            case VN:
                if (borders) {
                    bordersVonNeumann(cell);
                } else {
                    cyclicVonNeumann(cell);
                }
                break;
        }
        return cell.cellNeighbours;
    }

    // Returns the neighbours of the Cell cell for a Moore neighborhood with borders.
    private void bordersMoore(Cell cell) {
        int nulls = 0;

        Cell[] neighbors = new Cell[(2 * radius + 1) * (2 * radius + 1)];
        int k = -radius;

        int t = cell.pos[0];
        int i = cell.pos[1];
        int j = cell.pos[2];

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
                neighbors[I] = null;
                nulls++;
                continue;
            }
            neighbors[I] = universe[t][x][y];
        }

        cell.cellNeighbours = neighbors;
        cell.nulls = nulls;
    }

    // Returns the neighbours of the Cell cell for a cyclic Moore neighborhood (without borders).
    private void cyclicMoore(Cell cell) {
        Cell[] neighbors;
        int nulls = 0;

        neighbors = new Cell[(2 * radius + 1) * (2 * radius + 1)];
        int k = -radius;

        int t = cell.pos[0];
        int i = cell.pos[1];
        int j = cell.pos[2];

        for (int I = 0; I < (2 * radius + 1) * (2 * radius + 1); I++) {
            int index = I % (2 * radius + 1);
            if (index == 0 && I > 0) {
                k += 1;
            }
            if (index == radius && k == 0) {
                neighbors[I] = null;
                nulls++;
                continue;
            }
            neighbors[I] = universe[t][(size + k + i) % size][(size + index + j - radius) % size];
        }

        cell.cellNeighbours = neighbors;
        cell.nulls = nulls;
    }

    // Returns the neighbours of the Cell cell for a Von Neumann neighborhood with borders.
    private void bordersVonNeumann(Cell cell) {
        Cell[] neighbors;
        int nulls = 0;

        neighbors = new Cell[(radius + 1) * (radius + 1) + radius * radius];
        int m = 0;
        int I = 0;

        int t = cell.pos[0];
        int i = cell.pos[1];
        int j = cell.pos[2];

        int x;
        int y;
        for (int k = -radius; k <= radius; k++) {
            for (int l = -m; l <= m; l++) {
                x = k + i;
                y = j + l;
                if ((k == 0 && l == 0) || ((x < 0) || (x >= size) || (y < 0) || (y >= size))) {
                    neighbors[I++] = null;
                    nulls++;
                    continue;
                }
                neighbors[I++] = universe[t][(x) % size][(y) % size];
            }
            if (k < 0) {
                m++;
            } else {
                m--;
            }
        }

        cell.cellNeighbours = neighbors;
        cell.nulls = nulls;
    }

    // Returns the neighbours of the Cell cell for a cyclic Von Neumann neighborhood (without borders).
    private void cyclicVonNeumann(Cell cell) {
        Cell[] neighbors;
        int nulls = 0;

        neighbors = new Cell[(radius + 1) * (radius + 1) + radius * radius];
        int m = 0;
        int I = 0;

        int t = cell.pos[0];
        int i = cell.pos[1];
        int j = cell.pos[2];

        for (int k = -radius; k <= radius; k++) {
            for (int l = -m; l <= m; l++) {
                if (k == 0 && l == 0) {
                    neighbors[I++] = null;
                    nulls++;
                    continue;
                }
                neighbors[I++] = universe[t][(size + i + k) % size][(size + j + l) % size];
            }
            if (k < 0) {
                m++;
            } else {
                m--;
            }
        }

        cell.cellNeighbours = neighbors;
        cell.nulls = nulls;
    }

    /**
     * @param rnd     Random class object.
     * @param end     upper bound of the RNG interval.
     * @param exclude Integer List of the values to be excluded from the possible outputs.
     * @return a random int not in exclude.
     */
    private int getRandomWithExclusion(Random rnd, int end, List<Integer> exclude) {
        int rand;
        while (true) {
            rand = rnd.nextInt(end);
            final int random = rand;
            if (exclude.stream().noneMatch(e -> e == random)) {
                break;
            }
        }
        return rand;
    }

    /**
     * Set a random rule.
     */
    public void setRule() {
        Random rand = new Random();

        int[][][] ruleTable = new int[rand.nextInt(states * states) + 1][][];
        int[][] trans;

        int neighborhoodSize;
        List<Integer> exclude;

        int num;

        for (int n = 0; n < ruleTable.length; n++) {
            trans = new int[rand.nextInt(states) + 1][2];
            neighborhoodSize = getCellNeighbours(universe[0][0][0]).length;
            exclude = new ArrayList<>(Collections.singletonList(states));

            while (trans[0][0] == trans[0][1]) {
                trans[0][0] = rand.nextInt(states);
                trans[0][1] = rand.nextInt(states);
            }

            for (int cond = 1; cond < trans.length; cond++) {
                num = rand.nextInt(neighborhoodSize);
                neighborhoodSize -= num;
                trans[cond][0] = num;

                // counting the same state twice is redundant.
                trans[cond][1] = getRandomWithExclusion(rand, states, exclude);
                exclude.add(trans[cond][1]);
            }

            ruleTable[n] = trans;
            System.out.println(Arrays.deepToString(trans));
        }
        this.rule = new Rule(ruleTable);
    }

    /**
     * Set a custom rule.
     *
     * @param ruleTable 3d int array as described in the README file.
     */
    public void setRule(int[][][] ruleTable) {
        this.rule = new Rule(ruleTable);
    }

    /**
     * Creates the universe at time (time + 1) based on the universe at time time, using rule.
     *
     * @param rule a rule by which to generate the next time step.
     * @param time the time at which to generate the next time step (time + 1).
     */
    private void advance(Rule rule, int time) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = universe[time][i][j];
                cell.cellNeighbours = getCellNeighbours(cell);
                universe[time + 1][i][j] = Rule.change(this, cell, rule);
                universe[time + 1][i][j].pos = new int[]{time + 1, i, j};
            }
        }
    }

    /**
     * Generates a GUI graphing the Universe.
     *
     * @param size  the size of the GUI (in pixels).
     * @param time  the time in the Universe to draw.
     * @param delay delay between time steps (in milliseconds). Setting the delay to 0 will result with a still graph.
     */
    public void animate(int size, int time, int delay) {
        frame = new CanvasFrame(size, this, time, delay);
    }

    /**
     * Generates the complete universe over time based on the initial universe (created by the initialize function)
     * using the advance() method.
     */
    public void create() {
        for (int t = 0; t < timeSteps - 1; t++) {
            advance(rule, t);
        }
    }
}
