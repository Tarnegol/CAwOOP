public class Rule {
    private final int[][][] ruleTable;

    Rule(int[][][] ruleTable) {
        this.ruleTable = ruleTable;
    }

    static Cell change(Universe uni, Cell cell, Rule rule) {
        Cell newCell = new Cell(cell);
        int newState = cell.state;
        int[] statesCount = getCellNeighboursStatesCount(uni, cell);

        boolean is = false;
        for (int[][] trans : rule.ruleTable) {
            if (trans[0][0] == cell.state) {
                is = true;
                for (int cond = 1; cond < trans.length; cond++) {
                    if (statesCount[trans[cond][1]] != trans[cond][0]) {
                        is = false;
                        break;
                    }
                }
            }
            if (is) {
                newState = trans[0][1];
                break;
            }
        }
        newCell.state = newState;
        return newCell;
    }

    private static int[] getCellNeighboursStatesCount(Universe uni, Cell cell) {
        int[] neighbourStatesCount = new int[uni.states];
        for (Cell neighbour : cell.cellNeighbours) {
            if (neighbour != null) {
                neighbourStatesCount[neighbour.state]++;
            }
        }

        return neighbourStatesCount;
    }
}
