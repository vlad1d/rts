package nl.rug.oop.rts.model;

import nl.rug.oop.rts.view.EventDisplayScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for the fallen army.
 */
public class FallenArmyEvent extends Event {
    private String name;

    public FallenArmyEvent() {
        super("Fallen Army Event");
    }

    /**
     * Main method, goes through all armies at the node and a randomizer decides whether they stay or are removed.
     *
     * @param graph that has nodes
     * @param node  on which the army is
     */
    public void handleEvent(Graph graph, Node node) {
        String message = "Fallen Army activated by node " + node.getName() + ". Fallen soldiers:\n";
        List<Army> armyList = node.getArmyList();
        List<Army> deadArmy = new ArrayList<>();
        for (Army army : armyList) {
            Random random = new Random();
            int live = random.nextInt(2);
            if (live == 0) {
                deadArmy.add(army);
                message += army.getFaction();
                message += "\n";
            }
        }
        for (Army army : deadArmy) {
            node.removeArmy(army);
        }
        if (deadArmy.isEmpty()) {
            message += "none.";
        }
        EventDisplayScreen.displayMessage(message);
    }

    /**
     * Main method, goes through all armies at the edge and a randomizer decides whether they stay or are removed.
     *
     * @param graph that has edges
     * @param edge  on which the army is
     */
    public void handleEvent(Graph graph, Edge edge) {
        String message = "Fallen Army activated by edge [" +
                edge.getStart().getName() + "," + edge.getEnd().getName() + "]. Fallen soldiers:\n";
        List<Army> armyList = edge.getArmyList();
        List<Army> deadArmy = new ArrayList<>();
        for (Army army : armyList) {
            Random random = new Random();
            int live = random.nextInt(2);
            if (live == 0) {
                deadArmy.add(army);
                message += army.getFaction();
                message += "\n";
            }
        }
        for (Army army : deadArmy) {
            edge.removeArmy(army);
        }
        if (deadArmy.isEmpty()) {
            message += "none.";
        }
        EventDisplayScreen.displayMessage(message);
    }
}
