import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class CanvasPanel extends JPanel implements ActionListener {
    int size;
    Universe uni;
    int time;
    int[][] colors;
    Timer timer;
    JLabel timeText = new JLabel(String.format("Universe at time %d", time));

    CanvasPanel(int size, Universe uni, int time) {
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

        timer = new Timer(100, this);
        timer.start();
    }

    CanvasPanel(int size, Universe uni, int time, int[][] colors) {
        this.setPreferredSize(new Dimension(size, size + 30));
        this.size = size;
        this.uni = uni;
        this.time = time;
        this.colors = colors;

        this.setBackground(Color.BLACK);

        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int w = size / uni.size;
        int[] c;
        int us = uni.size;

        for (int i = 0; i < us; i++) {
            for (int j = 0; j < us; j++) {
                c = colors[uni.universe[time][j][i].state];
                g2d.setColor(new Color(c[0], c[1], c[2]));
                g2d.drawRect(i * w, j * w + 30, w, w);
                g2d.fillRect(i * w, j * w + 30, w, w);
                g2d.setColor(Color.BLACK);
                g2d.drawLine(j * w, 30, j * w, size + 30);
                g2d.drawLine(0, j * w + 30, size, j * w + 30);
                g2d.setColor(Color.WHITE);
                g2d.drawLine(0, 30, us * w, 30);
                timeText.setText(String.format("Universe at time %d", time));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (time < uni.timeSteps) {
            time++;
        }
        repaint();
    }
}
