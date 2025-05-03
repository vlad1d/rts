package nl.rug.oop.rts.model;

import nl.rug.oop.rts.view.EventDisplayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class of a teleporter event.
 */
public class TeleporterEvent extends Event {
    private String name;

    public TeleporterEvent() {
        super("Teleporter Event");
    }

    /**
     * The main method of this event, sends a message as well as teleports all armies to a random node.
     *
     * @param graph in which the battle is happening.
     * @param node  on which the army is.
     */
    public void handleEvent(Graph graph, Node node) {
        String message = "Teleporter activated by node " + node.getName() + ". Teleported armies to:\n";
        List<Army> armyList = node.getArmyList();
        List<Node> nodeList = graph.getNodes();
        Random random = new Random();
        int randomNode = random.nextInt(nodeList.size());
        Node newNode = nodeList.get(randomNode);

        message += newNode.getName();

        List<Army> teleportArmies = new ArrayList<>();
        for (Army army : armyList) {
            teleportArmies.add(army);
        }
        for (Army army : teleportArmies) {
            node.removeArmy(army);
            newNode.addArmy(army);
            army.setLocation(newNode, null);
        }
        EventDisplayScreen.displayMessage(message);
    }

    /**
     * Similar method of this event, sends a message as well as teleports all armies to a random edge.
     *
     * @param graph in which the battle is happening.
     * @param edge  on which the army is.
     */
    public void handleEvent(Graph graph, Edge edge) {
        String message = "Teleporter activated by edge [" +
                edge.getStart().getName() + "," + edge.getEnd().getName() + "]. Teleported armies to:\n";
        List<Army> armyList = edge.getArmyList();
        List<Edge> edgeList = graph.getEdges();
        Random random = new Random();
        int randomEdge = random.nextInt(edgeList.size());
        Edge newEdge = edgeList.get(randomEdge);

        message += "[" + newEdge.getStart().getName() + "," + newEdge.getEnd().getName() + "]";

        List<Army> teleportArmies = new ArrayList<>();
        for (Army army : armyList) {
            teleportArmies.add(army);
        }
        for (Army army : teleportArmies) {
            edge.removeArmy(army);
            newEdge.addArmy(army);
            army.setLocation(newEdge.getStart(), newEdge);
        }
        EventDisplayScreen.displayMessage(message);
    }
}
