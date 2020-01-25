public class Filter {

    public int[][] filter;

    public Filter(int[] arr)
    {
        this.filter = createFilter(arr);
    }

    private int[][] createFilter(int[] arr)
    {
        int[][] matrix = new int[3][3];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                matrix[i][j] = arr[j + i * 3];
            }
        }
        return matrix;
    }

    public void printFilter()
    {
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                System.out.print(this.filter[i][j] + " ");
            }
            System.out.println();
        }
    }
}
