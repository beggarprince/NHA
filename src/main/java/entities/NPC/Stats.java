package main.java.entities.NPC;

import main.java.entities.Direction;

public class Stats {
    public int health;
    public boolean inCombat = false;
    public boolean isDead = false;
    public NPCType type;
    public int zone;
    public Direction currDirection;
    public int combatCooldown = 0; // 0 means ready to attack
    public int basicAttackCooldown = 60; // Change this, default 60 i guess
    public int basicAttackStrength;
}
