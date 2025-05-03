package nl.rug.oop.rts.view;

import nl.rug.oop.rts.model.Faction;

import javax.swing.*;

/**
 * Class that creates the option panel with the factions.
 */
public class ArmyAdditionScreen {

    /**
     * This is the method that does all the work in the class, creating the panel.
     *
     * @return the faction that is selected by the user
     */
    public static Faction showFactions() {
        Faction[] factions = Faction.values();
        Faction selectedFaction = (Faction) JOptionPane.showInputDialog(
                null, "Select a faction:", "Selector",
                JOptionPane.PLAIN_MESSAGE, null, factions, factions[0]);
        return selectedFaction;
    }
}
