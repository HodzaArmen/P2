package DN.DN10;

import java.io.FileInputStream;
import java.io.IOException;

public class DN10 {
    public static void main(String[] args) {
        String imeDatoteke = args[0];
        try (FileInputStream fis = new FileInputStream(imeDatoteke)) {
            if (!veljavnaPNG(fis))
                System.exit(1);
            while (true) {
                int dolzina = preberiInt(fis);
                if (dolzina == -1)
                    break;
                String tip = preberiNiz(fis, 4);
                fis.skip(dolzina + 4);
                System.out.println("Chunk: " + tip + ", length: " + dolzina);
                if (tip.equals("IEND"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean veljavnaPNG(FileInputStream fis) throws IOException {
        byte[] glava = new byte[8];
        fis.read(glava);
        byte[] pngGlava = new byte[] { (byte) 0x89, 'P', 'N', 'G', (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A };
        for (int i = 0; i < pngGlava.length; i++) {
            if (glava[i] != pngGlava[i]) {
                return false;
            }
        }
        return true;
    }

    private static int preberiInt(FileInputStream fis) throws IOException {
        byte[] bajti = new byte[4];
        if (fis.read(bajti) == -1)
            return -1;
        return ((bajti[0] & 0xFF) << 24) | ((bajti[1] & 0xFF) << 16) | ((bajti[2] & 0xFF) << 8) | (bajti[3] & 0xFF);
    }

    private static String preberiNiz(FileInputStream fis, int dolzina) throws IOException {
        byte[] bajti = new byte[dolzina];
        fis.read(bajti);
        return new String(bajti, "UTF-8");
    }
}