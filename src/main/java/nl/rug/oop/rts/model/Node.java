package nl.rug.oop.rts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for node.
 */
public class Node {
    private static int count = 0;
    @Getter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    private List<Edge> edgeList;
    @Getter
    @Setter
    private int x;
    @Getter
    @Setter
    private int y;
    @Getter
    @Setter
    private boolean selected;
    @Getter
    private List<Army> armyList;
    @Getter
    private List<Event> eventList;

    /**
     * Initialize the node.
     *
     * @param name of node.
     * @param x    coordinate of the node.
     * @param y    coordinate of the node.
     */
    public Node(String name, int x, int y) {
        this.id = count++;
        this.name = name;
        this.edgeList = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.armyList = new ArrayList<>();
        this.eventList = new ArrayList<>();
        selected = false;
    }

    /**
     * Method that adds an army to the list of armies in a node.
     *
     * @param army that is added.
     */
    public void addArmy(Army army) {
        armyList.add(army);
    }

    /**
     * Method that removes an army to the list of armies in a node.
     *
     * @param army that is removed.
     */
    public void removeArmy(Army army) {
        armyList.remove(army);
    }

    /**
     * Adds an edge to the list of edges from this node.
     *
     * @param edge that gets added.
     */
    public void addEdge(Edge edge) {
        edgeList.add(edge);
    }

    /**
     * Removes an edges from the list of edges from this node.
     *
     * @param edge that gets removed.
     */
    public void removeEdge(Edge edge) {
        edgeList.remove(edge);
    }

    /**
     * Allows you to move the node across the panel.
     *
     * @param newX  of the node.
     * @param newY  of the node.
     * @param graph that needs to be updated.
     */
    public void move(int newX, int newY, Graph graph) {
        x = newX;
        y = newY;
        graph.notifyListeners();
    }

    /**
     * Method that adds an event to a node.
     *
     * @param event that is happening at the node.
     */
    public void addEvent(Event event) {
        eventList.add(event);
    }

    /**
     * Method that removes an event from a node.
     *
     * @param event that is no longer happening at the node.
     */
    public void removeEvent(Event event) {
        eventList.remove(event);
    }
}
