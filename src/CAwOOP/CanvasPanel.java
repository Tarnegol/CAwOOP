package CAwOOP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


class CanvasPanel extends JPanel implements ActionListener {
    private final int size;
    private final Universe uni;
    private int time;
    private final int[][] colors;
    private Timer timer;
    private final JLabel timeText = new JLabel(String.format("Universe at time %d", time));

    /**
     * Default color scheme constructor. Default color scheme is grey-scale.
     *
     * @param size  size of the panel (in pixels). Identical to the size of the frame.
     * @param uni   the Universe to graph.
     * @param time  the starting time.
     * @param delay delay between time steps (in milliseconds). Setting the delay to 0 will result with a still graph.
     */
    CanvasPanel(int size, Universe uni, int time, int delay) {
        this.setPreferredSize(new Dimension(size, size + 30));

        this.size = size;
        this.uni = uni;
        this.time = time;

        // Default color scheme is grey scale
        int states = uni.states;
        int[][] colors = new int[states][3];
        for (int i = 0; i < states; i++) {
            Arrays.fill(colors[i], (int) (255f * ((float) i) / ((float) (states - 1))));
        }
        this.colors = colors;

        this.setBackground(Color.BLACK);
        timeText.setForeground(Color.WHITE);
        this.add(timeText);

        if (delay > 0) {
            timer = new Timer(delay, this);
            timer.start();
        }
    }

    /**
     * Custom color scheme constructor.
     *
     * @param size   size of the panel (in pixels). Identical to the size of the frame.
     * @param uni    the Universe to graph.
     * @param time   the starting time.
     * @param delay  delay between time steps (in milliseconds). Setting the delay to 0 will result with a still graph.
     * @param colors 2d int array of the form int[states][3], represented by RGB color code.
     */
    CanvasPanel(int size, Universe uni, int time, int delay, int[][] colors) {
        this.setPreferredSize(new Dimension(size, size + 30));

        this.size = size;
        this.uni = uni;
        this.time = time;
        this.colors = colors;

        this.setBackground(Color.BLACK);

        if (delay > 0) {
            timer = new Timer(delay, this);
            timer.start();
        }
    }

    /**
     * Overrides the super class paint method. Uses super class method paint.
     *
     * @param g java.awt.Graphics object of the frame.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int[] c;
        int us = uni.size;
        int w = size / us;

        for (int i = 0; i < us; i++) {
            for (int j = 0; j < us; j++) {
                c = colors[uni.universe[time][j][i].state];
                g2d.setColor(new Color(c[0], c[1], c[2]));
                g2d.fillRect(i * w, j * w + 30, w, w);
            }
        }
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < us; i++) {
            g2d.drawLine(i * w, 30, i * w, size + 30);
            g2d.drawLine(0, i * w + 30, size, i * w + 30);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawLine(0, 30, us * w, 30);
        timeText.setText(String.format("Universe at time %d", time));
    }

    /**
     * ActionListener action method to be used by timer.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (time < uni.timeSteps - 1) {
            time++;
        }
        repaint();
    }
}
