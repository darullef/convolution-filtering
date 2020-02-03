import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Image {

    protected String fileName;
    protected String fileExtension;
    protected String imageType;
    protected String comment;
    protected int maxPixelValue;
    protected int x;
    protected int y;
    protected int[][] pixels;

    public Image(String filename) throws IOException
    {
        String[] imageProperties = read(filename);
        this.fileName = getFilename(filename);
        this.fileExtension = getFileExtension(filename);
        this.imageType = imageProperties[0];
        this.comment = imageProperties[1];
        this.x = Integer.parseInt(imageProperties[2]);
        this.y = Integer.parseInt(imageProperties[3]);
        this.maxPixelValue = Integer.parseInt(imageProperties[4]);
        this.pixels = createPixelsMatrix(imageProperties[5]);
    }

    private String getFilename(String filename)
    {
        Pattern p = Pattern.compile("(.*)\\.[^.]+$");
        Matcher matcher = p.matcher(filename);
        if(matcher.find())
        {
            return matcher.group(1);
        }
        else return "image";
    }

    private String getFileExtension(String filename)
    {
        Pattern p = Pattern.compile("[.][a-z]*");
        Matcher matcher = p.matcher(filename);
        if(matcher.find())
        {
            return matcher.group(0);
        }
        else return ".txt";
    }

    private String readFile(String filename) throws IOException
    {
        StringBuilder fileContent = new StringBuilder();
        File file = new File("files/" + filename);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            fileContent.append(scanner.nextLine()).append("\n");
        }
        return fileContent.toString();
    }

    private String[] tokenize(String str)
    {
        String[] infos = {"", "", "", "", ""};
        int counter = 0;

        Pattern comment = Pattern.compile("#.+\n");
        Matcher matcher = comment.matcher(str);
        if(matcher.find())
        {
            infos[1] = matcher.group(0);
        }
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
        }
        return infos;
    }

    private String[] read(String filename) throws FileNotFoundException
    {
        String[] infos = {/* imageType */ "",
                          /* comment */ "",
                          /* x */ "",
                          /* y */ "",
                          /* maxPixelValue */ "0",
                          /* pixels */ ""};
        int counter = 0;
        File file = new File("files/" + filename);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine())
        {
            if(counter == 0)
            {
                infos[0] = scanner.nextLine();
                counter++;
            }
            else if(counter == 1)
            {
                infos[1] = scanner.nextLine();
                counter++;
            }
            else if(counter == 2)
            {
                String[] dimensions = scanner.nextLine().split(" ");
                infos[2] = dimensions[0];
                infos[3] = dimensions[1];
                if(infos[0].equals("P2") || infos[0].equals("P3"))
                {
                    counter = 2137;
                }
                else counter++;
            }
            else if(counter == 2137)
            {
                infos[4] = scanner.nextLine();
                counter = 3;
            }
            else if(counter == 3)
            {
                while(scanner.hasNextLine())
                {
                    infos[5] += scanner.nextLine() + " ";
                }
            }
        }
        return infos;
    }

    private int[][] createPixelsMatrix(String input)
    {
        String[] pixels = input.split(" ");
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
        System.out.println(fileName + fileExtension);
        System.out.println(imageType);
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