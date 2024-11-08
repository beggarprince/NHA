package graphics

import entities.Enemy.Direction
import level.Tile

fun detectCollision(t : Tile) : Boolean{
    return t.walkable;
}

