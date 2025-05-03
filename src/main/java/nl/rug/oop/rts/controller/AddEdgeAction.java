package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to add edge.
 */
public class AddEdgeAction implements ActionListener {
    private Graph graph;

    public AddEdgeAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (graph.getSelectedNode() != null && !graph.isAddingEdge()) {
            graph.setAddingEdge(true);
            graph.setFirstNode(graph.getSelectedNode());
            graph.notifyListeners();
        }
    }
}
