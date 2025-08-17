package level;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelText {


    //TODO convert to json or yaml

    //LEVEL
        //ROUND
            //INTRO
            //HERO ENTERS
            //VICTORY
            //UPGRADE/POST VICTORY SCREEN

    private static String upgradeScreen = "It's time to upgrade your monsters Milord.";
    //Round intro, Entry, Victory, Post round
    public static ArrayList<ArrayList<ArrayList<String>>> levelTextData = new ArrayList<>(Arrays.asList(
            //Level 1
            new ArrayList<>(Arrays.asList(
                    //Round 1
                    //0 Intro
                    new ArrayList<>(Arrays.asList(
                            "Hello Milord, this is the intro to the game. Press ENTER to continue",
                            "Press WASD for cursor movement, use this to navigate your surroundings",
                            "I am asking for your assistance, Milord. A group of heroes will come looking for me, use these monsters to defend against them",
                            "What's that? Your maze is just 1x3 a hallway!\nYou're right! It's time to start digging!",
                            "Press F to dig on tile blocks, this will destroy them. Colored blocks spawn monsters.",
                            "But be careful, you only have a set amount of dig power, so don't waste it!",
                            "If I get pulled out of the dungeon that's game over"
                    )),
                    //1 Entry
                    new ArrayList<>(Arrays.asList(
                            "It's time Milord, the first Heroes are coming. Press ENTER to continue",
                            "They'll get progressively stronger and come in bigger groups in the future",
                            "For now we have 2 heroes coming, a Warrior and a Priest",
                            "The Warrior hits people, hit him back!",
                            "The Priest prefers to heal and cast spells, hit her as well!",
                            "You can still dig in the maze and spawn more monsters even as they wander around, use this to jump them!",
                            "Generally, heroes can only attack a single monster at a time. That means free strikes for any of the other monsters surrounding them!"
                    )),
                    //2 Victory
                    new ArrayList<>(Arrays.asList(
                            "This is the victory screen, you've made it past round 1! Press ENTER to continue",
                            "The heroes get progressively stronger, make sure to upgrade your monsters",
                            "If it's implemented, that is.",
                            "The upgrade screen, I mean. The next heroes are definitely stronger",
                            "A Warrior, a Mage, and a Joker are up next."
                    )),
                    //3 Post round
                    new ArrayList<>(Arrays.asList(
                            "Ha! You sure showed them Milord!",
                            "You'll be granted additional dig power at the end of rounds based on performance",
                            "You can also use this to immediately upgrade your monsters"
                    )),

                    //TODO use yaml or something or a bash tool to just edit the text easily this is bad
                    //Round 2
                    //Intro
                    new ArrayList<>(Arrays.asList(
                            "Those heroes were some real chumps, these next Heroes will be stronger than the last",
                            "I hear one of them can cast fireball spells",
                            "Much cooler than the stupid heal that priest had",
                            "Look at the skulls in the tiles in which they fell! The dungeon consumes all!",
                            "You can \"Dig\" on the skeleton and spawn a Skeleton Warrior!",
                            "These warriors are stronger than lower level monsters and do not need to eat to survive!",
                            "Use them for pinch situations when a hero is close by"
                    )),
                    //Entry
                    new ArrayList<>(Arrays.asList(
                            "It's time Milord, the next Heroes are coming. Press ENTER to continue"
                    )),
                    //Victory
                    new ArrayList<>(Arrays.asList(
                            "This is the victory screen, you've made it past round 2! Press ENTER",
                            "No Milord, I don't say something unique every time you accomplish something",
                            "True love is comfortable silence",
                            "...",
                            "That's what my wife says"
                    )),
                    //Post round
                    new ArrayList<>(Arrays.asList(
                            upgradeScreen // We have nothing unique to say
                    )),

                    //
                    //Round 3
                    //Intro
                    new ArrayList<>(Arrays.asList(
                            "This is the third and final level of our Beta Test",
                            "Yes this is still just a Beta, we do not hold all of the rights to the assets.",
                            "The next hero is a warrior, but special.",
                            "She combines the class of Warrior and Mage. I call it a \"Magior\"!",
                            "...",
                            "The general public calls it a Dual-Class, this one in particular Mage-Knight",
                            "The Hero System allows intricate inheritance!",
                            "But they die all the same!"
                    )),
                    //Entry
                    new ArrayList<>(Arrays.asList(
                            "This is our final test, Milord!",
                            "It was a pleasure to work with you!",
                            "Exhaust all of your dig power, we can't take it to the grave"
                    )),
                    //Victory
                    new ArrayList<>(Arrays.asList(
                            "That was a tough one, Milord!",
                            "But you sure showed that Mage-Knight who's the boss",
                            "Those were all the heroes that responded to the call, for now we can rest",
                            "We'll have to relocate to a different dungeon. This one has been compromised",
                            "What's that? You've worked so hard on this one?",
                            "...",
                            "I'll find you another one just like it, Milord, filled with dirt and...",
                            "Well that's about it, isn't it?"
                    )),
                    //Post round
                    //End of level
                    new ArrayList<>(Arrays.asList(
                            "Victory type shift, Milord. That's what the kids say.",
                            "Keep a close watch on the Github Repo, we update it frequently"
                    ))

            )),
            //End of level 1

            ///
            //Level 2
            new ArrayList<>(Arrays.asList(
                    //Round 1
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    )),
                    //Round 2
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    )),
                    //Round 3
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    )),
                    //Round 4 (Victory)
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    ))
            )),//End of level 3
            //Level 0
            new ArrayList<>(Arrays.asList(
                    //Round 1
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    )),
                    //Round 2
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    )),
                    //Round 3
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    )),
                    //Round 4 (Victory)
                    new ArrayList<>(Arrays.asList(
                            "",
                            "",
                            ""
                    ))
            ))//End of level 3

    ));//End of list


}
