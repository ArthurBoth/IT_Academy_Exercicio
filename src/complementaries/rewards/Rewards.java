package complementaries.rewards;

// Java library imports
import java.awt.Desktop;
import java.net.URI;

public interface Rewards {
    public static void giveReward(URL_LINKS link){
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.browse(new URI(link.url));
            } catch (Exception e) {
                // The app already displays the link to the user, so there's no need to worry about exceptions here.

        }
	}
}
