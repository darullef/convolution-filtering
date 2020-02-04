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
        this.filteredImage = new int[i.y][i.x];
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
        filteredImage[img.y - 1][0] = getNewValue(upperRight);
        filteredImage[0][img.x - 1] = getNewValue(bottomLeft);
        filteredImage[img.y - 1][img.x - 1] = getNewValue(bottomRight);
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

            filteredImage[y][x] = getNewValue(topSide);
        }

        for(int y = 1; y < img.y - 1; y++)
        {
            int x = img.x - 1;
            int[][] rightSide = {{img.pixels[y - 1][x - 1], img.pixels[y - 1][x], 0},
                                 {img.pixels[y][x - 1],     img.pixels[y][x],     0},
                                 {img.pixels[y + 1][x - 1], img.pixels[y + 1][x], 0}};

            filteredImage[y][x] = getNewValue(rightSide);
        }

        for(int x = 1; x < img.x - 1; x++)
        {
            int y = img.y - 1;
            int[][] botSide = {{img.pixels[y - 1][x - 1], img.pixels[y - 1][x], img.pixels[y - 1][x + 1]},
                               {img.pixels[y][x - 1], img.pixels[y][x], img.pixels[y][x + 1]},
                               {0, 0, 0}};

            filteredImage[y][x] = getNewValue(botSide);
        }
    }

    private int[][] getSquare(int x, int y)
    {
        return new int[][]{{img.pixels[y - 1][x - 1], img.pixels[y - 1][x], img.pixels[y - 1][x + 1]},
                          {img.pixels[y][x - 1], img.pixels[y][x], img.pixels[y][x + 1]},
                          {img.pixels[y + 1][x - 1], img.pixels[y + 1][x], img.pixels[y + 1][x + 1]}};
    }

    private int getNewValue(int[][] arr)
    {
        int value = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                double f = filter.filter[i][j];
                int a = arr[i][j];
                int acc = (int)(f * a);
                value += acc;
            }
        }
        if(img.maxPixelValue != 1 && value > img.maxPixelValue)
        {
            return img.maxPixelValue;
        }
        else return Math.max(value, 0);
    }

    public void filter() throws FileNotFoundException, UnsupportedEncodingException
    {
        System.out.println();
        corners();
        sides();
        for (int y = 1; y < img.y - 2; y++)
        {
            for(int x = 1; x < img.x - 1; x++)
            {
                filteredImage[y][x] = getNewValue(getSquare(x, y));
            }
        }
        saveImage();
    }

    private void saveImage() throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintWriter writer = new PrintWriter("files/filtered_" + img.fileName + img.fileExtension, "UTF-8");
        writer.println(img.imageType);
        writer.println("# Filtered image");
        writer.println(img.x + " " + img.y);
        if(!img.imageType.equals("P1"))
        {
            writer.println(img.maxPixelValue);
        }
        for(int y = 0; y < img.y; y++)
        {
            for(int x = 0; x < img.x; x++)
            {
                writer.print(filteredImage[y][x] + " ");
            }
            writer.println();
        }
        writer.close();
        System.out.println("Result saved to file!");
    }
}
