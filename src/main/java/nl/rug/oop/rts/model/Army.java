package nl.rug.oop.rts.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class for army.
 */
public class Army {
    @Getter
    private final List<Unit> unitList;
    private int unitNumber;
    @Getter
    private Faction faction;
    @Getter
    @Setter
    private Node node;
    @Getter
    @Setter
    private Edge edge;
    @Getter
    private Team team;
    @Getter
    @Setter
    private int health;
    @Getter
    @Setter
    private int damage;
    private int i;

    /**
     * Method that initiates army with a faction, gives it a random number of units and a team based on the faction.
     *
     * @param faction the faction of an army.
     */
    public Army(Faction faction) {
        Random random = new Random();
        this.faction = faction;
        this.unitNumber = random.nextInt(41) + 10;
        this.unitList = new ArrayList<>();
        this.node = null;
        this.edge = null;
        this.health = 0;
        this.damage = 0;

        if (faction == Faction.Men || faction == Faction.Elves || faction == Faction.Dwarves) {
            this.team = Team.MenElvesDwarves;
        } else {
            this.team = Team.MordorIsengard;
        }

        for (i = 0; i < unitNumber; i++) {
            List<Unit> availableUnits = faction.getUnitList();
            int unitSize = availableUnits.size();
            int randomNumber = random.nextInt(unitSize);
            Unit chosenUnit = availableUnits.get(randomNumber);
            unitList.add(chosenUnit);
            this.health += chosenUnit.getHealth();
            this.damage += chosenUnit.getDamage();
        }
    }

    public void setLocation(Node node, Edge edge) {
        this.setNode(node);
        this.setEdge(edge);
    }

}
