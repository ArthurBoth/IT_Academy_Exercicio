package complementaries.rewards;

// File imports
import static complementaries.Constants.Variables.randomizer;

public enum URL_LINKS {
    THE_DUCK_SONG   (1, "A música do pato", "https://www.youtube.com/watch?v=MtN1YnoL46Q"),
    CRAZY_HAMSTER   (2, "Um hamster muito louco", "https://www.youtube.com/watch?v=H0Yirlo6WSU"),
    BORING_VIDEO    (3, "Um vídeo chato", "https://www.youtube.com/watch?v=OtM73QPs3Hw"),
    FURRET_WALKS    (4, "Um furret andando", "https://www.youtube.com/watch?v=xzD0M1MXGqY"),
    DUNKEY_GUIDE    (5,"O Guia Dunkey para Serviços de Streaming",
                        "https://www.youtube.com/watch?v=Nh1IKMEbvJM"),
    GITCHEE_GOO     (6, "Mande isso pro seu crush", "https://www.youtube.com/watch?v=66dEwN5I0Bs "),
    KILL_GEOLOGIST  (7,"Uma dica para de lidar com geólogos que estão te incomodando",
                        "https://www.youtube.com/watch?v=NuyZ-ExYkpQ");
                        
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