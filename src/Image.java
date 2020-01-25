import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Image {

    private String name;
    private String comment;
    protected int x;
    protected int y;
    protected int[][] pixels;

    public Image(String filename) throws IOException
    {
        String[] imageProperties = tokenize(readFile(filename));
        this.name = imageProperties[0];
        this.comment = imageProperties[1];
        this.x = Integer.parseInt(imageProperties[2]);
        this.y = Integer.parseInt(imageProperties[3]);
        this.pixels = createPixelsMatrix(imageProperties[4]);
    }

    private String readFile(String filename) throws IOException
    {
        StringBuilder fileContent = new StringBuilder();
        File file = new File("files/" + filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            fileContent.append(scanner.nextLine()).append(" ");
        }
        return fileContent.toString();
    }

    private String[] tokenize(String str)
    {
        String[] infos = {"", "", "", "", ""};
        int counter = 0;
        Pattern name = Pattern.compile("([Pp])[0-9]+");
        Pattern number = Pattern.compile("[0-9]+");
        StringTokenizer st = new StringTokenizer(str);
        while(st.hasMoreTokens())
        {
            String token = st.nextToken();
            if(Pattern.matches(name.pattern(), token))
            {
                infos[0] = token;
            }
            else if(Pattern.matches(number.pattern(), token))
            {
               if(counter == 0)
               {
                   infos[2] = token;
                   counter++;
               }
               else if(counter == 1)
               {
                   infos[3] = token;
                   counter++;
               }
               else
               {
                   infos[4] += token;
               }
            }
            else
            {
                infos[1] += token + " ";
            }
        }
        return infos;
    }

    private int[][] createPixelsMatrix(String input)
    {
        String[] pixels = input.split("");
        int[][] matrix = new int[this.y][this.x];
        for(int i = 0; i < this.y; i++)
        {
            for(int j = 0; j < this.x; j++)
            {
                matrix[i][j] = Integer.parseInt(pixels[j + i * this.x]);
            }
        }
        return matrix;
    }

    public void printImageProperties()
    {
        System.out.println(name);
        System.out.println(comment);
        System.out.println("x: " + x + ", y: " + y);
        for(int i = 0; i < this.y; i++)
        {
            for(int j = 0; j < this.x; j++)
            {
                System.out.print(pixels[i][j] + " ");
            }
            System.out.println();
        }
    }
}