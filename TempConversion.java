import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TempConversion {

    private static ArrayList<Double> temps = new ArrayList<>();

    private static ArrayList<Double> fCK(double fah){
        double cel = (fah-32)*(5.0/9.0);
        double kel = cel + 273.15;

        return new ArrayList<>(Arrays.asList(cel, kel));
    }

    private static ArrayList<Double> cKF(double cel){
        double fah = (cel*(5.0/9.0))+32;
        double kel = cel + 273.15;

        return new ArrayList<>(Arrays.asList(kel, fah));
    }

    private static ArrayList<Double> kFC(double kel){
        double cel = kel - 273.15;
        double fah = (cel*(5.0/9.0))+32;
        

        return new ArrayList<>(Arrays.asList(fah, cel));
    }

    private static final JLabel[] headers = {
        new JLabel("Fahrenheit"),
        new JLabel("Celsius"),
        new JLabel("Kelvin"),
    };

    private static final JTextArea fNums = new JTextArea("");
    private static final JTextArea cNums = new JTextArea("");
    private static final JTextArea kNums = new JTextArea("");

    private static final JTextArea[] textAreas = {
        TempConversion.fNums,
        TempConversion.cNums,
        TempConversion.kNums
    };
    
    private static DocumentListener fahListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { 
                updateTemps();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                updateTemps();
            }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void updateTemps(){
                String text = fNums.getText().trim();
                if (text.isEmpty()){
                    cNums.setText("");
                    kNums.setText("");
                    
                }
                else{
                    temps = fCK(Double.parseDouble(fNums.getText()));
                    cNums.setText(String.format("%.2f", temps.get(0)));
                    kNums.setText(String.format("%.2f", temps.get(1)));
                }

            }
        };

    private static DocumentListener celListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { 
                updateTemps();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                updateTemps();
            }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void updateTemps(){
                String text = cNums.getText().trim();
                if (text.isEmpty()){
                    kNums.setText("");
                    fNums.setText("");
                    
                }
                else{
                    temps = cKF(Double.parseDouble(cNums.getText()));
                    kNums.setText(String.format("%.2f", temps.get(0)));
                    fNums.setText(String.format("%.2f", temps.get(1)));
                }

            }
        };
    
    private static DocumentListener kelListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateTemps(); }
            @Override public void removeUpdate(DocumentEvent e) { updateTemps(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            
            private void updateTemps(){
                String text = kNums.getText().trim();
                if (text.isEmpty()){
                    fNums.setText("");
                    cNums.setText("");
                    
                }
                else{
                    temps = kFC(Double.parseDouble(kNums.getText()));
                    fNums.setText(String.format("%.2f", temps.get(0)));
                    cNums.setText(String.format("%.2f", temps.get(1)));
                }

            }
    };

    private static final JCheckBox fahCB = new JCheckBox();
    private static final JCheckBox celCB = new JCheckBox();
    private static final JCheckBox kelCB = new JCheckBox();

    private static final JCheckBox[] checkBoxes = {
        TempConversion.fahCB,
        TempConversion.celCB,
        TempConversion.kelCB
    };

    private static ActionListener fCBListener = e -> {
        JCheckBox checkbox = (JCheckBox) e.getSource();
        boolean isChecked = checkbox.isSelected();
        if(isChecked){
            fNums.setBackground(GlobalColors.textFocusColor);
            cNums.setBackground(GlobalColors.unfocusedColor);
            kNums.setBackground(GlobalColors.unfocusedColor);

            fNums.getDocument().addDocumentListener(TempConversion.fahListener);
            cNums.getDocument().removeDocumentListener(TempConversion.celListener);
            kNums.getDocument().removeDocumentListener(TempConversion.kelListener);
            TempConversion.celCB.setSelected(false);
            TempConversion.kelCB.setSelected(false);
        }
        
    };

    private static ActionListener cCBListener = e -> {
        JCheckBox checkbox = (JCheckBox) e.getSource();
        boolean isChecked = checkbox.isSelected();
        if(isChecked){
            fNums.setBackground(GlobalColors.unfocusedColor);
            cNums.setBackground(GlobalColors.textFocusColor);
            kNums.setBackground(GlobalColors.unfocusedColor);

            fNums.getDocument().removeDocumentListener(TempConversion.fahListener);
            cNums.getDocument().addDocumentListener(TempConversion.celListener);
            kNums.getDocument().removeDocumentListener(TempConversion.kelListener);
            TempConversion.fahCB.setSelected(false);
            TempConversion.kelCB.setSelected(false);
        }

        
    };

    private static ActionListener kCBListener = e -> {
        JCheckBox checkbox = (JCheckBox) e.getSource();
        boolean isChecked = checkbox.isSelected();
        if(isChecked){
            fNums.setBackground(GlobalColors.unfocusedColor);
            cNums.setBackground(GlobalColors.unfocusedColor);
            kNums.setBackground(GlobalColors.textFocusColor);

            fNums.getDocument().removeDocumentListener(TempConversion.fahListener);
            cNums.getDocument().removeDocumentListener(TempConversion.celListener);
            kNums.getDocument().addDocumentListener(TempConversion.kelListener);
            TempConversion.fahCB.setSelected(false);
            TempConversion.celCB.setSelected(false);
        }

    };

    private static int getXPos(int width){
        int actualWidth = (width-16);
        int textArea = (int)(actualWidth*.8);
        int xPos = ((actualWidth-textArea)/2);

        return xPos;
    }

    private static ArrayList<Integer> getYPos(int height){
        int actualHeight = (height-39);
        int header = 40, textArea = 100, space = 15;
        ArrayList<Integer> yPos = new ArrayList<>();

        yPos.add(space);
        int nextPos = space;
        
        for(int i=1; i<6; i++){
            if(i%2 == 0){
                nextPos = nextPos + space + textArea;
            }
            else{
                nextPos = nextPos + header;
            }
            yPos.add(nextPos);
        }
        return yPos;
    } 

    public static JPanel create(int width, int height) {
        JPanel panel = new JPanel();
        //panel.add(new JLabel("This is for Temp Conversion"));
        panel.setBackground(GlobalColors.backColor);
        panel.setLayout(null);

        int xPos = getXPos(width);
        ArrayList<Integer> yPos = getYPos(height);

        for (JLabel header : TempConversion.headers) {
            header.setFont(new Font("Arial", Font.BOLD, 20));
        }

        for (JTextArea textArea : TempConversion.textAreas) {
            textArea.setFont(new Font("Arial", Font.PLAIN, 18));
            textArea.setBackground(GlobalColors.unfocusedColor);
            //textArea.getDocument().addDocumentListener(TempConversion.listener);
        }

        
        

        for(JCheckBox cb: checkBoxes){
            cb.setOpaque(false);
        }
        
        TempConversion.fahCB.addActionListener(fCBListener);
        TempConversion.celCB.addActionListener(cCBListener);
        TempConversion.kelCB.addActionListener(kCBListener);

        for(int i=0; i<3; i++){
            panel.add(headers[i]).setBounds(xPos, yPos.get(i*2), (int)(width*.8), 30);
            panel.add(textAreas[i]).setBounds(xPos, yPos.get(i*2+1), (int)(width*.8), 100);
            panel.add(checkBoxes[i]).setBounds(xPos-20, yPos.get(i*2)+5, 18, 18);
        }


        return panel;
    }
}
