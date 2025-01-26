package entities.NPC;

import entities.Direction;

public class Stats {
    public int health;
    public boolean inCombat = false;
    public boolean isDead = false;
    public NPCType type;
    public int zone;
    protected Direction currDirection;
    protected int cooldown = 0; // 0 means ready to attack
    protected int basicAttackCooldown = 10; // Change this, default 60 i guess
    protected int basicAttackStrength;
}
