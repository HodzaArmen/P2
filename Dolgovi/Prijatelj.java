package Dolgovi;

import java.util.*;

public class Prijatelj implements Comparable<Prijatelj> {
    private static int naslednjiID = 1;
    private final int id;
    private final String ime;
    private Map<Prijatelj, Double> dolguje; // seznam upnikov, pari (prijatelj, znesek dolga)

    public Prijatelj(String ime) {
        this.ime = ime;
        this.id = naslednjiID;
        naslednjiID++;
        // this.dolguje = new HashMap<>();
        this.dolguje = new TreeMap<>(); // za urejen izpis upnikov (po abecedi)
    }

    public String getIme() {
        return this.ime;
    }

    public String toString() {
        return String.format("[%03d] %s", this.id, this.ime);
    }

    public void dodajDolg(Prijatelj p, double znesek) {
        double dolg = znesek;
        if (this.dolguje.containsKey(p))
            dolg += this.dolguje.get(p);
        this.dolguje.put(p, dolg);
    }

    public double vrniSkupniZnesekDolga() {
        double dolg = 0;
        for (double d : dolguje.values())
            dolg += d;
        return dolg;
    }

    public boolean imaDolg() {
        return !this.dolguje.isEmpty();
    }

    public String vrniOpisZDolgovi() {
        StringBuilder tmp = new StringBuilder(this.toString());
        if (this.dolguje.isEmpty()) {
            tmp.append(" nima dolgov.\n");
        } else {
            tmp.append(" ima dolgove do naslednjih prijateljev:\n");
            for (Prijatelj p : this.dolguje.keySet()) {
                tmp.append("   --> ");
                tmp.append(p.toString());
                tmp.append(String.format(" (%.2f EUR)", this.dolguje.get(p)));
                tmp.append("\n");
            }
        }
        return tmp.toString();
    }

    @Override
    public int compareTo(Prijatelj p) {
        return this.ime.compareTo(p.ime);
    }
}
