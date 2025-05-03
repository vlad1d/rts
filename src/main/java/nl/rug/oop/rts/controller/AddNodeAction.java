package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to add node.
 */
public class AddNodeAction implements ActionListener {
    private Graph graph;
    private Node node;

    public AddNodeAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        node = new Node("New", 20, 20);
        graph.addNode(node);
        graph.notifyListeners();
    }
}
