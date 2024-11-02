//package DN09;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DN09 {
    public static void main(String[] args) {
        Tekmovanje tekmovanje = Tekmovanje.izDatotek(args[1], args[2]);
        if (tekmovanje != null) {
            //switch
            if (args[0].equals("izpisiTekmovanje")) {
                tekmovanje.izpisiTekmovalce();
                System.out.println();
                tekmovanje.izpisiGlasove();
            }
            if (args[0].equals("izpisiTocke")) {
                tekmovanje.izpisiTocke();
            }
            if (args[0].equals("najboljse")) {
                tekmovanje.izpisiRezultateUrejeno(Integer.parseInt(args[3]));
            }
            if (args[0].equals("utezeno")) {
                float utezGlasovanja = Float.parseFloat(args[4]);
                float utezZirije = Float.parseFloat(args[5]);
                Kriterij utezeniKriterij = new UtezeniKriterij(utezGlasovanja, utezZirije);
                tekmovanje.setKriterij(utezeniKriterij);
                tekmovanje.izpisiRezultateUrejeno(Integer.parseInt(args[3]));
            }
        }
    }
}

class Tekmovalec {
    private String drzava;
    private String izvajalec;
    private String naslovPesmi;

    public Tekmovalec(String drzava, String izvajalec, String naslovPesmi) {
        this.drzava = drzava;
        this.izvajalec = izvajalec;
        this.naslovPesmi = naslovPesmi;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getIzvajalec() {
        return izvajalec;
    }

    public String getNaslovPesmi() {
        return naslovPesmi;
    }

    public String toString() {
        return "(" + drzava + ") " + izvajalec + " - " + naslovPesmi;
    }
}

class Glas {
    private final String odDrzave;
    private final String zaDrzavo;
    private final int stTock;

    public Glas(String odDrzave, String zaDrzavo, int stTock) {
        this.odDrzave = odDrzave;
        this.zaDrzavo = zaDrzavo;
        this.stTock = stTock;
    }

    public String getOdDrzave() {
        return odDrzave;
    }

    public String getZaDrzavo() {
        return zaDrzavo;
    }

    public int getStTock() {
        return stTock;
    }

    public String toString() {
        return odDrzave + " --" + stTock + "t-> " + zaDrzavo;
    }
}

class LocenGlas extends Glas {
    private final int stTockGlasovi;
    private final int stTockZirija;
    public LocenGlas(String odDrzave, String zaDrzavo, int stTock, int stTockGlasovi, int stTockZirije) {
        super(odDrzave, zaDrzavo, stTock);
        this.stTockGlasovi = stTockGlasovi;
        this.stTockZirija = stTockZirije;
    }
    public int getStTockGlasovi() {
        return stTockGlasovi;
    }
    public int getStTockZirija() {
        return stTockZirija;
    }
}

class Tekmovanje {
    private final ArrayList<Tekmovalec> seznamTekmovalcev;
    private final ArrayList<Glas> seznamGlasov;
    private Kriterij kriterij;
    private boolean urejeno = false;

    public Tekmovanje(ArrayList<Tekmovalec> seznamTekmovalcev, ArrayList<Glas> seznamGlasov) {
        this.seznamTekmovalcev = seznamTekmovalcev;
        this.seznamGlasov = seznamGlasov;
        this.kriterij = new OsnovniKriterij();
    }

    public static Tekmovanje izDatotek(String datotekaTekmovalci, String datotekaGlasovi) {
        ArrayList<Tekmovalec> tekmovalci = preberiDatotekoTekmovalci(datotekaTekmovalci);
        ArrayList<Glas> glasovi = preberiDatotekoGlasovi(datotekaGlasovi);
        if (tekmovalci == null || glasovi == null) {
            return null;
        }
        return new Tekmovanje(tekmovalci, glasovi);
    }

    public ArrayList<Tekmovalec> getSeznamTekmovalcev() {
        return this.seznamTekmovalcev;
    }

    public ArrayList<Glas> getSeznamGlasov() {
        return this.seznamGlasov;
    }

    public void setKriterij(Kriterij kriterij) {
        this.kriterij = kriterij;
        this.urejeno = false;
    }

    private static ArrayList<Tekmovalec> preberiDatotekoTekmovalci(String datoteka) {
        try (BufferedReader br = new BufferedReader(new FileReader(datoteka))) {
            return new ArrayList<>(br.lines()
                    .skip(1)
                    .map(vrstica -> {
                        String[] podatki = vrstica.split(";");
                        if (podatki.length == 0) {
                            return null;
                        }
                        return new Tekmovalec(podatki[1], podatki[2], podatki[3]);
                    })
                    .toList());
        } catch (IOException e) {
            return null;
        }
    }

    private static ArrayList<Glas> preberiDatotekoGlasovi(String datoteka) {
        try (BufferedReader br = new BufferedReader(new FileReader(datoteka))) {
            return br.lines()
                    .skip(1)
                    .map(vrstica -> {
                        String[] podatki = vrstica.split(";");
                        if (podatki.length == 0) {
                            return null;
                        }
                        if (Integer.parseInt(podatki[0]) >= 2016) {
                            if (podatki.length == 6) {
                                return new LocenGlas(podatki[2], podatki[3], Integer.parseInt(podatki[4]), Integer.parseInt(podatki[5]), 0);
                            } else if (podatki.length == 7) {
                                return new LocenGlas(podatki[2], podatki[3], Integer.parseInt(podatki[4]), Integer.parseInt(podatki[5]), Integer.parseInt(podatki[6]));
                            }
                            return new LocenGlas(podatki[2], podatki[3], Integer.parseInt(podatki[4]), 0, 0);
                        } else {
                            return new Glas(podatki[2], podatki[3], Integer.parseInt(podatki[4]));
                        }
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            return null;
        }
    }

    public void izpisiTekmovalce() {
        if (!seznamTekmovalcev.isEmpty()) {
            System.out.println("Seznam tekmovalcev:");
            seznamTekmovalcev.stream()
                    .map(Tekmovalec::toString)
                    .forEach(System.out::println);
        } else {
            System.out.println("Seznam tekmovalcev je prazen.");
        }
    }

    public void izpisiGlasove() {
        if (!seznamGlasov.isEmpty()) {
            System.out.println("Seznam glasov:");
            seznamGlasov.stream()
                    .map(Glas::toString)
                    .forEach(System.out::println);
        } else {
            System.out.println("Seznam glasov je prazen.");
        }
    }

    public int steviloTock(String drzava) {
        return kriterij.steviloTock(this, drzava);
    }

    public void izpisiTocke() {
        if (seznamTekmovalcev.isEmpty()) {
            System.out.println("Seznam tekmovalcev je prazen.");
        } else {
            System.out.println("Seznam tekmovalcev in njihovih tock:");
            for (Tekmovalec tekmovalec : seznamTekmovalcev) {
                String drzava = tekmovalec.getDrzava();
                System.out.println("(" + drzava + ") " + tekmovalec.getIzvajalec() + " - " + tekmovalec.getNaslovPesmi() + ": " + steviloTock(drzava) + "t");
            }
        }
    }

    public Tekmovalec getZmagovalec() {
        Tekmovalec zmagovalec = null;
        int najTock = 0;
        for (Tekmovalec tekmovalec : seznamTekmovalcev) {
            int tocke = kriterij.steviloTock(this, tekmovalec.getDrzava());
            if (tocke > najTock) {
                najTock = tocke;
                zmagovalec = tekmovalec;
            }
        }
        return zmagovalec;
    }

    private void urediPoTockah() {
        if(!urejeno){
            seznamTekmovalcev.sort(Comparator.comparingInt((Tekmovalec tekmovalec) -> kriterij.steviloTock(this, tekmovalec.getDrzava())).reversed());
            urejeno = true;
        }
    }

    public int getMesto(String d) {
        urediPoTockah();
        for (int i = 0; i < seznamTekmovalcev.size(); i++) {
            Tekmovalec tekmovalec = seznamTekmovalcev.get(i);
            if (tekmovalec.getDrzava().equals(d)) {
                return i + 1;
            }
        }
        return -1;
    }

    public void izpisiRezultateUrejeno(int topK) {
        System.out.println("Najboljse uvrsceni tekmovalci:");
        urediPoTockah();
        for (int i = 0; i < Math.min(topK, seznamTekmovalcev.size()); i++) {
            Tekmovalec tekmovalec = seznamTekmovalcev.get(i);
            System.out.println((i + 1) + ". " + tekmovalec.toString() + ": " + steviloTock(tekmovalec.getDrzava()) + "t");
        }
    }
}

interface Kriterij {
    int steviloTock(Tekmovanje tekmovanje, String drzava);
}

class OsnovniKriterij implements Kriterij {
    @Override
    public int steviloTock(Tekmovanje tekmovanje, String drzava) {
        return tekmovanje.getSeznamGlasov().stream()
                .filter(glas -> glas.getZaDrzavo().equals(drzava))
                .mapToInt(Glas::getStTock)
                .sum();
    }
}
class UtezeniKriterij implements Kriterij {
    private float utezGlasovanja;
    private float utezZirija;
    public UtezeniKriterij(float utezGlasovanja, float utezZirije){
        this.utezGlasovanja = utezGlasovanja;
        this.utezZirija = utezZirije;
    }
    @Override
    public int steviloTock(Tekmovanje tekmovanje, String drzava) {
        double skupno = tekmovanje.getSeznamGlasov().stream()
                .filter(glas -> glas.getZaDrzavo().equals(drzava))
                .mapToDouble(glas -> {
                    if (glas instanceof LocenGlas) {
                        LocenGlas locenGlas = (LocenGlas) glas;
                        return locenGlas.getStTockGlasovi() * utezGlasovanja + locenGlas.getStTockZirija() * utezZirija;
                    } else {
                        return glas.getStTock() * utezZirija;
                    }
                })
                .sum();
        return (int) Math.round(skupno);
    }
}