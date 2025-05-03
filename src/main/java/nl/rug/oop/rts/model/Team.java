package nl.rug.oop.rts.model;

import java.util.Arrays;
import java.util.List;

/**
 * Enum for creating the teams.
 */
public enum Team {
    MenElvesDwarves,
    MordorIsengard;

    public static List<Team> getTeams() {
        return Arrays.asList(Team.values());
    }

}
