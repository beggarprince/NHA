package level;
//All   level loader will do is load in a TXT file, csv or not idk
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Just fetches a csv and returns an array of arrays of ints
//ATP i could use it for tutorial or challenges or testing levels, since i like generating the levels more than i like making them
public class LevelLoader {

    public static ArrayList<ArrayList<Integer>> getLevelData(String filePath) {

        String line;
        String delimiter = ",";  // CSV delimiter is a comma

        // Create an ArrayList of ArrayLists to store the 2D data
        ArrayList<ArrayList<Integer>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                // Split the line by commas into an array of string values
                String[] values = line.split(delimiter);

                // Create an ArrayList to store the row data
                ArrayList<Integer> row = new ArrayList<>();

                // Convert each value to an integer and add it to the row
                for (String value : values) {
                    row.add(Integer.parseInt(value));
                }

                // Add the row to the 2D data array
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
