import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Image img = new Image("baboon.pgm");
        img.printImageProperties();
        //possible filters: IDENTITY, LIGHT_OUTLINE, OUTLINE, STRONG_OUTLINE, SHARPEN, BLUR, EMBOSS
        Filter filter = new Filter(Filter.filterType.SHARPEN);
        ConvolutionFiltering cf1 = new ConvolutionFiltering(img, filter);
        cf1.filter();
    }
}
