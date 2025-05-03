package nl.rug.oop.rts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for edge.
 */
public class Edge {
    private static int counter = 0;
    @Getter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    private Node start;
    @Getter
    private Node end;
    @Getter
    private List<Army> armyList;
    @Getter
    private List<Event> eventList;

    /**
     * Creates an edge.
     *
     * @param name  of the edge.
     * @param start node of the edge.
     * @param end   node of the edge.
     */
    public Edge(String name, Node start, Node end) {
        this.id = counter++;
        this.name = name;
        this.start = start;
        this.end = end;
        this.armyList = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }

    /**
     * Method that adds an army to the list of armies in an edge.
     *
     * @param army that is added.
     */
    public void addArmy(Army army) {
        armyList.add(army);
    }

    /**
     * Method that removes an army to the list of armies in an edge.
     *
     * @param army that is removed.
     */
    public void removeArmy(Army army) {
        armyList.remove(army);
    }

    /**
     * Method that adds an event to an edge.
     *
     * @param event that is happening at the edge.
     */
    public void addEvent(Event event) {
        eventList.add(event);
    }

    /**
     * Method that removes an event from an edge.
     *
     * @param event that is no longer happening at the edge.
     */
    public void removeEvent(Event event) {
        eventList.remove(event);
    }
}
