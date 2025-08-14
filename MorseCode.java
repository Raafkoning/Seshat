import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MorseCode {

    private static final JLabel[] headers = {
        new JLabel("Morse Code"),
        new JLabel("English")
    };

    private static JTextArea topTextArea = new JTextArea("");
    private static JTextArea botTextArea = new JTextArea("");

    private static int width, height;

    private static void setWidth(int width){
        MorseCode.width = width;
    }

    private static void setHeight(int height){
        MorseCode.height = height;
    }

    private static Object[] getMorseItems(JTextArea[] textAreas, RoundedBtn[] buttons) {
        return new Object[] {
            MorseCode.headers[0],
            textAreas[0],
            buttons[0],
            MorseCode.headers[1],
            textAreas[1],
            buttons[1],
        };
    }

    private static final HashMap<String, String> alpha = new HashMap<String, String>() {{
        put("A",".-");
        put("B","-...");
        put("C","-.-.");
        put("D","-..");
        put("E",".");
        put("F","..-.");
        put("G","--.");
        put("H","....");
        put("I","..");
        put("J",".---");
        put("K","-.-");
        put("L",".-..");
        put("M","--");
        put("N","-.");
        put("O","---");
        put("P",".--.");
        put("Q","--.-");
        put("R",".-.");
        put("S","...");
        put("T","-");
        put("U","..-");
        put("V","...-");
        put("W",".--");
        put("X","-..-");
        put("Y","-.--");
        put("Z","--..");
        put("1",".----");
        put("2","..---");
        put("3","...--");
        put("4","....-");
        put("5",".....");
        put("6","-....");
        put("7","--...");
        put("8","---..");
        put("9","----.");
        put("0","-----");
        put(".",".-.-.-");
        put(",","--..--");
        put(":","---...");
        put("?","..--..");
        put("'",".----.");
        put("-","-...-");
        put("/","-..-.");
        put("(","-.--.");
        put(")","-.--.-");
        put("\"",".-..-.");
        put("&",".-...");
        put("!","-.-.--");
        put(";","-.-.-.");
        put("@",".--.-.");
    }};

    private static final HashMap<String, String> morse = new HashMap<String, String>() {{
        put(".-","A");
        put("-...","B");
        put("-.-.","C");
        put("-..","D");
        put(".","E");
        put("..-.","F");
        put("--.","G");
        put("....","H");
        put("..","I");
        put(".---","J");
        put("-.-","K");
        put(".-..","L");
        put("--","M");
        put("-.","N");
        put("---","O");
        put(".--.","P");
        put("--.-","Q");
        put(".-.","R");
        put("...","S");
        put("-","T");
        put("..-","U");
        put("...-","V");
        put(".--","W");
        put("-..-","X");
        put("-.--","Y");
        put("--..","Z");
        put(".----","1");
        put("..---","2");
        put("...--","3");
        put("....-","4");
        put(".....","5");
        put("-....","6");
        put("--...","7");
        put("---..","8");
        put("----.","9");
        put("-----","0");
        put(".-.-.-",".");
        put("--..--",",");
        put("---...",":");
        put("..--..","?");
        put(".----.","'");
        put("-...-","-");
        put("-..-.","/");
        put("-.--.","(");
        put("-.--.-",")");
        put(".-..-.","\"");
        put(".-...","&");
        put("-.-.--","!");
        put("-.-.-.",";");
        put(".--.-.","@");
    }};

    private static void ConvertMorse(){
        String text = MorseCode.topTextArea.getText();

        String bottomText = "";

        if(MorseCode.headers[0].getText().equals("Morse Code")){
            String regex = "[\\s]";
            String[] charArray = text.split(regex);

            for (String chars: charArray){
                bottomText += MorseCode.morse.get(chars);
                
            }  
            MorseCode.botTextArea.setText(bottomText);

        }
        else{
            for (int i=0; i<text.length(); i++){
                char c = Character.toUpperCase(text.charAt(i));
                bottomText += MorseCode.alpha.get(Character.toString(c))+" ";
                
            }
            MorseCode.botTextArea.setText(bottomText);

        }

        
    }

    private static void switchInputs(){
        JLabel temp = headers[0];
        MorseCode.headers[0]=MorseCode.headers[1];
        MorseCode.headers[1]=temp;

        MorseCode.topTextArea.setText("");
        MorseCode.botTextArea.setText("");
        
    }

    private static int getXPos(int width){
        int actualWidth = (width-16);
        int textArea = (int)(actualWidth*.8);
        int xPos = ((actualWidth-textArea)/2);

        return xPos;
    }

    private static ArrayList<Integer> getYPos(int height, Object[] morseItems){
        int actualHeight = (height-39);
        int header = 40, textArea = 200, space = 15, button = 20;
        ArrayList<Integer> yPos = new ArrayList<>();

        yPos.add(space);
        int nextPos = space;
        
        for(int i=1; i<morseItems.length; i++){
            if(morseItems[i] instanceof javax.swing.JLabel){
                nextPos += button + space;
            }
            else if(morseItems[i] instanceof javax.swing.JTextArea){
                nextPos += header;
            }
            else if(morseItems[i] instanceof RoundedBtn){
                nextPos += textArea + space;
            }
            yPos.add(nextPos);
        }
        return yPos;
    } 

    private static void createPanel(Object[] morseItems, int xPos, ArrayList<Integer> yPos, JPanel panel){
        for (int i=0; i<morseItems.length; i++){
            if(morseItems[i] instanceof javax.swing.JLabel){
                ((JComponent)morseItems[i]).setBounds(xPos, yPos.get(i), (int)(MorseCode.width*.8), 30);
            }
            else if(morseItems[i] instanceof javax.swing.JTextArea){
                ((JComponent)morseItems[i]).setBounds(xPos, yPos.get(i), (int)(MorseCode.width*.8), 200);
            }
            else if(morseItems[i] instanceof RoundedBtn){
                ((JComponent)morseItems[i]).setBounds(xPos, yPos.get(i), (int)(MorseCode.width*.3), 20);
            }
            

            panel.add((JComponent)morseItems[i]);
        }
    }

    public static JPanel create(int width, int height) {
        setWidth(width);
        setHeight(height);
        int xPos = getXPos(width);
        
        //Morse Code Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);        
        mainPanel.setBackground(GlobalColors.backColor);

        //Customize headers
        for(JLabel header: headers){
            header.setFont(new Font("Arial", Font.PLAIN, 20));
        }
    
        //Text Areas
        MorseCode.topTextArea.setFont(new Font("Arial", Font.PLAIN, 20));

        MorseCode.botTextArea.setEditable(false);
        MorseCode.botTextArea.setFont(new Font("Arial", Font.PLAIN, 20));

        JTextArea[] textAreas = {
            MorseCode.topTextArea,
            MorseCode.botTextArea
        };

        //Customize Text boxes
        for (JTextArea textArea : textAreas) {
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
        }

        topTextArea.setBackground(GlobalColors.textFocusColor);
        botTextArea.setBackground(GlobalColors.unfocusedColor);

        //Buttons
        RoundedBtn switchBtn = new RoundedBtn("Switch");
        switchBtn.setFont(new Font("Arial", Font.BOLD, 16));


        RoundedBtn convertBtn = new RoundedBtn("Convert");
        convertBtn.setFont(new Font("Arial", Font.BOLD, 16));

        RoundedBtn[] buttons = {
            switchBtn,
            convertBtn,
        };

        Object[] morseItems = getMorseItems(textAreas, buttons);

        ArrayList<Integer> yPos = getYPos(height, morseItems);

        switchBtn.addActionListener(e ->{
            switchInputs();
            mainPanel.removeAll();
            Object[] newMorseItems = getMorseItems(textAreas, buttons);
            createPanel(newMorseItems, xPos, yPos, mainPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        convertBtn.addActionListener(e ->{
            ConvertMorse();
        });
                
        createPanel(morseItems, xPos, yPos, mainPanel);       
        
        return mainPanel;
    }
    
}
