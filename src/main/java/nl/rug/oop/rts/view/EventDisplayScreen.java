package nl.rug.oop.rts.view;

import javax.swing.*;

/**
 * Class that handles the display of messages as pop-ups.
 */
public class EventDisplayScreen {

    /**
     * Display takes a string and pops up the message.
     *
     * @param message that pops up.
     */
    public static void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Event Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
