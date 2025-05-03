package nl.rug.oop.rts.view;

import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Event;
import nl.rug.oop.rts.model.Node;

import javax.swing.*;
import java.util.List;

/**
 * Class that handles removal of an event.
 */
public class EventRemovalScreen {

    /**
     * Method takes a node and checks what events it has, then prompts the user the option to remove one.
     *
     * @param node that has the events.
     * @return the selected event
     */
    public static Event showEventsNode(Node node) {
        List<Event> eventList = node.getEventList();
        Event[] eventOptions = eventList.toArray(new Event[eventList.size()]);
        Event selectedEvent = null;
        if (eventList.size() != 0) {
            selectedEvent = (Event) JOptionPane.showInputDialog(
                    null, "Select an event:", "Selector",
                    JOptionPane.PLAIN_MESSAGE, null, eventOptions, eventOptions[0]);
        }

        return selectedEvent;
    }

    /**
     * Method takes an edge and checks what events it has, then prompts the user the option to remove one.
     *
     * @param edge that has the events.
     * @return the selected event
     */
    public static Event showEventsEdge(Edge edge) {
        List<Event> eventList = edge.getEventList();
        Event[] eventOptions = eventList.toArray(new Event[eventList.size()]);
        Event selectedEvent = null;
        if (eventList.size() != 0) {
            selectedEvent = (Event) JOptionPane.showInputDialog(
                    null, "Select an event:", "Selector",
                    JOptionPane.PLAIN_MESSAGE, null, eventOptions, eventOptions[0]);
        }
        return selectedEvent;
    }
}
