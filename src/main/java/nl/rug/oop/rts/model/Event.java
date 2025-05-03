package nl.rug.oop.rts.model;

import java.util.Objects;

/**
 * Event class, subclases are the events that happen in battle.
 */
public abstract class Event {
    private String name;

    public Event(String name) {
        this.name = name;
    }

    public abstract void handleEvent(Graph graph, Node node);

    public abstract void handleEvent(Graph graph, Edge edge);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Event event = (Event) obj;
        return name.equals(event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
