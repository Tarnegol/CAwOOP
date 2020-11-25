import javax.swing.*;

public class CanvasFrame extends JFrame {
    CanvasPanel panel;

    CanvasFrame(int size, Universe uni, int time) {
        panel = new CanvasPanel(size, uni, time);

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
