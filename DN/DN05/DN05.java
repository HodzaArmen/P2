package DN.DN05;

import java.io.*;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;

public class DN05 {
    public static void main(String[] args) {
        String ukaz = args[0];
        if (ukaz.equals("izpisi")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                izpisiSliko(slika);
        } else if (ukaz.equals("histogram")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                izpisiHistogram(slika);
        } else if (ukaz.equals("svetlost")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                System.out.println("Srednja vrednost sivine na sliki " + args[1] + " je: " + svetlostSlike(slika));
        } else if (ukaz.equals("zmanjsaj")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                izpisiSliko(zmanjsajSliko(slika));
        } else if (ukaz.equals("rotiraj")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                izpisiSliko(rotirajSliko(slika));
        } else if (ukaz.equals("zrcali")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                izpisiSliko(zrcaliSliko(slika));
        } else if (ukaz.equals("vrstica")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                System.out.println("Max razlika svetlo - temno je v " + poisciMaxVrstico(slika) + ". vrstici.");
        } else if (ukaz.equals("barvna")) {
            int[][][] slika = preberiBarvnoSliko(args[1]);
            if (slika != null)
                izpisiBarvnoSliko(slika);
        } else if (ukaz.equals("sivinska")) {
            int[][][] slika = preberiBarvnoSliko(args[1]);
            if (slika != null)
                izpisiSliko(pretvoriVSivinsko(slika));
        } else if (ukaz.equals("uredi")) {
            String[] imenaSlik = new String[args.length - 1];
            for (int i = 0; i < args.length - 1; i++) {
                imenaSlik[i] = args[i + 1];
            }
            preberiVseInIzpisi(imenaSlik);
        } else if (ukaz.equals("jedro")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                konvolucijaJedro(slika);
        } else if (ukaz.equals("glajenje")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                konvolucijaGlajenje(slika);
        } else if (ukaz.equals("robovi")) {
            int[][] slika = preberiSliko(args[1]);
            if (slika != null)
                konvolucijaRobovi(slika);
        }
    }

    static int[][] preberiSliko(String ime) {
        try {
            File file = new File(ime);
            Scanner sc = new Scanner(file);
            if (file.length() == 0) {
                System.out.println("Napaka: Datoteka " + ime + " je prazna.");
                sc.close();
                return null;
            }
            String prvaVrstica = sc.nextLine();
            if (prvaVrstica.length() == 0 || !prvaVrstica.startsWith("P2")) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2.");
                sc.close();
                return null;
            }
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                sc.close();
                return null;
            }
            String[] prvaVrsticaSplit = prvaVrstica.trim().split(":|\\s+");
            if (prvaVrsticaSplit.length != 5) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2.");
                sc.close();
                return null;
            }
            String format = prvaVrsticaSplit[0];
            String sir = prvaVrsticaSplit[2];
            String x = prvaVrsticaSplit[3];
            String vis = prvaVrsticaSplit[4];
            String[] drugaVrstica = sc.nextLine().trim().split(" ");
            if (!format.equals("P2") || !x.equals("x")) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2.");
                sc.close();
                return null;
            }
            int sirina = Integer.parseInt(sir);
            int visina = Integer.parseInt(vis);
            if (drugaVrstica.length < sirina * visina) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                sc.close();
                return null;
            }

            if (sirina <= 0 || visina <= 0) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike je 0 ali negativna).");
                sc.close();
                return null;
            }

            int[][] slika = new int[visina][sirina];
            sc.close();
            sc = new Scanner(file);
            prvaVrstica = sc.nextLine();
            for (int i = 0; i < visina; i++) {
                for (int j = 0; j < sirina; j++) {
                    if (!sc.hasNextInt()) {
                        System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                        sc.close();
                        return null;
                    }
                    int piksel = sc.nextInt();
                    if (piksel < 0 || piksel > 255) {
                        System.out.println("Napaka: datoteka " + ime + " vsebuje podatke izven obsega 0 do 255.");
                        sc.close();
                        return null;
                    }
                    slika[i][j] = piksel;
                }
            }
            sc.close();
            return slika;
        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka " + ime + " ne obstaja.");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike ni pravilna).");
            return null;
        }
    }

    static void izpisiSliko(int[][] slika) {
        System.out.println("velikost slike: " + slika[0].length + " x " + slika.length);
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[i].length; j++) {
                System.out.printf("%3d ", slika[i][j]);
            }
            System.out.println();
        }
    }

    static void izpisiHistogram(int[][] slika) {
        int[] histogram = new int[256];
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[i].length; j++) {
                histogram[slika[i][j]]++;
            }
        }
        System.out.println("sivina : # pojavitev");
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > 0) {
                System.out.printf("%5d  :   %-5d\n", i, histogram[i]);
            }
        }
    }

    static double svetlostSlike(int[][] slika) {
        int vs = 0;
        int st = 0;
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[i].length; j++) {
                vs += slika[i][j];
                st++;
            }
        }
        double povp = (double) vs / (double) st;
        return (Math.round(povp * 100.0) / 100.0);
    }

    static int[][] zmanjsajSliko(int[][] slika) {
        int visina = slika.length;
        int sirina = slika[0].length;
        if (visina < 3 || sirina < 3) {
            return slika;
        }
        int zmanjsanaVisina = visina / 2;
        int zmanjsanaSirina = sirina / 2;
        int[][] zmanjsanaSlika = new int[zmanjsanaVisina][zmanjsanaSirina];
        int k = 0;
        int l = 0;
        for (int i = 0; i < zmanjsanaVisina; i++) {
            for (int j = 0; j < zmanjsanaSirina; j++) {
                k = i * 2;
                l = j * 2;
                int povp = (slika[k][l] + slika[k][l + 1] + slika[k + 1][l] + slika[k + 1][l + 1]) / 4;
                zmanjsanaSlika[i][j] = povp;
            }
        }
        return zmanjsanaSlika;
    }

    static int[][] rotirajSliko(int[][] slika) {
        int[][] rotiranaSlika = new int[slika[0].length][slika.length];
        int y = 0;
        for (int i = slika.length - 1; i >= 0; i--) {
            int x = 0;
            for (int j = 0; j < slika[i].length; j++) {
                rotiranaSlika[x][y] = slika[i][j];
                x++;
            }
            y++;
        }
        return rotiranaSlika;
    }

    static int[][] zrcaliSliko(int[][] slika) {
        int[][] zrcaljenaSlika = new int[slika.length][slika[0].length];
        int x = 0;
        for (int i = 0; i < slika.length; i++) {
            int y = 0;
            for (int j = slika[i].length - 1; j >= 0; j--) {
                zrcaljenaSlika[x][y] = slika[i][j];
                y++;
            }
            x++;
        }
        return zrcaljenaSlika;
    }

    static int poisciMaxVrstico(int[][] slika) {
        int vrstica = 0;
        int razlika = 0;
        int maxRazlika = Integer.MIN_VALUE;
        for (int i = 0; i < slika.length; i++) {
            int max = 0;
            int min = 255;
            for (int j = 0; j < slika[i].length; j++) {
                if (slika[i][j] > max)
                    max = slika[i][j];
                if (slika[i][j] < min)
                    min = slika[i][j];
            }
            razlika = max - min;
            if (razlika > maxRazlika) {
                maxRazlika = razlika;
                vrstica = i + 1;
            }
        }
        return vrstica;
    }

    static int[][][] preberiBarvnoSliko(String ime) {
        try {
            File file = new File(ime);
            Scanner sc = new Scanner(file);
            if (file.length() == 0) {
                System.out.println("Napaka: Datoteka " + ime + " je prazna.");
                sc.close();
                return null;
            }
            String prvaVrstica = sc.nextLine();
            if (prvaVrstica.length() == 0 || !prvaVrstica.startsWith("P2B")) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2B.");
                sc.close();
                return null;
            }
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                sc.close();
                return null;
            }
            String[] prvaVrsticaSplit = prvaVrstica.trim().split(":|\\s+");
            if (prvaVrsticaSplit.length != 5) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2.");
                sc.close();
                return null;
            }
            String format = prvaVrsticaSplit[0];
            String sir = prvaVrsticaSplit[2];
            String x = prvaVrsticaSplit[3];
            String vis = prvaVrsticaSplit[4];
            String[] drugaVrstica = sc.nextLine().trim().split(" ");
            if (!format.equals("P2B") || !x.equals("x")) {
                System.out.println("Napaka: datoteka " + ime + " ni v formatu P2B.");
                sc.close();
                return null;
            }
            int sirina = Integer.parseInt(sir);
            int visina = Integer.parseInt(vis);
            if (drugaVrstica.length < sirina * visina) {
                System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                sc.close();
                return null;
            }

            if (sirina <= 0 || visina <= 0) {
                System.out
                        .println("Napaka: datoteka " + ime + " ni v formatu P2B (velikost slike je 0 ali negativna).");
                sc.close();
                return null;
            }
            sc.close();
            sc = new Scanner(file);
            prvaVrstica = sc.nextLine();
            int[][][] slika = new int[visina][sirina][3];
            for (int i = 0; i < visina; i++) {
                for (int j = 0; j < sirina; j++) {
                    if (!sc.hasNextInt()) {
                        System.out.println("Napaka: datoteka " + ime + " vsebuje premalo podatkov.");
                        sc.close();
                        return null;
                    }
                    int piksel = sc.nextInt();
                    if (piksel < 0 || piksel > 1073741824) {
                        System.out
                                .println("Napaka: datoteka " + ime + " vsebuje podatke izven obsega 0 do 1073741824.");
                        sc.close();
                        return null;
                    }
                    int r = (piksel >> 20) & 0x3FF;
                    int g = (piksel >> 10) & 0x3FF;
                    int b = piksel & 0x3FF;
                    slika[i][j][0] = r;
                    slika[i][j][1] = g;
                    slika[i][j][2] = b;
                }
            }
            sc.close();
            return slika;
        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka " + ime + " ne obstaja.");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Napaka: datoteka " + ime + " ni v formatu P2 (velikost slike ni pravilna).");
            return null;
        }
    }

    static void izpisiBarvnoSliko(int[][][] slika) {
        System.out.println("velikost slike: " + slika[0].length + " x " + slika.length);
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[i].length; j++) {
                System.out.print("(" + slika[i][j][0] + "," + slika[i][j][1] + "," + slika[i][j][2] + ") ");
            }
            System.out.println();
        }
    }

    static int[][] pretvoriVSivinsko(int[][][] slika) {
        int[][] sivinskaSlika = new int[slika.length][slika[0].length];
        for (int i = 0; i < slika.length; i++) {
            for (int j = 0; j < slika[i].length; j++) {
                int y = 0;
                for (int k = 0; k < 3; k++) {
                    y += slika[i][j][k];
                }
                y = (y / 3) * 255 / 1023;
                sivinskaSlika[i][j] = y;
            }
        }
        return sivinskaSlika;
    }

    static void preberiVseInIzpisi(String[] imenaSlik) {
        String[][] slike = new String[256][imenaSlik.length];
        String[] tmp = new String[imenaSlik.length];
        String[] imena1 = new String[imenaSlik.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = imenaSlik[i].toLowerCase();
        }
        Arrays.sort(tmp); // prvo lowercase, pa sortiram
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < imenaSlik.length; j++) {
                if (tmp[i].equals(imenaSlik[j].toLowerCase())) {
                    imena1[i] = imenaSlik[j];
                }
            }
        }
        for (int i = 0; i < imena1.length; i++) {
            int[][] slika = preberiSliko(imena1[i]);
            int svetlost = (int) Math.round(svetlostSlike(slika));
            slike[svetlost][i] = imena1[i];
        }
        for (int i = slike.length - 1; i >= 0; i--) {
            for (int j = 0; j < slike[i].length; j++) {
                if (slike[i][j] != null) {
                    System.out.println(slike[i][j] + " (" + i + ")");
                }
            }
        }
    }

    static void konvolucijaJedro(int[][] slika) {
        int visina = slika.length;
        int sirina = slika[0].length;
        if (visina < 3 || sirina < 3) {
            return;
        }
        int visinaKonvolucija = visina - 2;
        int sirinaKonvolucija = sirina - 2;
        int[][] konvolucija = new int[visinaKonvolucija][sirinaKonvolucija];
        int vs = 0;
        for (int i = 0; i < visinaKonvolucija; i++) {
            for (int j = 0; j < sirinaKonvolucija; j++) {
                vs = 0;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        vs += slika[i + k][j + l];
                    }
                }
                konvolucija[i][j] = vs;
            }
        }
        izpisiSliko(konvolucija);
    }

    static void konvolucijaGlajenje(int[][] slika) {
        int visina = slika.length;
        int sirina = slika[0].length;
        int[][] razsirjenaSlika = razsiriSliko(slika);
        int[][] glajenje = new int[visina][sirina];
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                glajenje[i][j] = (int) (Math.round(razsirjenaSlika[i][j] / 16d)
                        + Math.round(razsirjenaSlika[i][j + 1] / 8d)
                        + Math.round(razsirjenaSlika[i][j + 2] / 16d)
                        + Math.round(razsirjenaSlika[i + 1][j] / 8d) + Math.round(razsirjenaSlika[i + 1][j + 1] / 4d)
                        + Math.round(razsirjenaSlika[i + 1][j + 2] / 8d)
                        + Math.round(razsirjenaSlika[i + 2][j] / 16d) + Math.round(razsirjenaSlika[i + 2][j + 1] / 8d)
                        + Math.round(razsirjenaSlika[i + 2][j + 2] / 16d));

            }
        }
        izpisiSliko(glajenje);
    }

    static int[][] razsiriSliko(int[][] slika) {
        int visina = slika.length;
        int sirina = slika[0].length;
        int[][] razsirjenaSlika = new int[visina + 2][sirina + 2];
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                razsirjenaSlika[i + 1][j + 1] = slika[i][j];
            }
        }
        for (int i = 0; i < visina; i++) {
            razsirjenaSlika[i + 1][0] = slika[i][0]; // levi stolpec
            razsirjenaSlika[i + 1][sirina + 1] = slika[i][sirina - 1]; // desni stolpec
        }
        for (int j = 0; j < sirina; j++) {
            razsirjenaSlika[0][j + 1] = slika[0][j]; // prva vrstica
            razsirjenaSlika[visina + 1][j + 1] = slika[visina - 1][j]; // zadnja vrstica
        }
        razsirjenaSlika[0][0] = slika[0][0]; // robovi
        razsirjenaSlika[0][sirina + 1] = slika[0][sirina - 1];
        razsirjenaSlika[visina + 1][0] = slika[visina - 1][0];
        razsirjenaSlika[visina + 1][sirina + 1] = slika[visina - 1][sirina - 1];
        return razsirjenaSlika;
    }

    static void konvolucijaRobovi(int[][] slika) {
        int visina = slika.length;
        int sirina = slika[0].length;
        int[][] razsirjenaSlika = razsiriSliko(slika);
        int[][] roboviNavpicno = new int[visina][sirina];
        int[][] roboviVodoravno = new int[visina][sirina];
        int[][] roboviSkupaj = new int[visina][sirina];
        int[][] roboviKoncni = new int[visina][sirina];
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                roboviNavpicno[i][j] = (int) (Math.round(razsirjenaSlika[i][j] * 1d)
                        + Math.round(razsirjenaSlika[i][j + 1] * 0d)
                        + Math.round(razsirjenaSlika[i][j + 2] * -1d)
                        + Math.round(razsirjenaSlika[i + 1][j] * 2d) + Math.round(razsirjenaSlika[i + 1][j + 1] * 0d)
                        + Math.round(razsirjenaSlika[i + 1][j + 2] * -2d)
                        + Math.round(razsirjenaSlika[i + 2][j] * 1d) + Math.round(razsirjenaSlika[i + 2][j + 1] * 0d)
                        + Math.round(razsirjenaSlika[i + 2][j + 2] * -1d));
            }
        }
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                roboviVodoravno[i][j] = (int) (Math.round(razsirjenaSlika[i][j] * 1d)
                        + Math.round(razsirjenaSlika[i][j + 1] * 2d)
                        + Math.round(razsirjenaSlika[i][j + 2] * 1d)
                        + Math.round(razsirjenaSlika[i + 1][j] * 0d) + Math.round(razsirjenaSlika[i + 1][j + 1] * 0d)
                        + Math.round(razsirjenaSlika[i + 1][j] * 0d)
                        + Math.round(razsirjenaSlika[i + 2][j] * -1d) + Math.round(razsirjenaSlika[i + 2][j + 1] * -2d)
                        + Math.round(razsirjenaSlika[i + 2][j + 2] * -1d));
            }
        }
        int maxVrednost = Integer.MIN_VALUE;
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                roboviSkupaj[i][j] = (int) Math
                        .round(Math.sqrt(Math.pow(roboviNavpicno[i][j], 2) + Math.pow(roboviVodoravno[i][j], 2)));
                if (roboviSkupaj[i][j] > maxVrednost) {
                    maxVrednost = roboviSkupaj[i][j];
                }
            }
        }
        double ratio = 255.0 / maxVrednost;
        for (int i = 0; i < visina; i++) {
            for (int j = 0; j < sirina; j++) {
                roboviKoncni[i][j] = (int) Math.round(roboviSkupaj[i][j] * ratio);
            }
        }
        izpisiSliko(roboviKoncni);
    }
}