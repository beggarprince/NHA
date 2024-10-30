package entities.Enemy;

import util.Coordinate;

public abstract class Enemy {
    protected int health;
    protected Coordinate position;

    public Enemy(int health, Coordinate position) {
        this.health = health;
        this.position = position;
    }

    // Getter and setter methods
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    // Abstract method for setting the image, to be implemented by subclasses
    protected abstract void setImage();

    // Other common methods can be added here
}
