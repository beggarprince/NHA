package Game;

import entities.NPC.Monsters.Monster;
import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import entities.NPC.NPCType;

import java.util.EnumMap;

public class UpgradeShop {

    //6 upgrades for the 3 base classes
    private static int cursorPosition = 0;

    public static int getCursorPosition() {
        return cursorPosition;
    }

    //0 - 5, 0 being top position closer to 0 on the y-axis
    public static void setCursorPosition(MenuControl input) {
        if (input == MenuControl.Up) {
            cursorPosition--;
        } else if (input == MenuControl.Down) {
            cursorPosition++;
        }
    }

    //Player will have to choose something, if they choose something they can't afford
    //then we'll return the dp and check if dp==dp
    //If return is dp+1 we won't set the dp to this but we'll know that the player did not upgrade
    public static int upgradeMonster(int digPower) {
        switch (cursorPosition) {
            case 0:
                var slime = DungeonIQ.getMonsterState(NPCType.Slime);
                if(slime.getUpgradeCost() <= digPower){
                    digPower -= slime.getUpgradeCost();
                    slime.upgradeAttack(10);
                    slime.upgradeHp(10);
                    return digPower;
                }
                return 0;
            case 1:
                var bug = DungeonIQ.getMonsterState(NPCType.Bug);
                if(bug.getUpgradeCost() <= digPower){
                    digPower -= bug.getUpgradeCost();
                    bug.upgradeAttack(10);
                    bug.upgradeHp(10);
                    return digPower;
                }
                return 0;
            case 2:
                var lizard = DungeonIQ.getMonsterState(NPCType.LizardMan);
                if(lizard.getUpgradeCost() <= digPower){
                    digPower -= lizard.getUpgradeCost();
                    lizard.upgradeAttack(10);
                    lizard.upgradeHp(10);
                    return digPower;
                }
                return 0;
            case 3:
                var spirit = DungeonIQ.getMonsterState(NPCType.Spirit);
                if(spirit.getUpgradeCost() <= digPower){
                    digPower -= spirit.getUpgradeCost();
                    spirit.upgradeAttack(10);
                    spirit.upgradeHp(10);
                    return digPower;
                }
                return 0;
            case 4:
                var lilith = DungeonIQ.getMonsterState(NPCType.Lilith);
                if(lilith.getUpgradeCost() <= digPower){
                    digPower -= lilith.getUpgradeCost();
                    lilith.upgradeAttack(10);
                    lilith.upgradeHp(10);
                    return digPower;
                }
                return 0;
            case 5:
                var dragon = DungeonIQ.getMonsterState(NPCType.Dragon);
                if(dragon.getUpgradeCost() <= digPower){
                    digPower -= dragon.getUpgradeCost();
                    dragon.upgradeAttack(10);
                    dragon.upgradeHp(50);
                    return digPower;
                }
                return 0;
            case 6:
                return ++digPower;
            default:
                System.out.println("Error with cursor position");
                break;
        }
        return digPower;
    }

}

