package util

import level.Tile

fun detectCollision(t : Tile) : Boolean{
    return t.walkable;
}

