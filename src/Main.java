import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException
    {
        Image img = new Image("j.pbm");
        img.printImageProperties();
        int[] arr = {1, 1, 1,
                     1, 1, 1,
                     1, 1, 1};
        Filter filter = new Filter(arr);
        ConvolutionFiltering cf1 = new ConvolutionFiltering(img, filter);
        cf1.filter();
    }
}
