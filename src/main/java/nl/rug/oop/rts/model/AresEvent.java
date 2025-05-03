package nl.rug.oop.rts.model;

import nl.rug.oop.rts.view.EventDisplayScreen;

import java.util.List;
import java.util.Random;

/**
 * Class for Ares events.
 */
public class AresEvent extends Event {
    private String name;

    public AresEvent() {
        super("Ares Event");
    }

    /**
     * Main method, goes through all armies on the node and gives them more damage, at the price of health.
     *
     * @param graph that has nodes
     * @param node  on which the armies are
     */
    public void handleEvent(Graph graph, Node node) {
        String message = "Ares activated by node " + node.getName() + ". All armies get:\n";
        Random random = new Random();
        int randomDamage = random.nextInt(150) + 100;
        int randomHealth = random.nextInt(150) + 100;
        message += randomDamage + " damage\n";
        message += "-" + randomHealth + " health\n";
        List<Army> armyList = node.getArmyList();
        for (Army army : armyList) {
            army.setDamage(army.getDamage() + randomDamage);
            if (army.getHealth() - randomHealth > 1) {
                army.setHealth(army.getHealth() - randomHealth);
            } else {
                army.setHealth(1);
            }
        }
        EventDisplayScreen.displayMessage(message);
    }

    /**
     * Main method, goes through all armies on th edge and gives them more damage, at the price of health.
     *
     * @param graph that has edges
     * @param edge  on which the armies are
     */
    public void handleEvent(Graph graph, Edge edge) {
        String message = "Ares activated by edge [" +
                edge.getStart().getName() + "," + edge.getEnd().getName() + "]. All armies get:\n";
        Random random = new Random();
        int randomDamage = random.nextInt(150) + 100;
        int randomHealth = random.nextInt(150) + 100;
        message += randomDamage + " damage\n";
        message += "-" + randomHealth + " health\n";
        List<Army> armyList = edge.getArmyList();
        for (Army army : armyList) {
            army.setDamage(army.getDamage() + randomDamage);
            if (army.getHealth() - randomHealth > 1) {
                army.setHealth(army.getHealth() - randomHealth);
            } else {
                army.setHealth(1);
            }
        }
        EventDisplayScreen.displayMessage(message);
    }
}
