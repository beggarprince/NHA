package entities.Enemy;


public class EnemyFactory {

/*
* Handles the creation of enemies and returns them
* Does not handle EnemyList anymore
* */

    public Enemy createEnemy(String type, int posx, int posy) {

        if (type.equals("Slime")) {
            Slime slime = new Slime(posx, posy);
            return slime;
        }

        else if(type.equals("Spirit")){
            Spirit spirit = new Spirit(posx, posy);
            return spirit;
        }
        else{//This should not be here but there ought to be a return for nothing
            System.out.println("FACTORY ERROR");
            return  new Slime(0, 0);
        }

    }
}
