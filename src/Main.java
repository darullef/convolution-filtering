import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException
    {
        Image img = new Image("casablanca.pgm");
        img.printImageProperties();
        int[] arr = {0, -1, 0,
                     -1, 5, -1,
                     0, -1, 0};
        Filter filter = new Filter(arr);
        ConvolutionFiltering cf1 = new ConvolutionFiltering(img, filter);
        cf1.filter();
    }
}
