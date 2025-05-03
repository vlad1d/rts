package nl.rug.oop.rts.view;

import nl.rug.oop.rts.model.AresEvent;
import nl.rug.oop.rts.model.Event;
import nl.rug.oop.rts.model.FallenArmyEvent;
import nl.rug.oop.rts.model.TeleporterEvent;

import javax.swing.*;

/**
 * Class that creates the option panel with the events.
 */
public class EventAdditionScreen {

    /**
     * This is the method that does all the work in the class, creating the panel.
     *
     * @return the faction that is selected by the user
     */
    public static Event showEvents() {
        String[] eventOptions = {"Teleporter Event", "Fallen Army Event", "Ares Event"};
        Event selectedEvent = null;
        String selectedString = (String) JOptionPane.showInputDialog(
                null, "Select an event:", "hi andrew",
                JOptionPane.PLAIN_MESSAGE, null, eventOptions, eventOptions[0]);
        if (selectedString != null) {
            if (selectedString.equals("Teleporter Event")) {
                selectedEvent = new TeleporterEvent();
            } else if (selectedString.equals("Fallen Army Event")) {
                selectedEvent = new FallenArmyEvent();
            } else if (selectedString.equals("Ares Event")) {
                selectedEvent = new AresEvent();
            }
        }
        return selectedEvent;
    }
}
