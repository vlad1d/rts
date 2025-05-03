package nl.rug.oop.rts.model;

import lombok.Getter;

/**
 * Class for unit.
 */
public class Unit {
    @Getter
    private int damage;
    @Getter
    private int health;
    @Getter
    private String name;

    /**
     * Sets the unit's health, daamage and name.
     *
     * @param health of a unit
     * @param damage that a unit can do
     * @param name   of a unit
     */
    public Unit(int health, int damage, String name) {
        this.health = health;
        this.damage = damage;
        this.name = name;
    }
}
