import java.awt.*;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BaseConversion {

    private static String formatString(String unformatted){
        String returnStr = "";

        if(unformatted.length()%4 != 0 || unformatted.length()>4){
            int count = 0;
            
            while((count+unformatted.length())%4 != 0){
                count++;
            }
            for(int i=0; i<count; i++){
                unformatted = "0"+unformatted;
            }
            
            for(int i=0; i<unformatted.length(); i++){
                
                if(i%4 == 0){
                    returnStr = returnStr + (" ");
                }
                returnStr = returnStr + unformatted.charAt(i);
            }
            //binary = String.format("%04s", binary);
        }
        else{
            returnStr = unformatted;
        }
        return returnStr = returnStr.trim();
    }

    private static String decToBin(int decimal){
        String binary = "", returnBin = "";

        while((double)decimal/2 != 0.5){
            if(decimal%2 != 0){
                binary = "1" + binary;
            }
            else{
                binary = "0" + binary;
            }
            decimal = decimal/2;
            //System.out.println(decimal);
        }
        binary = "1" + binary;

        return formatString(binary);

    }       

    private static String dectoHex(int decimal){
        String returnHex = "", binary="";
        int binNum = 8, hexValue=0;

        binary = decToBin(decimal);

        String[] hexChars = binary.split(" ");
        for(String hexChar : hexChars){
            int i=0;
            //System.out.println(hexChar);
            while(binNum > 0){
                char binaryDigit = hexChar.charAt(i);

                if(binaryDigit == '1'){
                    hexValue += binNum;
                }
                binNum = binNum/2;
                i++;
            }            
            switch(hexValue){
                case 10: returnHex = returnHex + "A"; break;
                case 11: returnHex = returnHex + "B"; break;
                case 12: returnHex = returnHex + "C"; break;
                case 13: returnHex = returnHex + "D"; break;
                case 14: returnHex = returnHex + "E"; break;
                case 15: returnHex = returnHex + "F"; break;
                default: returnHex = returnHex + Integer.toString(hexValue); break;
            }
            hexValue = 0;
            binNum = 8;
        }
        
        return formatString(returnHex);
        
    }

    private static final String[] bases = {"Binary", "Hexadecimal"};

    private static final JLabel[] headers = {
        new JLabel("Binary"),
        new JLabel("Hexadecimal"),
    };

    private static JTextArea topTextArea = new JTextArea("");
    private static JTextArea botTextArea = new JTextArea("");

    private static int[] selectedItem = {0};

    private static void createPanel(JPanel panel, int width){
        panel.removeAll();

        JComboBox<String> baseSelect = new JComboBox<>(BaseConversion.bases);
        baseSelect.setBackground(GlobalColors.unfocusedColor);
        baseSelect.setForeground(GlobalColors.white);
        baseSelect.setMaximumSize(new Dimension(100, 40));
        
        baseSelect.addActionListener(e -> {
            BaseConversion.selectedItem[0] = baseSelect.getSelectedIndex();
            
            System.out.println(BaseConversion.selectedItem[0]);
            createPanel(panel, width);
            
            panel.revalidate();
            panel.repaint();

            updateCon();

        });

        panel.add(baseSelect);

        panel.add(Box.createVerticalStrut(50)); 

        JLabel topHeader = new JLabel("Decimal");
        topHeader.setFont(new Font("Arial", Font.BOLD, 20));
        topHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(topHeader);

        
        BaseConversion.topTextArea.setMaximumSize(new Dimension((int)(width*.8), 100));
        BaseConversion.topTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        BaseConversion.topTextArea.setBackground(GlobalColors.textFocusColor);
        BaseConversion.topTextArea.getDocument().addDocumentListener(decListener);
        panel.add(BaseConversion.topTextArea);

        panel.add(Box.createVerticalStrut(20)); 

        BaseConversion.headers[BaseConversion.selectedItem[0]].setFont(new Font("Arial", Font.BOLD, 20));
        BaseConversion.headers[BaseConversion.selectedItem[0]].setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(BaseConversion.headers[BaseConversion.selectedItem[0]]);

        
        BaseConversion.botTextArea.setMaximumSize(new Dimension((int)(width*.8), 100));
        BaseConversion.botTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        BaseConversion.botTextArea.setBackground(GlobalColors.unfocusedColor);
        BaseConversion.botTextArea.setEditable(false);
        
        panel.add(BaseConversion.botTextArea);
        
    };

    private static DocumentListener decListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { 
                updateCon();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                updateCon();
            }
            @Override public void changedUpdate(DocumentEvent e) {}
        };

    private static void updateCon(){
        String text = topTextArea.getText().trim(), value = "";
        if (text.isEmpty()){
            BaseConversion.botTextArea.setText("");
            
        }
        else{
            switch(BaseConversion.selectedItem[0]){
                case 0:
                    value = decToBin(Integer.parseInt(text));
                    BaseConversion.botTextArea.setText(value);
                    break;
                case 1:
                    value = dectoHex(Integer.parseInt(text));
                    BaseConversion.botTextArea.setText(value);
                    break;
            }


        }

    }

    public static JPanel create(int width, int height) {
        JPanel panel = new JPanel();
        panel.setBackground(GlobalColors.backColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        createPanel(panel, width);

        return panel;
    }
}
