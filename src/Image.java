import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Image {

    protected String fileName;
    protected String fileExtension;
    protected String imageType;
    protected int maxPixelValue;
    protected int x;
    protected int y;
    protected int[][] pixels;

    public Image(String filename)
    {
        this.fileName = getFilename(filename);
        this.fileExtension = getFileExtension(filename);
        this.imageType = "P1";
        this.x = 0;
        this.y = 0;
        this.maxPixelValue = 1;
        this.pixels = new int[0][0];
        try {
            tokenize(readFile(filename));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while reading file!");
        }
    }

    private String readFile(String filename)
    {
        StringBuilder fileContent = new StringBuilder();
        File file = new File("files/" + filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }
        if (scanner != null) {
            while (scanner.hasNextLine())
            {
                fileContent.append(scanner.nextLine()).append("\n");
            }
        }
        return fileContent.toString();
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

    private String nextToken(StringTokenizer st, Pattern p) throws Exception
    {
        if(st.hasMoreTokens())
        {
            String token = st.nextToken();
            if(Pattern.matches(p.pattern(), token))
            {
                return token;
            }
            else throw new Exception("Wrong token!");
        }
        else throw new Exception("End of file!");
    }

    private void tokenize(String str) throws Exception
    {
        str = str.replaceAll("#(.+)\n", "");
        Pattern imageType = Pattern.compile("([Pp])[0-9]+");
        Pattern number = Pattern.compile("[0-9]+");
        StringTokenizer st = new StringTokenizer(str);
        this.imageType = nextToken(st, imageType);
        this.x = Integer.parseInt(nextToken(st, number));
        this.y = Integer.parseInt(nextToken(st, number));
        if(!this.imageType.equals("P1"))
        {
            this.maxPixelValue = Integer.parseInt(nextToken(st, number));
        }
        else this.maxPixelValue = 1;
        this.pixels = new int[this.y][this.x];
        for(int y = 0; y < this.y; y++)
        {
            for(int x = 0; x < this.x; x++)
            {
                pixels[y][x] = Integer.parseInt(nextToken(st, number));
            }
        }
    }

    public void printImageProperties()
    {
        System.out.println("File: " + fileName + fileExtension);
        System.out.println("Image type: " + imageType);
        System.out.println("X: " + x + ", Y: " + y);
        System.out.println("Max pixel value: " + maxPixelValue);
    }
}