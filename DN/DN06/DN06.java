package DN;

import edu.princeton.cs.algs4.StdDraw;
import java.awt.Font;
public class DN06 {
    public static void main(String[] args) {
        if (args[0].length() != 81 && args.length != 1) {
            System.out.println("Moras vpisat 81 številk!");
        } else {
            narisiSudoku(args[0]);
        }
    }
    public static void narisiSudoku(String nizStevilk) {
        String[][] tab = new String[9][9];
        //004000000000030002390700080400009001209801307600200008010008053900040000000000800
        //000000800900040000..
        String stevilke = "";
        for (int i = 0; i <= nizStevilk.length(); i++) {
            if (i % 9 == 0 && i != 0)
                stevilke = nizStevilk.substring((i - 9), i) + stevilke;
        }
        int k = 0;
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = String.valueOf(stevilke.charAt(k));
                k++;
            }
        }
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setXscale(-0.04, 9.04);
        StdDraw.setYscale(-0.04, 9.04);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.line(0, 0, 9, 0); // spodnja črta
        StdDraw.line(0, 9, 9, 9); // zgornja crta
        StdDraw.line(0, 0, 0, 9); // leva črta
        StdDraw.line(9, 0, 9, 9); // desna črta
        for (int i = 1; i < 9; i++) {
            if (i % 3 == 0) {
                StdDraw.setPenRadius(0.01);
            } else {
                StdDraw.setPenRadius(0.005);
            }
            StdDraw.line(i, 0, i, 9); // navpicna črta
            StdDraw.line(0, i, 9, i); // vodoravna crta
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                double x = j + 0.5;
                double y = i + 0.5;
                if (!tab[i][j].equals("0")) {
                    StdDraw.text(x, y, tab[i][j]);
                }
            }
        }
        StdDraw.show();
    }
}
