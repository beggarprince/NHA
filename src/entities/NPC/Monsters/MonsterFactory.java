package entities.NPC.Monsters;


public class MonsterFactory {

/*
* Handles the creation of enemies and returns them
* Does not handle EnemyList anymore
* */
//Made as static instead of singleton
    public static Monster createMonster(String type, int posx, int posy) {

        if (type.equals("Slime")) {
            return new Slime(posx, posy);
        }
        else if(type.equals("Bug")){
            return new Bug(posx, posy);
        }
        else if(type.equals("Lizard")){
            return new LizardMan(posx, posy);
        }

        else if(type.equals("Spirit")){
            return new Spirit(posx, posy);
        }
        else if(type.equals("Slime_Flower")){
            return new Slime_Flower(posx, posy);
        }
        else{//This should not be here but there ought to be a return for nothing
            System.out.println("FACTORY ERROR");
            return  new Slime(0, 0);
        }

    }
}
