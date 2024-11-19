package PlayerActions;

import level.TileType;
import level.Tile;

public class Dig {

    public static void dig(Tile tile) {
        if (tile.type == TileType.PATH) return;

        try {
            tile.digDestruct();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: Position is out of bounds.");
        } catch (NullPointerException e) {
            System.err.println("Error: levelData or a nested ArrayList is null.");
        }
        if (tile.type != TileType.PATH) {
            System.out.println("Dig did not change value despite not being PATH");
        }
    }
}
