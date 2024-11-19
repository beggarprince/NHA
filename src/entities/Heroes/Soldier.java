package entities.Heroes;

import graphics.ImgLoader;

public class Soldier extends Hero{


    public Soldier(int health, int x, int y){
        super(health, x, y);
        System.out.println(worldPositionX + " " + worldPositionY);
        this.image = ImgLoader.getImageResource("knight.png");
    }

    @Override
    public void behavior() {
        //Ideally something
    }

    @Override
    public void destroy() {
        image = null;
    }
}
