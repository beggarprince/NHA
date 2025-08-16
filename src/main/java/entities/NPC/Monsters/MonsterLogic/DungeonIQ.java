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
    private static final Map<NPCType, NPCState> NPC_STATES = new EnumMap<>(NPCType.class);



    public static class NPCState {
        private int speciesBaseAttack =1;
        private int speciesBaseHP = 1;
        //Could probably add an enum in here to add different properties

        NPCState( int baseHP, int baseAttack ){
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

    }

    static {
        // Initialize the NPC states map

        //Nutrient Family
        NPC_STATES.put(NPCType.Slime, new NPCState(8, 1));
        NPC_STATES.put(NPCType.Slime_Flower, new NPCState(8, 0));
        NPC_STATES.put(NPCType.Bug, new NPCState(24, 8));
        NPC_STATES.put(NPCType.LizardMan, new NPCState(48, 16));

        //Mana Family
        NPC_STATES.put(NPCType.Dragon, new NPCState(300, 34));
        NPC_STATES.put(NPCType.Lilith, new NPCState(24, 8));
        NPC_STATES.put(NPCType.Spirit, new NPCState(8, 1));

        NPC_STATES.put(NPCType.Skeleton, new NPCState(30, 30));
        NPC_STATES.put(NPCType.NULL_TYPE, new NPCState(8, 1));
    }

    // Method to get NPC state
    public static NPCState getNPCState(NPCType npcType) {
        return NPC_STATES.get(npcType);
    }


}
