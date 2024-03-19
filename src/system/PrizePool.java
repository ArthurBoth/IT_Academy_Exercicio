package system;

// Java library imports
import java.util.Set;

// File imports
import static complementaries.Constants.Variables.POINTS_FOR_WINNER_BETS;

public class PrizePool {
    
    private static int pot = 0;
    private static int lastDistributedPoints;

    private PrizePool() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 
     * @param number is added to the pot
     */
    public static void addToThePot(int number) {
        pot += number;
    }

    /**
     * All points in the pot are divided equally among bets. Spoils stay in the pot
     * @param bets the bets to recieve poits
     */
    public static void distributePoints(Set<Bet> bets) {
        lastDistributedPoints = (pot / bets.size());
        pot = (pot % bets.size());

        for (Bet b : bets) {
            b.addPoints(POINTS_FOR_WINNER_BETS + lastDistributedPoints);
        }
    }

    /**
     * 
     * @return The value inside the pot
     */
    public static int getPotValue() {
        return pot;
    }

    /**
     * 
     * @return the amount of points distributed last time
     */
    public static int getLastDistributedPoints() {
        return lastDistributedPoints;
    }
}