package kodiranje2.kodirniki;

public class XORAlgoritem implements Kodirnik {
    private String geslo; // geslo, s katerim kodiramo
    private int indeks;   // stanje kodirnika (kateri znak uporabimo za XOR)

    public XORAlgoritem(String geslo) {
        this.geslo = geslo;
        this.indeks = 0; // začetno stanje kodirnika je 0 (kodiranje začnemo s prvim znakom)
    }

    @Override
    public int zakodiraj(int vrednost) {
        int rezultat = vrednost ^ geslo.charAt(indeks);
        indeks = (indeks + 1) % geslo.length(); // spremeni stanje kodirnika (naslednji znak gesla)
        return rezultat;
    }

    @Override
    public int odkodiraj(int vrednost) {
        return zakodiraj(vrednost);
    }

    @Override
    public void ponastavi() {
        this.indeks = 0; // kodirnik nastavi na začetno stanje
    }
}
