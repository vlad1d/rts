package nl.rug.oop.rts;

import nl.rug.oop.rts.view.GraphFrame;

import javax.swing.*;

/**
 * The main class.
 */
public class Main {

    /**
     * First method that is called, initialises GraphFrame, displays the graph window and allows interaction with it.
     *
     * @param args passed through the java program when the program is executed
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GraphFrame();
        });

    }
}