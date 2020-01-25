import java.util.Arrays;

public class ConvolutionFiltering {

    private Filter filter;
    private Image img;
    private int[][] filteredImage;

    public ConvolutionFiltering(Image i, Filter f)
    {
        this.img = i;
        this.filter = f;
        this.filteredImage = new int[i.x][i.y];
    }

    public void Corners()
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
    }
}
