package DN.DN02;

public class DN02 {
    public static void main(String[] args) {
        String niz = args[0];
        int n = niz.length();
        double a = Math.sqrt(n);
        int x;
        if (a % 1 == 0) {
            x = (int) a;
        } else {
            x = (int) a + 1;
        }
        int index = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (index < n) {
                    System.out.print(niz.charAt(index) + "  ");
                    index++;
                } else {
                    System.out.print(".  ");
                }
            }
            System.out.println();
        }
    }
}
