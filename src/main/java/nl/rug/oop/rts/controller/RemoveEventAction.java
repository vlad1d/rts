package nl.rug.oop.rts.controller;

import nl.rug.oop.rts.model.Edge;
import nl.rug.oop.rts.model.Event;
import nl.rug.oop.rts.model.Graph;
import nl.rug.oop.rts.model.Node;
import nl.rug.oop.rts.view.EventRemovalScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that removes an event after prompting the user with the choice.
 */
public class RemoveEventAction implements ActionListener {
    private Graph graph;

    public RemoveEventAction(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (graph.getSelectedNode() != null) {
            Node selectedNode = graph.getSelectedNode();
            Event selectedEvent = EventRemovalScreen.showEventsNode(selectedNode);
            if (selectedEvent != null) {
                selectedNode.removeEvent(selectedEvent);
            }
        } else if (graph.getSelectedEdge() != null) {
            Edge selectedEdge = graph.getSelectedEdge();
            Event selectedEvent = EventRemovalScreen.showEventsEdge(selectedEdge);
            if (selectedEvent != null) {
                selectedEdge.removeEvent(selectedEvent);
            }
        }
    }

}
