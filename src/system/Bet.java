package system;

// Java library imports
import java.util.Set;
import java.util.HashSet;

// File imports
import static complementaries.Constants.Variables.*;
import complementaries.Constants;
import complementaries.comparators.AlphabeticString;

public class Bet {
    
    private final int ID;
    private final AlphabeticString BETTER_NAME;
    private final String BETTER_CPF;
    private final int RAFFLE_ID;
    private final HashSet<Integer> BET_NUMBERS;
    private final HashSet<Integer> POSSIBLE_NUMBERS;

    public Bet (int RAFFLE_ID, int ID, String BETTER_NAME, String BETTER_CPF) {
        this.RAFFLE_ID = RAFFLE_ID;
        this.ID = ID;
        this.BETTER_NAME = new AlphabeticString(BETTER_NAME);
        this.BETTER_CPF = formatCpf(BETTER_CPF);
        BET_NUMBERS = new HashSet<>();
        
        POSSIBLE_NUMBERS = Constants.ValidNumbers();
    }

    /**
     * Adds the specified number to this bet if it is not already present.
     * @param number number to be added
     * @return {@code true} if this bet did not already contain the specified number.
     */
    public boolean bet(int number) {
        if (POSSIBLE_NUMBERS.contains(number)) {
            POSSIBLE_NUMBERS.remove(number);
        }
        return (BET_NUMBERS.add(number));
    }

    /**
     * Randomly chooses a number that is not in this bet, adds it to this bet.
     * @return The number added
     */
    public int chooseNumberForMe() {
        int drawnNumber;
        int[] nonBetNumbers = POSSIBLE_NUMBERS.stream().mapToInt(Integer::intValue).toArray();

        drawnNumber = nonBetNumbers[randomizer.nextInt(1,POSSIBLE_NUMBERS.size())];
        POSSIBLE_NUMBERS.remove(drawnNumber);
        BET_NUMBERS.add(drawnNumber);
        return drawnNumber;
    }

    /**
     * Formats a {@code String} to the format <i>000.000.000-00</i>.
     * @param CPF {@code String} to be formated
     * @return A formated {@code String}
     * @throws IndexOutOfBoundsException if {@code CPF}
     *              has less than 9 characters.
     */
    public static String formatCpf (String CPF) {
        StringBuilder newCpf;

        CPF = CPF.replaceAll(REGEX_CLEANSE_NON_NUMBERS, "");

        newCpf = new StringBuilder(CPF.substring(0,3));
        newCpf.append(".");
        newCpf.append(CPF.substring(3,6));
        newCpf.append(".");
        newCpf.append(CPF.substring(6,9));
        newCpf.append("-");
        newCpf.append(CPF.substring(9));

        return newCpf.toString();
    }

    /**
     * 
     * @return {@code HashSet} storing the numbers from this bet.
     */
    public Set<Integer> getNumbers(){
        return new HashSet<>(BET_NUMBERS);
    }

    /**
     * 
     * @return {@code int} that reprerents the edition of the Raffle this bet is registered in.
     */
    public int getRaffleId() {
        return this.RAFFLE_ID;
    }

    /**
     * 
     * @return {@code int} that reprerents the Id of this bet.
     */
    public int getId() {
        return this.ID;
    }

    /**
     * 
     * @return {@code AlphabeticString} that reprerents the better's name.
     */
    public AlphabeticString getBetterName() {
        return this.BETTER_NAME;
    }

    /**
     * 
     * @return {@code String} that reprerents the better's CPF, formated as <i>000.000.000-00</i>.
     */
    public String getBetterCpf() {
        return this.BETTER_CPF;
    }

    /**
     * 
     * @param displayCpf determines if the output will have {@code CPF} displayed
     * @return formated {@code String} ready to be printed.
     */
    public String toString(boolean displayCpf) {
        StringBuilder returnValue = new StringBuilder(
                String.format("\tAposta do %sSorteio %d%s", COLOUR_YELLOW, RAFFLE_ID, COLOUR_RESET)
            );
        returnValue.append(
                String.format("\n\t\tID: %s%d%s", COLOUR_BLUE, ID, COLOUR_RESET)
            );
        returnValue.append(
                String.format("\n\t\tNome do apostator: %s%s%s", COLOUR_CYAN, BETTER_NAME, COLOUR_RESET)
            );
        if (displayCpf) {
            returnValue.append(
                String.format("\n\t\tCPF do apostator: %s%s%s", COLOUR_YELLOW, BETTER_CPF, COLOUR_RESET)
            );
        }
        returnValue.append("\n\t\tNúmeros apostados: [");
        for (Integer i : BET_NUMBERS) {
            returnValue.append(
                String.format("%s%d%s, ", COLOUR_BLUE, i, COLOUR_RESET)
            );
        }
        return (returnValue.substring(0, returnValue.length()-2) + "]");
    }
}
