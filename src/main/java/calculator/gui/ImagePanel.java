package calculator.gui;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;

/**
 * The UI component used for plotting.
 *
 * You should not need to read this file in order to complete the assignment.
 */
public class ImagePanel extends JPanel {
    private static final int DELAY = 100;  // In milliseconds
    private Image image;
    private Timer timer;

    public ImagePanel(Image image) {
        super(true);
        this.setImage(image);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.timer = new Timer(DELAY, e -> this.repaint());
        this.timer.start();
        this.addAncestorListener(new Closer());
    }

    public ImageDrawer getImageDrawer() {
        return new ImageDrawer(this.image);
    }

    public Image getImage() {
        return this.image;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, this);
    }

    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }

    private class Closer implements AncestorListener {
        @Override
        public void ancestorAdded(AncestorEvent event) {
            // Do nothing
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
            // Do nothing
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            if (ImagePanel.this.timer != null && ImagePanel.this.timer.isRunning()) {
                ImagePanel.this.timer.stop();
                ImagePanel.this.timer = null;
            }
        }
    }
}
