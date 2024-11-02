package kodiranje2.kodirniki;

import java.util.Random;

public class KombiniraniKodirnik implements Kodirnik {
    private Kodirnik[] kodirniki;
    private long seme;
    private Random rnd;

    public KombiniraniKodirnik(Kodirnik[] kodirniki, long seme) {
        this.kodirniki = kodirniki;
        this.seme = seme;
        rnd = new Random(seme);
    }

    @Override
    public int zakodiraj(int vrednost) {
        int ind = rnd.nextInt(kodirniki.length);
        Kodirnik k = kodirniki[ind];
        return k.zakodiraj(vrednost);
    }

    @Override
    public int odkodiraj(int vrednost) {
        return kodirniki[rnd.nextInt(kodirniki.length)].odkodiraj(vrednost);
    }

    @Override
    public void ponastavi() {
        // ponastavi generator naključnih števil
        rnd = new Random(seme);
        // ponastavi vse kodirnike
        for(Kodirnik k : kodirniki){
            k.ponastavi();
        }
    }
}
