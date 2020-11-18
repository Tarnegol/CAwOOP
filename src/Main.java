import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Universe uni = new Universe(10, 4, false, 1);
        uni.setNeighborhood("vn", 2);
        uni.initialize();

//        int[][] colors = new int[4][3];
//        colors[0] = new int[]{0, 0, 0};
//        colors[1] = new int[]{255, 0, 0};
//        colors[2] = new int[]{255, 255, 0};
//        colors[3] = new int[]{255, 255, 255};

        int i = 1;
        int j = 1;
        System.out.println(Arrays.toString(uni.getCellNeighbors(0, i, j)));
        System.out.println(uni.universe[0][i][j].state);

        uni.animate(500, 0);
    }
}
