package level;

//All level loader will do is load in a TXT file, csv or not idk
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Just fetches a csv and puts it's data into a vector like structure
//Solely a utility function
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

        //WORKS
        // Print the stored 2D array
//        for (ArrayList<Integer> row : data) {
//            for (Integer value : row) {
//                System.out.print(value + " ");
//            }
//            System.out.println();  // Newline after each row
//        }
        return data;
    }
}
