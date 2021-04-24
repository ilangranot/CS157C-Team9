import java.io.BufferedReader;

public class Reader {

    public static void main(String[] args) {
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader("1_22_ordered_combined_instructureHistory - 1_22_ordered_combined_instructureHistory.csv"));
        }
        catch (IOException e) {
        }
    }

}