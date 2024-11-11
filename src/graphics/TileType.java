package graphics;

import level.Tile;

import java.awt.image.BufferedImage;


public enum TileType {
    DIRT("dirt.png"),
    NUTRIENT("nutrientsL1.png"),
    BRICK("brick.png"),
    PATH("gravel.png"),
    MANA("mana.png"),
    NUTRIENTL2("nutrientsL2.png"),
    NUTRIENTL3("nutrientsL3.png");

    private BufferedImage image;

    // Enum constructor, loading the image
    TileType(String imagePath) {
            this.image = imgLoader.getImageResource(imagePath);

    }

    // Method to get the image associated with the tile type
    //TODO This broke the purpose of TileType only returning the correct image in an elegant manner, it needs to be moved to Tile class
    public BufferedImage getImage(Tile t) {
        if(t.type == TileType.NUTRIENT){
            if(t.getNutrients() <= t.nutrientL2Min){
                return image;
            }
            else if (t.getNutrients() > t.nutrientL2Min && t.getNutrients() < t.nutrientL3Min){
               // System.out.println("Level 2 nutrients");
                return getAltImage(TileType.NUTRIENTL2);
            }
            return getAltImage(TileType.NUTRIENTL3);
        }

        //ATP all tiles without alts simply get returned
        return image;
    }

    private BufferedImage getAltImage(TileType t){
        return t.image;
    }

    public BufferedImage getImage(){
        return image;
    }

}
