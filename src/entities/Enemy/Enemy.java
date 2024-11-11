package entities.Enemy;

import graphics.ScreenSettings;
import level.Level;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static util.CollisionKt.detectCollision;

public abstract class Enemy {
    protected int health;
    protected int lifespan;
    protected int hunger;
    protected Direction dir;

    public boolean dead = false;
    public int worldPosX;
    public int worldPosY;
    public int screenPosX;
    public int screenPosY;
    protected int movementSpeed;

    Level level = Level.getInstance("res/levelTest.csv");


    private Random random = new Random();


    public Enemy(int health, int x, int y) {
        this.health = health;
    }

    // Getter and setter methods
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }



    public int getWorldPosX() {
        return worldPosX;
    }

    public int getWorldPosY() {
        return worldPosY;
    }

    protected void decreaseHunger() {
        hunger--;
    }

    protected void damage(int damage) {
        health -= damage;
    }

    protected void age() {
        lifespan--;
    }

    protected abstract void setImage();

    public abstract BufferedImage getImage();

    public abstract void behavior();

    protected abstract void eat();

    protected  void agingCycle(){
        lifespan--;
        if(lifespan <= 0) death();
    }

    protected abstract void reproductionCycle();

    protected void death(){
        dead = true;
        //Add nutrients back to the ecosystem
    }

    protected Direction getRandomValidDirection(int x, int y) {
        // List of possible directions
        List<Direction> possibleDirections = new ArrayList<>();

        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);

        // Remove invalid directions
        possibleDirections.removeIf(direction -> !validateDirection(direction, x, y)); // whatever is not valid won't be used in the random function

        // If no valid directions, return NOT_MOVING
        if (possibleDirections.isEmpty()) {
            return Direction.NOT_MOVING;
        }

        // Randomly select a valid direction
        int index = random.nextInt(possibleDirections.size());

        return possibleDirections.get(index);
    }

    public boolean validateDirection(Direction dir, int x, int y) {
        if (dir == Direction.UP) {
            if (y > 0) {
                return detectCollision(level.tileData.get(y - 1).get(x));
            }
        } else if (dir == Direction.DOWN) {
            if (y < Level.levelRows - 1) {
                return detectCollision(level.tileData.get(y + 1).get(x));
            }
        } else if (dir == Direction.LEFT) {
            if (x > 0) {
                return detectCollision(level.tileData.get(y).get(x - 1));
            }
        } else if (dir == Direction.RIGHT) {
            if (x < Level.levelColumns - 1) {
                return detectCollision(level.tileData.get(y).get(x + 1));
            }
        }
        return false; // If all checks fail, the move is invalid.
    }

    protected void move(int movementSpeed) {
        if (dir == Direction.UP) {
            screenPosY -= movementSpeed;
        } else if (dir == Direction.DOWN) {
            screenPosY += movementSpeed;
        } else if (dir == Direction.LEFT) {
            screenPosX -= movementSpeed;
        } else if (dir == Direction.RIGHT) {
            screenPosX += movementSpeed;
        }
    }

    protected void updateWorldPosition() {
        worldPosX = screenPosX / ScreenSettings.TILE_SIZE;
        worldPosY = screenPosY / ScreenSettings.TILE_SIZE;
    }
}
