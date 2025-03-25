package main.java.level;

import main.java.util.ImgLoader;

import java.awt.image.BufferedImage;


public enum TileType {
    DIRT("sprites/tile/dirt.png"),
    NUTRIENT("sprites/tile/nutrientsL1.png"),
    BRICK("sprites/tile/brick.png"),
    PATH("sprites/tile/gravel.png"),
    MANA("sprites/tile/manaL1.png"),
    MANA2("sprites/tile/manaL2.png"),
    MANA3("sprites/tile/manaL3.png"),
    NUTRIENT2("sprites/tile/nutrientsL2.png"),
    NUTRIENT3("sprites/tile/nutrientsL3.png");

    private BufferedImage image;

    // Enum constructor, loading the image
    TileType(String imagePath) {
            this.image = ImgLoader.getImageResource(imagePath);

    }

    // Method to get the image associated with the tile type
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
        else if (t.type == TileType.MANA){
            if(t.getMana() <= t.manaL2Min){
                return image;
            }
            else if(t.getMana() > t.manaL2Min && t.getMana() < t.manaL3Min){
                // System.out.println("Level 2 nutrients");
                return getAltImage(TileType.MANA2);
            }
            return getAltImage(TileType.MANA3);
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
