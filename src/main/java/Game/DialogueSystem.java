package main.java.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DialogueSystem {

    final static int max = 50;
    final static int buffer = 4;

    Font font = new Font("Arial", Font.PLAIN, 24);
    final static Font l1 = new Font("Arial", Font.PLAIN, 24);
    //This will use some static strings broken up by
        //LEVEL
            //STAGE
                    //PRE MATCH HYPE AND TIPS
                        //WIN/LOSS

    public List<String> createDialogueTree(String S, Graphics g) {
        LinkedList<String> segments = new LinkedList<>();
        FontMetrics m = g.getFontMetrics(font);

        // Split at newlines to allow for hardcoded breaks
        segments.addAll(Arrays.asList(S.split("\n")));

        // Process each segment, breaking it at most once per iteration
        int i = 0;
        while (i < segments.size()) {
            String segmentString = segments.get(i);

            if (m.stringWidth(segmentString) > max) {

                int breakPos = -1;
                boolean brokenAtSpace = false;

                // Look for a space to break at within the buffer distance from max
                for (int j = segmentString.length() - 1; j >= 0; j--) {

                    //take of a char and see if it fits
                    String sub = segmentString.substring(0, j);

                    //As soon as we're at <= max we create the break
                    if (m.stringWidth(sub) <= max) {
                        breakPos = j;
                        if (segmentString.charAt(j) == ' ') {
                            brokenAtSpace = true;
                        }

                        //We are breaking on a char NOT whitespace
                        else{
                            for(int k = j-1; k >= (j-buffer); k--){
                                if(segmentString.charAt(k) == ' '){
                                    breakPos = k;
                                    brokenAtSpace = true;
                                    break;
                                }
                                //If we're here we could not break at space within the buffer
                                //and thus need to add hyphens instead,
                                //if we do it here we can remove boolean/but tbh this flows okay
                            }
                        }

                        break;
                    }
                }

                //create breaks
                String cutSegment = segmentString.substring(0, breakPos);
                String remainingSegment = segmentString.substring(breakPos+1); // -1?,

                // it seems that with one argument it just does from i to the end of the string, but we'll see
                //Add hyphens
                if(!brokenAtSpace){
                    cutSegment = cutSegment + "-";
                     remainingSegment = "-" + remainingSegment;
                }

                segments.set(i, cutSegment);
                segments.add(i+1, remainingSegment);

             //End of breaking
            }
            i++;

            //End of while loop iteration
        }//End of while

        return segments;
    }

}
