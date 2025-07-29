package entities.NPC.Monsters.MonsterLogic;

public class DungeonIQ {
    DungeonIQ instance;
    private int iq = 0;

    private DungeonIQ(){

    }

    public DungeonIQ getInstance() {
        if(instance == null) {
            instance = new DungeonIQ();
        }
        return instance;
    }

    public int getIq(){
        return iq;
    }
    public void increaseIq(){
        iq++;
    }
    public void decreaseIq(){
        if(iq != 0) iq--;
    }

}
