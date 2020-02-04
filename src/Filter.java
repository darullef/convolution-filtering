public class Filter {

    public enum filterType {IDENTITY, LIGHT_OUTLINE, OUTLINE, STRONG_OUTLINE, SHARPEN, BLUR, EMBOSS}
    protected double[][] filter;

    public Filter(filterType f)
    {
        switch (f)
        {
            case LIGHT_OUTLINE:
                this.filter = new double[][]{{1, 0, -1},
                                             {0, 0, 0},
                                             {-1, 0, 1}};
                break;
            case OUTLINE:
                this.filter = new double[][]{{0, 1, 0},
                                             {1, -4, 1},
                                             {0, 1, 0}};
                break;
            case STRONG_OUTLINE:
                this.filter = new double[][]{{-1, -1, -1},
                                             {-1, 8, -1},
                                             {-1, -1, -1}};
                break;
            case SHARPEN:
                this.filter = new double[][]{{0, -1, 0},
                                             {-1, 5, -1},
                                             {0, -1, 0}};
                break;
            case BLUR:
                this.filter = new double[][]{{0.0625, 0.125, 0.0625},
                                             {0.125, 0.25, 0.125},
                                             {0.0625, 0.125, 0.0625}};
                break;
            case EMBOSS:
                this.filter = new double[][]{{-2, -1, 0},
                                             {-1, 1, 1},
                                             {0, 1, 2}};
                break;
            case IDENTITY:
            default:
                this.filter = new double[][]{{0, 0, 0},
                                             {0, 1, 0},
                                             {0, 0, 0}};
        }
    }
}
