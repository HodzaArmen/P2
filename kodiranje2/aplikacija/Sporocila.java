package kodiranje2.aplikacija;

import kodiranje2.izjeme.IzjemaDatoteke;
import kodiranje2.kodirniki.*;

public class Sporocila {
    public static String sporocilo = "To je dolgo besedilo, ki ga kodiram.";

    public static void main(String[] args) {
        Kodirnik cezar = new CezarjevAlgoritem(0); // cezarjev kodirnik
        Kodirnik xor = new XORAlgoritem("programiranje"); // xor kodirnik

        Kodirnik kombinacija = new KombiniraniKodirnik(new Kodirnik[] { xor, cezar }, 123456789); // kombinacija
                                                                                                  // cezarjevega in xor
                                                                                                  // kodirnika

        // naključna kombinacija štirih cezarjevih in enega XOR kodirnika, kodirnike
        // gnezdimo
        int[] zamiki = new int[] { -2, -1, 1, 2 };
        Kodirnik[] cezarjeviKodirniki = new Kodirnik[zamiki.length];
        for (int i = 0; i < zamiki.length; i++) {
            cezarjeviKodirniki[i] = new CezarjevAlgoritem(zamiki[i]);
        }
        Kodirnik cezarjevKombiniran = new KombiniraniKodirnik(cezarjeviKodirniki, 987654321); // naključno eden od
                                                                                              // štirih cezarjevih
                                                                                              // kodirnikov
        Kodirnik k = new KombiniraniKodirnik(new Kodirnik[] { xor, cezarjevKombiniran }, 123456789); // v 50 % bo xor, v
                                                                                                     // 50 % pa eden od
                                                                                                     // cezarjevih
                                                                                                     // kodirnikov

        Kodiranje kodiranje = new Kodiranje(k);

        // kodiranje besedila (niz znakov)
        String zakodirano = kodiranje.zakodiranjeBesedila(sporocilo);
        System.out.println(zakodirano);

        String odkodirano = kodiranje.odkodiranjeBesedila(zakodirano);
        System.out.println(odkodirano);

        // kodiranje tekstovne datoteke
        try {
            kodiranje.zakodiranjeTekstovneDatoteke("viri/original.txt", "viri/zakodirano.txt");
            kodiranje.odkodiranjeTekstovneDatoteke("viri/zakodirano.txt", "viri/odkodirano.txt");
        } catch (IzjemaDatoteke e) {
            System.out.println(e.getMessage());
        }

        // kodiranje binarne datoteke (slike)
        try {
            kodiranje.zakodiranjeBinarneDatoteke("viri/slika.jpg", "viri/zakodirana_slika.jpg");
            kodiranje.odkodiranjeBinarneDatoteke("viri/zakodirana_slika.jpg", "viri/slika.jpg");
        } catch (IzjemaDatoteke e) {
            System.out.println(e.getMessage());
        }

        // kodiranje tekstovne datoteke na binarni način
        try {
            kodiranje.zakodiranjeBinarneDatoteke("viri/original.txt", "viri/zakodirano_b.txt");
            kodiranje.odkodiranjeBinarneDatoteke("viri/zakodirano_b.txt", "viri/odkodirano_b.txt");
        } catch (IzjemaDatoteke e) {
            System.out.println(e.getMessage());
        }

        // kodiranje tekstovne datoteke po znakih - dodatna naloga
        try {
            kodiranje.zakodiranjePodatkovneDatoteke("viri/original.txt", "viri/zakodirano1.txt");
            kodiranje.odkodiranjePodatkovneDatoteke("viri/zakodirano1.txt", "viri/odkodirano1.txt");
        } catch (IzjemaDatoteke e) {
            System.out.println(e.getMessage());
        }

    }
}