import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Integer> parseInput(String fileName) {
        return convertStreamToList(getFileFromResourceAsStream(fileName));
    }


    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    private static List<Integer> convertStreamToList(InputStream is) {
        List<Integer> result = new ArrayList<>();
        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                result.add(Integer.parseInt(line));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
