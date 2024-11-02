package DN.DN04;

public class DN04 {
    public static void main(String[] args) {
        String binSporocilo = args[0];
        String sporocilo = "";
        String binZnak = "";
        int ascii = 0;
        for (int i = 0; i < binSporocilo.length(); i += 8) {
            binZnak = binSporocilo.substring(i, i + 8);
            ascii = Integer.parseInt(binZnak, 2);
            char c = (char) ascii;
            sporocilo += c;
        }
        System.out.println(sporocilo);
    }
}
