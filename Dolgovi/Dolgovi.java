package Dolgovi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dolgovi {
    private List<Prijatelj> vsiPrijatelji = new ArrayList<>();

    public void preberiPrijatelje(File datoteka) {
        try (Scanner sc = new Scanner(datoteka)) {
            while (sc.hasNextLine()) {
                String vrstica = sc.nextLine();
                if (!vrstica.isEmpty()) {
                    Prijatelj p = new Prijatelj(vrstica);
                    this.vsiPrijatelji.add(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Napaka: ni datoteke %s.%n", datoteka.getPath());
        }
    }

    private Prijatelj vrniPrijatelja(String ime) {
        for (Prijatelj p : vsiPrijatelji)
            if (p.getIme().equals(ime))
                return p;
        return null;
    }

    // vrstica "Ana Marija;Berta;5.00" v datoteki pomeni, da Ana Marija dolguje
    // Berti 5 EUR
    public void preberiDolgove(File datoteka) {
        try (Scanner sc = new Scanner(datoteka)) {
            while (sc.hasNextLine()) {
                String[] elementi = sc.nextLine().split(";");
                if (elementi.length < 3) // vnos ni popoln, ga preskočimo
                    continue;
                Prijatelj dolznik = vrniPrijatelja(elementi[0]);
                Prijatelj upnik = vrniPrijatelja(elementi[1]);
                if (upnik == null || dolznik == null) // imena ni v seznamu prijateljev
                    continue;
                double znesek = Double.parseDouble(elementi[2]);
                if (znesek < 0) { // zamenjaj dolžnika in upnika
                    Prijatelj tmp = upnik;
                    upnik = dolznik;
                    dolznik = tmp;
                    znesek *= -1;
                }
                dolznik.dodajDolg(upnik, znesek);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Napaka: ni datoteke %s.%n", datoteka.getPath());
        }
    }

    public void izpisi() {
        Collections.sort(this.vsiPrijatelji, new Comparator<Prijatelj>() {
            public int compare(Prijatelj p1, Prijatelj p2) {
                return p1.getIme().compareTo(p2.getIme());
            }
        });
        for (Prijatelj p : vsiPrijatelji) {
            // System.out.println(p.toString());
            System.out.println(p.vrniOpisZDolgovi());
        }
    }

    public Set<Prijatelj> vrniBrezDolga() {
        Set<Prijatelj> tmp = new TreeSet<>(); // za urejeno množico prijateljev
        for (Prijatelj p : this.vsiPrijatelji) {
            if (!p.imaDolg())
                tmp.add(p);
        }
        return tmp;
    }

    public void izpisiSkupenDolg() {
        double vsiDolgovi = 0.0;
        Collections.sort(this.vsiPrijatelji, new Comparator<Prijatelj>() {
            public int compare(Prijatelj p1, Prijatelj p2) {
                return Double.compare(p2.vrniSkupniZnesekDolga(), p1.vrniSkupniZnesekDolga());
            }
        });
        for (Prijatelj p : vsiPrijatelji) {
            double dolg = p.vrniSkupniZnesekDolga();
            System.out.printf("%s: skupaj dolguje %.2f EUR%n", p.toString(), dolg);
            vsiDolgovi += dolg;
        }
        System.out.printf("Vsi prijatelji skupaj imajo %.2f EUR dolgov.%n", vsiDolgovi);
    }
}
