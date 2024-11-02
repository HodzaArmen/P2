package dolgovi;

import java.io.File;
import java.util.Set;

public class CistiRacuni {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.printf("Podajte argumente: <prijatelji.txt> <dolgovi.txt>%n");
            return;
        }

        // preberi podatke in jih izpiši
        Dolgovi neporavnaniRacuni = new Dolgovi();
        neporavnaniRacuni.preberiPrijatelje(new File(args[0]));
        neporavnaniRacuni.preberiDolgove(new File(args[1]));
        neporavnaniRacuni.izpisi();
        System.out.println();

        // poišči prijatelje brez dolgov
        Set<Prijatelj> brezDolgov = neporavnaniRacuni.vrniBrezDolga();
        if (brezDolgov.isEmpty()) {
            System.out.println("Vsi prijatelji imajo dolgove.");
        } else {
            System.out.println("Prijatelji, ki nimajo dolgov:");
            for (Prijatelj p : brezDolgov) {
                System.out.println(p.toString());
            }
        }
        System.out.println();

        // izpiši skupni dolg vsakega prijatelja
        neporavnaniRacuni.izpisiSkupenDolg();
        System.out.println();
    }
}
