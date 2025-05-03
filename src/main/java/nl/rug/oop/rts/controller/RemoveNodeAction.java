package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for removing a node.
 */
public class RemoveNodeAction implements ActionListener {

    private Graph graph;

    public RemoveNodeAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Node selectednode = graph.getSelectedNode();
        List<Edge> edgeList = selectednode.getEdgeList();
        List<Edge> newList = new ArrayList<>();

        for (Edge edge : edgeList) {
            newList.add(edge);
        }

        for (Edge edge : newList) {
            graph.removeEdge(edge);
        }

        graph.removeNode(selectednode);

        graph.setSelectedNode(null);
        graph.notifyListeners();
    }
}
