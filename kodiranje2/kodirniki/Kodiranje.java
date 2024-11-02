package kodiranje2.kodirniki;

import kodiranje2.izjeme.IzjemaDatoteke;

import java.io.*;

public class Kodiranje {
    private Kodirnik kodirnik; // kodirnik, ki se uporabi za kodiranje in odkodiranje

    public Kodiranje(Kodirnik k) {
        this.kodirnik = k;
    }

    // Kodiranje besedila (niz znakov)

    public String zakodiranjeBesedila(String besedilo) {
        kodirnik.ponastavi();
        StringBuilder zakodirano = new StringBuilder();
        for (int i = 0; i < besedilo.length(); i++) {
            char zakodiranZnak = (char) kodirnik.zakodiraj(besedilo.charAt(i));
            zakodirano.append(zakodiranZnak);
        }
        return zakodirano.toString();
    }

    public String odkodiranjeBesedila(String besedilo) {
        kodirnik.ponastavi();
        StringBuilder odkodirano = new StringBuilder();
        for (int i = 0; i < besedilo.length(); i++) {
            odkodirano.append((char) kodirnik.odkodiraj(besedilo.charAt(i)));
        }
        return odkodirano.toString();
    }

    // Kodiranje tekstovnih datotek

    public void zakodiranjeTekstovneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        kodirnik.ponastavi();
        try (FileReader vhod = new FileReader(vhodnaDatoteka);
             FileWriter izhod = new FileWriter(izhodnaDatoteka)) {
            int znak;
            while ((znak = vhod.read()) != -1) {
                izhod.write((char) kodirnik.zakodiraj(znak));
            }
        } catch (FileNotFoundException e) {
            throw new IzjemaDatoteke(String.format("Napaka: ne najdem datoteke %s.", vhodnaDatoteka));
        } catch (IOException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri branju datoteke %s ali pri pisanju datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        }
    }

    public void odkodiranjeTekstovneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        kodirnik.ponastavi();
        try (FileReader vhod = new FileReader(vhodnaDatoteka);
             FileWriter izhod = new FileWriter(izhodnaDatoteka)) {
            int znak;
            while ((znak = vhod.read()) != -1) {
                izhod.write((char) kodirnik.odkodiraj(znak));
            }
        } catch (FileNotFoundException e) {
            throw new IzjemaDatoteke(String.format("Napaka: ne najdem datoteke %s.", vhodnaDatoteka));
        } catch (IOException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri branju datoteke %s ali pri pisanju datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        }
    }

    // Kodiranje binarnih datotek

    public void zakodiranjeBinarneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        kodirnik.ponastavi();
        try (FileInputStream vhod = new FileInputStream(vhodnaDatoteka);
             FileOutputStream izhod = new FileOutputStream(izhodnaDatoteka)) {
             while (vhod.available() > 0) {
                 int bajt = vhod.read();
                 izhod.write(kodirnik.zakodiraj(bajt));
             }
        } catch (FileNotFoundException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri odpiranju datoteke %s ali datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        } catch (IOException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri branju datoteke %s ali pri pisanju datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        } catch (SecurityException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri pravicah za branje datoteke %s ali za pisanje datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        }
    }

    public void odkodiranjeBinarneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        kodirnik.ponastavi();
        try (FileInputStream vhod = new FileInputStream(vhodnaDatoteka);
             FileOutputStream izhod = new FileOutputStream(izhodnaDatoteka)) {
            while (vhod.available() > 0) {
                izhod.write(kodirnik.odkodiraj(vhod.read()));
            }
        } catch (FileNotFoundException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri odpiranju datoteke %s ali datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        } catch (IOException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri branju datoteke %s ali pri pisanju datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        } catch (SecurityException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri pravicah za branje datoteke %s ali za pisanje datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        }
    }

    public void zakodiranjePodatkovneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        kodirnik.ponastavi();
        try (DataInputStream vhod = new DataInputStream(new FileInputStream(vhodnaDatoteka));
             DataOutputStream izhod = new DataOutputStream(new FileOutputStream(izhodnaDatoteka))) {
            while (true) {
                izhod.writeChar(kodirnik.zakodiraj(vhod.readChar()));
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            throw new IzjemaDatoteke(String.format("Napaka: ne najdem datoteke %s.", vhodnaDatoteka));
        } catch (IOException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri branju datoteke %s ali pri pisanju datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        }
    }

    public void odkodiranjePodatkovneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        kodirnik.ponastavi();
        try (DataInputStream vhod = new DataInputStream(new FileInputStream(vhodnaDatoteka));
             DataOutputStream izhod = new DataOutputStream(new FileOutputStream(izhodnaDatoteka))) {
            while (true) {
                izhod.writeChar(kodirnik.odkodiraj(vhod.readChar()));
            }
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            throw new IzjemaDatoteke(String.format("Napaka: ne najdem datoteke %s.", vhodnaDatoteka));
        } catch (IOException e) {
            throw new IzjemaDatoteke(String.format("Napaka: pri branju datoteke %s ali pri pisanju datoteke %s.", vhodnaDatoteka, izhodnaDatoteka));
        }
    }
}
