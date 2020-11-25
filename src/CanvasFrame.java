import javax.swing.*;

class CanvasFrame extends JFrame {
    CanvasPanel panel;

    CanvasFrame(int size, Universe uni, int time, int delay) {
        panel = new CanvasPanel(size, uni, time, delay);

        this.setTitle(String.format("Universe at time %d", panel.time));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(size, size);
        this.setResizable(false);

        this.add(panel);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
