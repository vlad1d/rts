package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to rename a node.
 */
public class RenameAction implements ActionListener {
    private Graph graph;
    private Node node;
    private Edge edge;
    private String myString;
    private JTextField textField;

    public RenameAction(Graph graph, JTextField textField) {
        this.graph = graph;
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        node = graph.getSelectedNode();
        edge = graph.getSelectedEdge();
        if (node != null) {
            myString = textField.getText();
            if (myString.length() < 8) {
                node.setName(myString);
                textField.setText("");
            } else {
                textField.setText("Name too long.");
            }
        }

        if (edge != null) {
            myString = textField.getText();
            if (myString.length() < 8) {
                edge.setName(myString);
                textField.setText("");
            } else {
                textField.setText("Name too long.");
            }
        }
        graph.notifyListeners();
    }
}
