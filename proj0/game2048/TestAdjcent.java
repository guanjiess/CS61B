package game2048;

import org.junit.Test;
import static org.junit.Assert.*;
public class TestAdjcent {

    static Board b;
    public int[][] rawVals1 = new int[][] {
            {1, 2, 3, 4},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {5, 6, 7, 8},
    };

    public int[][] rawVals2 = new int[][] {
            {2, 0, 2, 0},
            {4, 4, 2, 2},
            {0, 4, 0, 0},
            {2, 4, 4, 8},
    };

    public int[][] rawVals3 = new int[][] {
            {2, 4, 2, 4},
            {16, 2, 4, 2},
            {2, 4, 2, 4},
            {4, 2, 4, 2},
    };

    public int[][] rawVals4 = new int[][] {
            {2, 4, 64, 64},
            {16, 2, 4, 8},
            {2, 4, 2, 32},
            {4, 2, 4, 32},
    };

    @Test
    public void TestCorner(){
        b = new Board(rawVals1,0);
        Tile [] expected = new Tile[2];
        expected[0] = Tile.create(3,1,0);
        expected[0] = Tile.create(3,0,1);

    }

    @Test
    public void TestEdge(){

    }

    @Test
    public void TestNormal(){

    }




}
