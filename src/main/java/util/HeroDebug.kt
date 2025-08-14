package util

import entities.NPC.Heroes.HeroList

class HeroDebug {
    companion object {


        public fun printHeroDetails() {
            val heroes = HeroList.getInstance().heroes;
            if(heroes.isEmpty()) println("No Heroes Allowed!")
            for (h in heroes.indices) {
                println("${heroes[h].name} is at:${heroes[h].tilePositionX}:${heroes[h].tilePositionY}")

            }
        }
    }

}