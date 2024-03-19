package system;

// Java library imports
import java.util.Set;
import java.util.HashSet;

// File imports
import static complementaries.Constants.Variables.*;
import complementaries.Constants;

public class Raffle {

    private final Set<Integer> POSSIBLE_NUMBERS;
    private final Set<Bet> BETS;
    private final Set<Bet> WINNER_BETS;
    private final Set<Integer> DRAWN_NUMBERS;
    private final int[] BET_NUMBERS;

    public Raffle() {
        BETS = new HashSet<>();
        DRAWN_NUMBERS = new HashSet<>();
        WINNER_BETS = new HashSet<>();
        BET_NUMBERS = new int[UPPER_BOUND_OF_VALID_NUMBERS];

        POSSIBLE_NUMBERS = Constants.ValidNumbers();
    }

    /**
     * Registers a bet to this etition.
     * @param bet {@code Bet} object to be added
     */
    public void addBet(Bet bet) {
        for (int i : bet.getNumbers()) {
            BET_NUMBERS[i-1]++;
        }
        BETS.add(bet);
        PrizePool.addToThePot(POINTS_PER_NEW_BET);
    }

    /**
     * Alternates between <i>drawing phase</i> and <i>investigation phase</i> many times.
     * 
     * <p>Stops when a winner is determined or if both phases happen
     *  {@code AMOUNT_OF_EXTRA_RAFFLE_NUMBERS + 1} times.
     * @return {@code HashSet} storing the winners. It may be empty
     */
    public Set<Bet> drawWinners() {
        for (int i = 0; i < NUMBER_OF_MAXIMUM_ROUNDS; i++) {
            drawWinnerNumbers();
            checkForWinners();
            if (!WINNER_BETS.isEmpty()) {
                PrizePool.distributePoints(WINNER_BETS);
                break;
            }
        }
        return new HashSet<>(WINNER_BETS);
    }

    /**
     * <i>Drawing phase</i>
     * <p>Draws {@code AMOUNT_OF_INITIAL_DRAW_NUMBERS} winning numbers, 
     * if there aren't any winning numbers.
     * <p><i>Otherwise</i>
     * <p> Draws one winning number.
     */
    private void drawWinnerNumbers() {
        if (DRAWN_NUMBERS.size() == 0) {
            for (int i = 0; i < AMOUNT_OF_INITIAL_DRAW_NUMBERS; i++) {
                drawNumber();
            }
            return;
        }
        drawNumber();
    }

    /**
     * Draws a winning number for this edition.
     */
    private void drawNumber() {
        int[] nonDrawnNumbers = POSSIBLE_NUMBERS.stream().mapToInt(Integer::intValue).toArray();
        int drawnNumber = nonDrawnNumbers[randomizer.nextInt(0,nonDrawnNumbers.length)];
        POSSIBLE_NUMBERS.remove(drawnNumber);
        DRAWN_NUMBERS.add(drawnNumber);
    }

    /**
     * <i>Investigation phase</i>
     * <p>Checks if there are any winning bets.
     */
    private void checkForWinners() {
        for (Bet a : BETS) {
            if (checkWinner(a)) {
                WINNER_BETS.add(a);
            }
        }
    }

    /**
     * Checks if every number from a bet is a winning number.
     * @param bet {@code Bet} to be checked
     * @return {@code true} if every number is a winning number.
     */
    private boolean checkWinner(Bet bet) {
        for (Integer i : bet.getNumbers()) {
            if (!DRAWN_NUMBERS.contains(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @return {@code int} that reprerents the number of registered bets.
     */
    public int getNumberOfBets() {
        return BETS.size();
    }

    /**
     * 
     * @return {@code HashSet} storing every registered bet.
     */
    public HashSet<Bet> getBets() {
        return new HashSet<>(BETS);
    }

    /**
     * 
     * @return {@code int[]} storing every winning bet. 
     */
    public int[] getWinnerNumbers() {
        return DRAWN_NUMBERS.stream().mapToInt(Integer::intValue).toArray();
    }
    
    /**
     * 
     * @return {@code int[]} storing the amount of times every number was bet.
     *             <p>eg: {@code [10]} stores the amount of times {@code 10} was bet
     */
    public int[] getAllBetNumbers() {
        return BET_NUMBERS.clone();
    }
}
