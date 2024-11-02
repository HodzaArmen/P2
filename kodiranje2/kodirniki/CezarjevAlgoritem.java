package kodiranje2.kodirniki;

public class CezarjevAlgoritem implements Kodirnik {
    private int zamik; // za koliko zamakne vrednost (ključ šifriranja)

    public CezarjevAlgoritem(int zamik) {
        this.zamik = zamik;
    }

    @Override
    public int zakodiraj(int vrednost) {
        return vrednost + zamik;
    }

    @Override
    public int odkodiraj(int vrednost) {
        return vrednost - zamik;
    }

    @Override
    public void ponastavi() {
        // ker algoritem deluje neodvisno od stanja,
        // se stanje ne ohranja in zato ni potrebna
        // ponastavitev začetnega stanja
    }
}
