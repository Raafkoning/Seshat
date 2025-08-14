import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RoundedBtn extends JButton {

    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    private boolean isHovered = false;

    public RoundedBtn(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setBackground(GlobalColors.foreColor);
        initMouseEvents();
    }

    public RoundedBtn(ImageIcon icon) {
        super(icon);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setBackground(GlobalColors.foreColor);
        initMouseEvents();
    }

    private void initMouseEvents(){
        this.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(GlobalColors.hoverColor);
                    repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(GlobalColors.foreColor);
                    repaint();
                }

        });
    }

    public void setHovered(boolean hovered){
        this.isHovered = hovered;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {        

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Draw the filled rounded background
        Shape roundRect = new RoundRectangle2D.Double(
            0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT
        );
        g2.setColor(getBackground());
        g2.fill(roundRect);

        // Get the icon from the button
        ImageIcon icon = (ImageIcon) getIcon();

        if (icon != null) {
            // Get the original image and scale it to fit the button size
            Image img = icon.getImage();
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            // Scale the image to fit within the button while maintaining the aspect ratio
            int buttonWidth = getWidth();
            int buttonHeight = getHeight();

            // Calculate the new dimensions while maintaining aspect ratio
            double aspectRatio = (double) imgWidth / imgHeight;
            int newWidth = buttonWidth;
            int newHeight = (int) (newWidth / aspectRatio);

            if (newHeight > buttonHeight) {
                newHeight = buttonHeight;
                newWidth = (int) (newHeight * aspectRatio);
            }

            // Scale the image
            Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

            // Draw the scaled image centered in the button
            int x = (buttonWidth - newWidth) / 2;
            int y = (buttonHeight - newHeight) / 2;
            g2.drawImage(scaledImg, x, y, null);
        }
        Color fillColor = GlobalColors.unfocusedColor;

        if (isHovered) {
            fillColor = GlobalColors.hoverColor;
        }
        else{
            fillColor = GlobalColors.foreColor;
        }

        
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded border
        g2.setColor(Color.BLACK);
        g2.draw(new RoundRectangle2D.Double(
            0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT
        ));
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        Shape roundRect = new RoundRectangle2D.Double(
            0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT
        );
        return roundRect.contains(x, y);
    }

}
