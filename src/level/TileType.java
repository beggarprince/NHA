package level;

import util.ImgLoader;

import java.awt.image.BufferedImage;


public enum TileType {
    DIRT("sprites/tile/dirt.png"),
    NUTRIENT("sprites/tile/nutrientsL1.png"),
    BRICK("sprites/tile/brick.png"),
    PATH("sprites/tile/gravel.png"),
    MANA("sprites/tile/mana.png"),
    NUTRIENT2("sprites/tile/nutrientsL2.png"),
    NUTRIENT3("sprites/tile/nutrientsL3.png");

    private BufferedImage image;

    // Enum constructor, loading the image
    TileType(String imagePath) {
            this.image = ImgLoader.getImageResource(imagePath);

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
                return getAltImage(TileType.NUTRIENT2);
            }
            return getAltImage(TileType.NUTRIENT3);
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
