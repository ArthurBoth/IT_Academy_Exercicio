package complementaries;

// Java library imports
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;

// File imports
import complementaries.rewards.URL_LINKS;

public interface Constants {
    public interface Variables{

        public static final int LOWER_BOUND_OF_VALID_NUMBERS = 1;
        public static final int UPPER_BOUND_OF_VALID_NUMBERS = 50;
        public static final int NUMBERS_PER_BET = 5;
        public static final int AMOUNT_OF_INITIAL_DRAW_NUMBERS = 5;
        public static final int NUMBER_OF_MAXIMUM_ROUNDS = 25;
        public static final int START_OF_OPTIONS_IN_MENU = 1;
        public static final int NUMBER_OF_OPTIONS_IN_MENU = 5;
        public static final int SPECIAL_OCASION_NUMBER = 0;
        public static final int BET_ID_STARTING_NUMBER = 1000;
        public static final int NUMBER_OF_NAMES_PER_LINE = 5;
        public static final int NUMBER_OF_REWARDS = URL_LINKS.values().length;
        public static final int SECONDS_TO_WAIT_FOR_REWARD = 2;
        public static final int POINTS_PER_NEW_BET = 10;
        public static final int POINTS_FOR_WINNER_BETS = 100;
        public static final int REWARD_POINT_PRIZE = 100;
        
        // visualization constants
        public static final String COLOUR_RESET = "\u001B[0m";
        public static final String COLOUR_RED = "\u001B[31m"; // for interruptions, denials, and warnings
        public static final String COLOUR_GREEN = "\u001B[32m"; // for affirmations and confirmations
        public static final String COLOUR_YELLOW = "\u001B[33m"; // for highlighting text
        public static final String COLOUR_BLUE = "\u001B[34m"; // for highlighting numbers
        public static final String COLOUR_PURPLE = "\u001B[35m"; // for representing 'nothing'
        public static final String COLOUR_CYAN = "\u001B[36m"; // for highlighting very special text and numbers
        public static final String DIVIDER = "\n=====================================================================\n";
    
        // regular expression: validates 11 numbers or 11 numbers with separators after chars 3, 6, and 9
        public static final String REGEX_VALIDATE_CPF = "^(?:(?:[0-9]{3}[.,/| -]){3}[0-9]{2}|[0-9]{11})$";

        // regular expression: validares non-numbers
        public static final String REGEX_CLEANSE_NON_NUMBERS = "[^0-9]";
    
        // for the 'suspense' module
        public static final boolean HAS_SUSPENSE = true;
        public static final int SECONDS_TO_BUILD_SUSPENSE = 5;
    
        public static final Random randomizer = new Random();
    
        public static final Scanner scanner = new Scanner(System.in);
        
    }

    /**
     * Returns a {@code HashSet} storing every possible bet number value.
     * 
     * <p> Creates a new {@code HashSet} every time the method is called, 
     * so different callers don't share the same set.
     * @return {@code HashSet} storing every possible bet number value.
     */
    public static HashSet<Integer> ValidNumbers() {
        HashSet<Integer> returnValue = new HashSet<>();
        for (int i = Variables.LOWER_BOUND_OF_VALID_NUMBERS; i <= Variables.UPPER_BOUND_OF_VALID_NUMBERS; i++) {
            returnValue.add(i);
        }
        return returnValue;
    }

}
