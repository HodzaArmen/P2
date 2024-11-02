package DN;

abstract class Lik {
    abstract public double obseg();
}
class Pravokotnik extends Lik {
    private int a;
    private int b;
    public Pravokotnik(int a, int b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public double obseg() {
        return a * 2 + b * 2;
    }
}
class Kvadrat extends Lik {
    private int a;
    public Kvadrat(int a) {
        this.a = a;
    }
    @Override
    public double obseg() {
        return a * 4;
    }
}
class NKotnik extends Lik {
    private int a;
    private int n;
    public NKotnik(int a, int n) {
        this.a = a;
        this.n = n;
    }
    @Override
    public double obseg() {
        return a * n;
    }
}
public class DN08{
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Vnesi argumente!");
            return;
        }
        Lik[] tabelaLikov = new Lik[args.length];
        for (int i = 0; i < args.length; i++) {
            String[] podatki = args[i].split(":");
            String likTip = podatki[0];
            switch (likTip) {
                case "kvadrat":
                    int a = Integer.parseInt(podatki[1]);
                    tabelaLikov[i] = new Kvadrat(a);
                    break;
                case "pravokotnik":
                    int a1 = Integer.parseInt(podatki[1]);
                    int b = Integer.parseInt(podatki[2]);
                    tabelaLikov[i] = new Pravokotnik(a1, b);
                    break;
                case "nkotnik":
                    int n = Integer.parseInt(podatki[1]);
                    int a2 = Integer.parseInt(podatki[2]);
                    tabelaLikov[i] = new NKotnik(a2, n);
                    break;
                default:
                    System.out.println("Neveljaven lik: " + likTip);
                    return;
            }
        }
        int obseg = (int) skupniObseg(tabelaLikov);
        System.out.println(obseg);

    }
    static double skupniObseg(Lik[] tabelaLikov) {
        double skupniObseg = 0;
        for (Lik lik : tabelaLikov){
            skupniObseg += lik.obseg();
        }
        return skupniObseg;
    }
}
