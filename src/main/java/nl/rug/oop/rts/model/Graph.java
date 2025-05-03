package nl.rug.oop.rts.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.rts.controller.GraphListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class to keep track of nodes and edges.
 */
public class Graph {
    @Getter
    private List<Node> nodes;
    @Getter
    private List<Edge> edges;
    @Getter
    @Setter
    private Node selectedNode;
    @Getter
    @Setter
    private Edge selectedEdge;
    @Getter
    @Setter
    private Node firstNode;
    @Getter
    @Setter
    private Node secondNode;

    @Getter
    @Setter
    private boolean isAddingEdge;
    private Collection<GraphListener> listeners;

    /**
     * Creates a new graph.
     */
    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        selectedNode = null;
        isAddingEdge = false;
        listeners = new ArrayList<>();
    }

    public void addListener(GraphListener listener) {
        listeners.add(listener);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Removes a node from the graph, also removes edges that are connected to this edge.
     *
     * @param node that gets removed.
     */
    public void removeNode(Node node) {
        nodes.remove(node);
        removeConnectedEdges(node);
        notifyListeners();
    }

    /**
     * Removes the edges that are connected to a removed edge.
     *
     * @param node that got removes.
     */
    public void removeConnectedEdges(Node node) {
        List<Edge> connectedEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getStart() == node || edge.getEnd() == node) {
                connectedEdges.add(edge);
            }
        }
        edges.removeAll(connectedEdges);
    }

    /**
     * Prints out all nodes in a graph.
     */
    public void printNodes() {
        for (Node node : nodes) {
            System.out.println("Node = " + node.getName() + " with ID = " + node.getId());
        }
    }

    /**
     * Prints out all edges in a graph.
     */
    public void printEdges() {
        for (Edge edge : edges) {
            System.out.println("Edge = " + edge.getName());
        }
    }

    /**
     * Adds an edge to the graph.
     *
     * @param edge that gets added.
     */
    public void addEdge(Edge edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
            edge.getStart().addEdge(edge);
            edge.getEnd().addEdge(edge);
        }
    }

    /**
     * Removes an edge from the graph.
     *
     * @param edge that gets removed.
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
        if (edge.getStart().getEdgeList().contains(edge)) {
            edge.getStart().removeEdge(edge);
        }
        if (edge.getEnd().getEdgeList().contains(edge)) {
            edge.getEnd().removeEdge(edge);
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Inform the panel if any components have been updated.
     */
    public void notifyListeners() {
        for (GraphListener listener : listeners) {
            listener.graphUpdated();
        }
    }

    /**
     * Method that searches all nodes and edges for armies and returns the complete list.
     *
     * @return the army list
     */
    public List<Army> getArmies() {
        List<Army> armyList = new ArrayList<>();
        for (Node node : nodes) {
            List<Army> nodeArmyList = node.getArmyList();
            armyList.addAll(nodeArmyList);
        }
        for (Edge edge : edges) {
            List<Army> edgeArmyList = edge.getArmyList();
            armyList.addAll(edgeArmyList);
        }
        return armyList;
    }
}
