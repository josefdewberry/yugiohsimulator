import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.*;
/**
 * A GUI for the yugioh simulator that will look similar to the GBA Yu-Gi-Oh games.
 * 
 * @author josefdewberry
 */
public class GUI {
    
    private ImageIcon bgImage;
    private JLabel bgLabel;
    private ImageIcon deckImage;
    private JLabel deckLabel;
    private ImageIcon oppDeckImage;
    private JLabel oppDeckLabel;
    private ImageIcon fieldImage;
    private JLabel fieldLabel;


    /**
     * GUI constructor. Eventually will run a Yu-Gi-Oh monster battle simulator.
     */
    public GUI() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        bgImage = new ImageIcon(this.getClass().getResource("./yugioh/background.png"));
        bgLabel = new JLabel(bgImage);
        bgLabel.setSize(frame.getWidth(), frame.getHeight());

        deckImage = new ImageIcon(this.getClass().getResource("./yugioh/deckresized.png"));
        deckLabel = new JLabel(deckImage);
        deckLabel.setBounds(850, 475, 100, 147);

        oppDeckImage = new ImageIcon(this.getClass().getResource("./yugioh/oppdeckresized.png"));
        oppDeckLabel = new JLabel(deckImage);
        oppDeckLabel.setBounds(75, -7, 100, 147);

        fieldImage = new ImageIcon(this.getClass().getResource("./yugioh/field.png"));
        fieldLabel = new JLabel(fieldImage);
        fieldLabel.setBounds(250, 150, 500, 300);

        bgLabel.add(deckLabel);
        bgLabel.add(oppDeckLabel);
        bgLabel.add(fieldLabel);
        frame.add(bgLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Yu-Gi-Oh!");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Makes a GUI.
     * @param args Command line arguments, unused.
     */
    public static void main(String[] args) {
        new GUI();
    }
}