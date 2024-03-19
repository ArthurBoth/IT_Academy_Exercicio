package complementaries.rewards;

// File imports
import static complementaries.Constants.Variables.randomizer;

public enum URL_LINKS {
    THE_DUCK_SONG   (1, "A música do pato", "https://www.youtube.com/watch?v=MtN1YnoL46Q"),
    CRAZY_HAMSTER   (2, "Um hamster muito louco", "https://www.youtube.com/watch?v=H0Yirlo6WSU"),
    FAVOURITE_SONG  (3, "A minha música favorita", "https://www.youtube.com/watch?v=sWz7feSZXzg"),
    FURRET_WALKS    (4, "Um furret andando", "https://www.youtube.com/watch?v=xzD0M1MXGqY"),
    DUNKEY_GUIDE    (5,"O Guia Dunkey para Serviços de Streaming",
                        "https://www.youtube.com/watch?v=Nh1IKMEbvJM"),
    GITCHEE_GOO     (6, "Mande isso pro seu crush", "https://www.youtube.com/watch?v=66dEwN5I0Bs "),
    SOFTWARE_TESTER (7,"Um vídeo sobre verificação e validação de software",
                        "https://www.youtube.com/watch?v=GzhKG23pl-0");
                        
    public final String message;
    public final String url;
    public final int id;

    private URL_LINKS(int id, String message, String url) {
        this.id = id;
        this.message = message;
        this.url = url;
    }

    public static URL_LINKS getRandomLink() {
        return URL_LINKS.values()[randomizer.nextInt(URL_LINKS.values().length)];
    }
}