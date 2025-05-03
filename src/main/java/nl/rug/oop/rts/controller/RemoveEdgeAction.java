package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to remove edge.
 */
public class RemoveEdgeAction implements ActionListener {
    private Graph graph;

    public RemoveEdgeAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Edge selectedEdge = graph.getSelectedEdge();
        graph.removeEdge(selectedEdge);
        graph.setSelectedEdge(null);
        graph.notifyListeners();
    }
}
