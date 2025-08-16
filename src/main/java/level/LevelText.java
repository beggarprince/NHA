package level;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelText {



    public static ArrayList<ArrayList<ArrayList<String>>> levelTextData = new ArrayList<>(Arrays.asList(
            //Level 1
            new ArrayList<>(Arrays.asList(
                    //Intro/Welcome
                    new ArrayList<>(Arrays.asList(
                            "Hello Milord, this is the intro to the game. Press ENTER to progress",
                            "This should show before the logic runs",
                            "Though, this is not the question array. This is the static test array, and this text message is extra long to test our state of the art text splitter. This should be separate text windows, each requiring a press of enter! Haha, neat. Presumably, if it worked."
                    )),
                    //Round 1
                    new ArrayList<>(Arrays.asList(
                            "This is the victory screen, you've made it past round 1! Press ENTER",
                            "The heroes get progressively stronger, make sure to upgrade your monsters",
                            "If it's implemented, that is."
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
            )),//End of level 1
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
