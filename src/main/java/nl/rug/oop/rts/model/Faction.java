package nl.rug.oop.rts.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * An enum that contains all the factions including their units.
 */
public enum Faction {
    Men(Arrays.asList(
            new Unit(40, 12, "Gondor Soldier"),
            new Unit(60, 21, "Tower Guard"),
            new Unit(21, 53, "Ithilien Ranger"))
    ),
    Elves(Arrays.asList(
            new Unit(13, 2, "Lorien Warrior"),
            new Unit(31, 1, "Mirkwood Archer"),
            new Unit(54, 50, "Rivendell Lancer"))
    ),
    Dwarves(Arrays.asList(
            new Unit(13, 60, "Guardian"),
            new Unit(53, 7, "Phalanx"),
            new Unit(12, 12, "Axe Thrower"))
    ),
    Mordor(Arrays.asList(
            new Unit(76, 27, "Orc Warrior"),
            new Unit(34, 67, "Orc Pikeman"),
            new Unit(1, 19, "Haradrim Archer"))
    ),
    Isengard(Arrays.asList(
            new Unit(45, 12, "Uruk-hai"),
            new Unit(12, 33, "Uruk Crossbowman"),
            new Unit(53, 23, "Warg Rider"))
    );

    @Getter
    private List<Unit> unitList;

    Faction(List<Unit> unitList) {
        this.unitList = unitList;
    }
}
