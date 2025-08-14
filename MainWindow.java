import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

class GlobalColors {
    public static Color unfocusedColor = Color.decode("#b39b6b");
    public static Color textFocusColor = Color.decode("#ccb170");
    public static Color focusColor = Color.decode("#F1D592");
    public static Color hoverColor = Color.decode("#6b83b3");
    public static Color borderColor = Color.decode("#70452F");
    public static Color backColor = Color.decode("#F1D592");
    public static Color foreColor = Color.decode("#252937");
    public static Color unknownColor = Color.decode("#1D1D27");
    public static Color white = Color.decode("#ffffff");
}


class ResizeListener implements ComponentListener{
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentResized(ComponentEvent e){
        Dimension newSize = e.getComponent().getBounds().getSize();
        //System.out.print(newSize);
    }
}

class TabStateHandler extends MouseAdapter implements FocusListener {
    //private boolean isFocused = false;

    private final RoundedTab[] allButtons;
    
    public TabStateHandler(RoundedTab[] buttons){
        this.allButtons = buttons;
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getComponent() instanceof RoundedTab focusedTab) {
            for (RoundedTab tab : allButtons){
                boolean isFocused = (tab == focusedTab);
                tab.setFocused(isFocused);

                if(isFocused){
                    tab.setBackground(GlobalColors.focusColor);
                    tab.setClicked(tab.getText());
                }
                else{
                    tab.setBackground(GlobalColors.unfocusedColor);
                }

            }
        }
    }

    
    @Override
    public void focusLost(FocusEvent e) {
        /*if (e.getComponent() instanceof RoundedTab button) {
            button.setFocused(false);
        }*/
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getComponent() instanceof RoundedTab button) {
            button.setHovered(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent() instanceof RoundedTab button) {
            button.setHovered(false);
        }
    }
}

class RoundedTab extends JButton {

    private static final int ARC_RADIUS = 20;

    private boolean isHovered = false;
    private boolean isFocused = false;
    private String isClicked = "";

    public void setClicked(String clicked){
        //System.out.print(clicked);
        this.isClicked = clicked;
        repaint();
    }

    public void setHovered(boolean hovered) {
        this.isHovered = hovered;
        repaint();
    }

    public void setFocused(boolean focused) {
        this.isFocused = focused;
        repaint();
    }

    public RoundedTab(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Create path with top rounded corners and bottom square
        Path2D.Float path = new Path2D.Float();
        path.moveTo(0, ARC_RADIUS);
        path.quadTo(0, 0, ARC_RADIUS, 0);
        path.lineTo(width - ARC_RADIUS, 0);
        path.quadTo(width, 0, width, ARC_RADIUS);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.closePath();

        Color fillColor = GlobalColors.unfocusedColor;
        //System.out.print(this.isClicked);

        if (isFocused) {
            fillColor = GlobalColors.focusColor;
        }
        else if (isHovered) {
            fillColor = GlobalColors.hoverColor;
        }
        else{
            fillColor = GlobalColors.unfocusedColor;
        }
        
        g2.setColor(fillColor);
        g2.fill(path);

        g2.fill(path);

        //Draw border
        //g2.setColor(GlobalColors.borderColor);
        //g2.draw(path);

        g2.dispose();

        //Paint button
        super.paintComponent(g);
    }


}

public class MainWindow {
    
    //width and height of the window
    public static int frameWidth = 600;
    public static int frameHeight = 700;

    public static ArrayList<Integer> btnLayouts(int numBtns, int frameWidth){
        ArrayList<Integer> btnInfo = new ArrayList<>();
        
        frameWidth = frameWidth - 16;
        int space = (15);
        int btnWidth = ((frameWidth-(space*2))-((numBtns-1)*space))/3;
        
        btnInfo.add(btnWidth);
        btnInfo.add(space);
        
        for(int i=1; i<numBtns; i++){
            int nextBtn = (space * (i+1))+(btnWidth*i);
            btnInfo.add(nextBtn);
        }
        //System.out.print(btnInfo);
    
        return btnInfo;
    } 

    public static void main(String[] args)
    {
        RoundedTab[] tabs = {
            new RoundedTab("Morse Code"),
            new RoundedTab("Base Conversion"),
            new RoundedTab("Temperature"),
        };

        int tabHeight = 50;

        // Creating instance of JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new ResizeListener());
        frame.getContentPane().setBackground(GlobalColors.unknownColor);

        UIManager.put("Label.foreground", GlobalColors.foreColor);
        UIManager.put("Button.foreground", GlobalColors.foreColor);
        UIManager.put("Button.background", GlobalColors.backColor);
        UIManager.put("TextField.foreground", GlobalColors.foreColor);

        ArrayList<Integer> btnInfo = btnLayouts(tabs.length, frameWidth);

        JPanel contentPanel = new JPanel(new CardLayout());

        CardLayout cl = (CardLayout) contentPanel.getLayout();

        TabStateHandler handler = new TabStateHandler(tabs);

        for (int i=0; i<tabs.length; i++) {
            tabs[i].setBounds(btnInfo.get(i+1), 10, btnInfo.get(0), tabHeight);
            tabs[i].setFocusPainted(false);
            tabs[i].addFocusListener(handler);
            tabs[i].addMouseListener(handler);
            final int panelIndex = i+1;
            tabs[i].addActionListener(e -> cl.show(contentPanel, "panel" + panelIndex));
            frame.add(tabs[i]);
            tabs[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(null);

        //Content Panel
        contentPanel.add(MorseCode.create(frame.getWidth(),frame.getHeight()), "panel1");
        contentPanel.add(BaseConversion.create(frame.getWidth(),frame.getHeight()), "panel2");
        contentPanel.add(TempConversion.create(frame.getWidth(),frame.getHeight()), "panel3");

        contentPanel.setBounds(0,60, frame.getWidth(), frame.getHeight());
        contentPanel.setBackground(GlobalColors.backColor);
        frame.add(contentPanel);

        frame.setVisible(true);
        //System.out.print(frame.getContentPane().getHeight());
    }

}
