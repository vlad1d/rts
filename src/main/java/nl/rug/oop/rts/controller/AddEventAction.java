package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Event;
import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;
import nl.rug.oop.rts.view.EventAdditionScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Class that adds an event to a node/edge depending on what was selected from the panel.
 */
public class AddEventAction implements ActionListener {
    private Graph graph;

    public AddEventAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (graph.getSelectedNode() != null) {
            Event selectedEvent = EventAdditionScreen.showEvents();
            if (selectedEvent != null) {
                Node selectedNode = graph.getSelectedNode();
                int dontAdd = 0;
                List<Event> eventList = selectedNode.getEventList();
                for (Event event : eventList) {
                    if (event.equals(selectedEvent)) {
                        dontAdd = 1;
                        break;
                    }
                }
                if (dontAdd == 0) {
                    selectedNode.addEvent(selectedEvent);
                }
            }
        } else if (graph.getSelectedEdge() != null) {
            Event selectedEvent = EventAdditionScreen.showEvents();
            if (selectedEvent != null) {
                Edge selectedEdge = graph.getSelectedEdge();
                int dontAdd = 0;
                List<Event> eventList = selectedEdge.getEventList();
                for (Event event : eventList) {
                    if (event.equals(selectedEvent)) {
                        dontAdd = 1;
                        break;
                    }
                }
                if (dontAdd == 0) {
                    selectedEdge.addEvent(selectedEvent);
                }
            }
        }
    }
}
