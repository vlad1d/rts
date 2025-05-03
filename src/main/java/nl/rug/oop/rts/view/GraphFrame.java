package nl.rug.oop.rts.view;

import nl.rug.oop.rts.model.Graph;

import javax.swing.*;
import java.awt.*;

/**
 * Class to create a frame.
 */
public class GraphFrame extends JFrame {

    /**
     * This creates the graph as well as the pane, sets the default dimensions and visibility.
     */
    public GraphFrame() {
        super("Map");

        Graph graph = new Graph();

        GraphPanel graphPanel = new GraphPanel(graph);
        OptionMenuPanel optionMenu = new OptionMenuPanel(graph);

        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        pane.setResizeWeight(0.5);
        pane.setEnabled(false);

        graphPanel.requestFocusInWindow();
        optionMenu.requestFocusInWindow();

        pane.add(optionMenu);
        pane.add(graphPanel);

        add(pane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(1200, 1000));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
