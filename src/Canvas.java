import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class Canvas extends JFrame {
    public int size; // Size of the window (in pixels).
    public Universe universe; // The universe to draw.
    public int time; // The time in universe to draw.
    public int[][] colors; // color scheme

    public Canvas(int size, Universe universe, int time) // init function to create Canvas object.
    // Creates and defines JFrame window.
    // Defines colors. Default color scheme is grey scale. Linearly distributed, calculated by the number of states.
    {
        super(String.format("Universe at time %d", time));

        this.size = size;
        this.universe = universe;
        this.time = time;

        // Default color scheme is grey scale
        int states = universe.states;
        int[][] colors = new int[states][3];
        for (int i = 0; i < states; i++) {
            Arrays.fill(colors[i], (int) (255f * ((float) i) / ((float) (states - 1))));
        }
        this.colors = colors;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size, size + 30);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }

    public Canvas(int size, Universe universe, int time, int[][] colors)
    // Same as other Canvas function, but with a custom color scheme.
    {
        super(String.format("Universe at time %d", time));
        this.size = size;
        this.universe = universe;
        this.time = time;
        this.colors = colors;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size, size + 30);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }

    public void paint(Graphics g) // Graphs the grid using super function paint and java.awt.Graphics class functions.
    {
        super.paint(g);
        int w = size / universe.size;
        int[] c;
        int us = universe.size;

        for (int i = 0; i < us; i++) {
            for (int j = 0; j < us; j++) {
                c = colors[universe.universe[time][j][i].state];
                g.setColor(new Color(c[0], c[1], c[2]));
                g.drawRect(i * w, j * w + 30, w, w);
                g.fillRect(i * w, j * w + 30, w, w);
                g.setColor(Color.BLACK);
                g.drawLine(j * w, 30, j * w, size + 30);
                g.drawLine(0, j * w + 30, size, j * w + 30);
            }
        }
        g.drawLine(us * w, 30, us * w, size + 30);
        g.drawLine(0, us * w + 30, size, us * w + 30);
    }
}
