import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Image {

    private static String name;
    private static String comment;
    private static int[] xy = new int[2];
    private static int[][] pixels;

    public Image(String filename) throws IOException
    {
        System.out.println(readFile(filename));

    }

    private static String readFile(String filename) throws IOException
    {
        return new String(String.valueOf(Files.readAllLines(Paths.get("Files/" + filename))));
    }
}
