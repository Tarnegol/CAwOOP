public class Main {

    public static void main(String[] args) {
        int size = 100;
        int time = 100;

        Universe uni = new Universe(size, 2, true, time);

//        int[][] start = new int[size][size];
//        start[0][1] = 1;
//        start[2][0] = 1;
//        start[1][2] = 1;
//        start[2][1] = 1;
//        start[2][2] = 1;
//        uni.initialize(start);

        uni.initialize();
        uni.setNeighborhood(Universe.Neighborhood.MOORE, 1);

        int[][][] ruleTable = new int[][][]{
                {{0, 1}, {3, 1}},
                {{1, 0}, {0, 1}},
                {{1, 0}, {1, 1}},
                {{1, 0}, {4, 1}},
                {{1, 0}, {5, 1}},
                {{1, 0}, {6, 1}},
                {{1, 0}, {7, 1}},
                {{1, 0}, {8, 1}},
        };
        uni.setRule(new Rule(ruleTable));

//        uni.setRule();

        uni.create();

        uni.animate(500, 0, 100);
    }
}
