public class Array {
    public static void main(String [] args){
        int [] x;
        int [] y;
        int [] z = null;
        x = new int[]{1, 2, 3, 4, 5};
        y = x;
        x = new int[]{-1, 2, 5, 4, 99};
        y = new int[3];
        z = new int[0];
        int xL = x.length;

        String[] s = new String[6];
        s[4] = "ketchup";
        s[x[3] - x[1]] = "muffins";

        int[] b = {9, 10, 11};
        System.arraycopy(b, 0, x, 3, 2);

        int [][] x2 = {{1,2,3},{4,5,6},{7,8,9}};
        int [][] z2 = new int[3][];
        z2[0] = x2[0];
        z2[1] = x2[1];
        z2[2] = x2[2];
        z2[0][0] = -z2[0][0];

        int [][] w = new int[3][3];
        System.arraycopy(x2[0], 0, w[0], 0, 3);
        System.arraycopy(x2[1], 0, w[1], 0, 3);
        System.arraycopy(x2[2], 0, w[2], 0, 3);
        w[0][0] = -w[0][0];


    }
}
