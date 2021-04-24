import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    public static void main(String[] args) {
        try {

            File file = new File("./src/main/java/1_22_ordered_combined_instructureHistory.csv");
            BufferedReader lineReader = new BufferedReader(new FileReader(file));
            String lineText = null;

            while( (lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                int userID = Integer.parseInt(data[0]);
                String URL = data[6];

                System.out.println(userID + ": " + URL);
            }
        }
        catch (IOException e) {
        }
    }

}