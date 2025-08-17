package entities.NPC.Monsters.MonsterLogic;

import entities.NPC.NPCType;

import java.util.EnumMap;
import java.util.Map;

public  class DungeonIQ {
    //DungeonIQ instance;
    private static int iq = 0;



//    private DungeonIQ(){
//
//    }
//
//    public DungeonIQ getInstance() {
//        if(instance == null) {
//            instance = new DungeonIQ();
//        }
//        return instance;
//    }

    public static int getIq(){
        return iq;
    }
    public static void increaseIq(){
        iq++;
    }
    public static void decreaseIq(){
        if(iq != 0) iq--;
    }


    // Using EnumMap approach (recommended - similar to your sprite animations)
    private static final Map<NPCType, NPCState> MONSTER_STATE = new EnumMap<>(NPCType.class);



    public static class NPCState {
        private int speciesBaseAttack =1;
        private int speciesBaseHP = 1;
        private int upgradeCost = 1;
        //Could probably add an enum in here to add different properties

        NPCState( int baseHP, int baseAttack, int upgradeCost ){
            this.speciesBaseHP = baseHP;
            this.speciesBaseAttack = baseAttack;
        }

        public int getBaseAtt(){
            return speciesBaseAttack;
        }

        public int getBaseHp(){
            return speciesBaseHP;
        }

        public void upgradeAttack(int amountIncrease){
            speciesBaseAttack += amountIncrease;
        }

        public void upgradeHp(int amountIncrease){
            speciesBaseHP += amountIncrease;
        }

        public int getUpgradeCost() {
            return upgradeCost;
        }

        public void setUpgradeCost(int upgradeCost) {
            this.upgradeCost = upgradeCost;
        }
    }

    static {
        // Initialize the NPC states map

        //Nutrient Family
        MONSTER_STATE.put(NPCType.Slime, new NPCState(8, 1, 100));
        MONSTER_STATE.put(NPCType.Slime_Flower, new NPCState(8, 0, 100));
        MONSTER_STATE.put(NPCType.Bug, new NPCState(24, 8, 250));
        MONSTER_STATE.put(NPCType.LizardMan, new NPCState(48, 16, 450));

        //Mana Family
        MONSTER_STATE.put(NPCType.Dragon, new NPCState(300, 34, 700));
        MONSTER_STATE.put(NPCType.Lilith, new NPCState(24, 8, 500));
        MONSTER_STATE.put(NPCType.Spirit, new NPCState(8, 1, 200));

        MONSTER_STATE.put(NPCType.Skeleton, new NPCState(30, 30, 0));
        MONSTER_STATE.put(NPCType.NULL_TYPE, new NPCState(8, 1, 0));
    }

    // Method to get NPC state
    //technically npcs, but this should affect heroes so the naming should help
    public static NPCState getMonsterState(NPCType npcType) {
        return MONSTER_STATE.get(npcType);
    }


}
