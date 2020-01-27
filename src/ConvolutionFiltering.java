import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class ConvolutionFiltering {

    private final Filter filter;
    private final Image img;
    private final int[][] filteredImage;

    public ConvolutionFiltering(Image i, Filter f)
    {
        this.img = i;
        this.filter = f;
        this.filteredImage = new int[i.x][i.y];
    }

    private void corners()
    {
        int [][] upperLeft = {{0, 0, 0,},
                             {0, img.pixels[0][0], img.pixels[0][1]},
                             {0, img.pixels[1][0], img.pixels[1][1]}};
        int [][] upperRight = {{0, 0, 0,},
                              {img.pixels[0][img.x - 2], img.pixels[0][img.x - 1], 0},
                              {img.pixels[1][img.x - 2], img.pixels[1][img.x - 1], 0}};
        int [][] bottomLeft = {{0, img.pixels[img.y - 2][0], img.pixels[img.y - 2][1]},
                              {0, img.pixels[img.y - 1][0], img.pixels[img.y - 1][1]},
                              {0, 0, 0}};
        int [][] bottomRight = {{img.pixels[img.y - 2][img.x - 2], img.pixels[img.y - 2][img.x - 1], 0},
                                {img.pixels[img.y - 1][img.x - 2], img.pixels[img.y - 1][img.x - 1], 0},
                                {0, 0, 0}};

        filteredImage[0][0] = getNewValue(upperLeft);
        filteredImage[img.x - 1][0] = getNewValue(upperRight);
        filteredImage[0][img.y - 1] = getNewValue(bottomLeft);
        filteredImage[img.x - 1][img.y - 1] = getNewValue(bottomRight);
    }

    private void sides()
    {
        for(int y = 1; y < img.y - 1; y++){
            int x = 0;
            int[][] leftSide = {{0, img.pixels[y - 1][x], img.pixels[y][x + 1]},
                                {0, img.pixels[y][x], img.pixels[y][x + 1]},
                                {0, img.pixels[y + 1][x], img.pixels[y + 1][x + 1]}};

            filteredImage[x][y] = getNewValue(leftSide);
        }

        for(int x = 1; x < img.x - 1; x++)
        {
            int y = 0;
            int[][] topSide = {{0, 0, 0},
                    {img.pixels[y][x - 1], img.pixels[y][x], img.pixels[y][x + 1]},
                    {img.pixels[y + 1][x - 1], img.pixels[y + 1][x], img.pixels[y + 1][x + 1]}};

            filteredImage[x][y] = getNewValue(topSide);
        }

        for(int y = 1; y < img.y - 1; y++)
        {
            int x = img.x - 2;
            int[][] rightSide = {{img.pixels[y - 1][x], img.pixels[y - 1][x + 1], 0},
                    {img.pixels[y][x], img.pixels[y][x + 1], 0},
                    {img.pixels[y + 1][x], img.pixels[y + 1][x + 1], 0}};

            filteredImage[x][y] = getNewValue(rightSide);
        }

        for(int x = 1; x < img.x - 1; x++)
        {
            int y = img.y - 2;
            int[][] botSide = {{img.pixels[y][x - 1], img.pixels[y][x], img.pixels[y][x + 1]},
                    {img.pixels[y + 1][x - 1], img.pixels[y + 1][x], img.pixels[y + 1][x + 1]},
                    {0, 0, 0}};

            filteredImage[x][y] = getNewValue(botSide);
        }
    }

    private int[][] getSquare(int x, int y)
    {
        return new int[][]{{img.pixels[y - 1][x - 1], img.pixels[y - 1][x], img.pixels[y - 1][x + 1]},
                          {img.pixels[y][x - 1], img.pixels[y][x], img.pixels[y][x + 1]},
                          {img.pixels[y + 1][x - 1], img.pixels[y + 1][x], img.pixels[x + 1][x + 1]}};
    }

    private int getNewValue(int[][] arr)
    {
        int sum = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(filter.filter[i][j] == 1)
                {
                    sum += arr[i][j];
                }
            }
        }
        return sum;
    }

    public void filter() throws FileNotFoundException, UnsupportedEncodingException
    {
        System.out.println();
        corners();
        sides();
        for (int i = 1; i < img.y - 2; i++)
        {
            for(int j = 1; j < img.x - 2; j++)
            {
                filteredImage[j][i] = getNewValue(getSquare(j, i));
            }
        }

        System.out.println("Result of filtering:");
        for(int i = 0; i < img.y; i++)
        {
            for(int j = 0; j < img.x; j++)
            {
                System.out.print(filteredImage[j][i] + " ");
            }
            System.out.println();
        }
        saveImage();
    }

    private void saveImage() throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter("files/filtered" + img.filename + ".pbm", "UTF-8");
        writer.println(img.name);
        writer.println("# Filtered image");
        writer.println(img.x + " " + img.y);
        for(int i = 0; i < img.y; i++)
        {
            for(int j = 0; j < img.x; j++)
            {
                writer.print(filteredImage[j][i] + " ");
            }
            writer.println();
        }
        writer.close();
    }
}
