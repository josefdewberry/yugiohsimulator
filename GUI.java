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
    /**
     * GUI constructor. Eventually will run a Yu-Gi-Oh monster battle simulator.
     */
    public GUI() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        bgImage = new ImageIcon(this.getClass().getResource("./yugioh/background.png"));
        bgLabel = new JLabel(bgImage);
        bgLabel.setSize(frame.getWidth(), frame.getHeight());

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