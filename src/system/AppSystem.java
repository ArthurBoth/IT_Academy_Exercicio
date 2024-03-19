package system;

// Java library imports
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.HashMap;

// File imports
import static complementaries.Constants.Variables.*;
import complementaries.INPUT_TYPE;
import complementaries.comparators.PairedInt;
import complementaries.rewards.Rewards;
import complementaries.rewards.URL_LINKS;

public class AppSystem {

    private Raffle currentRaffle;
    private int numberOfRaffles;
    private Set<Bet> winnerBets;
    
    public AppSystem(){
        numberOfRaffles = 0;
        winnerBets = new HashSet<>();
    }

    public void start() {
        while (true){
            UI.menu();
            userChoice(Integer.parseInt(UI.userInput(INPUT_TYPE.MENU)));
        }
    }

    /**
     * Call the apropriate method from the user choice, where:
     * <p>1 => Stops the current edition (without drawing numbers) and starts a new one.
     * <p>2 => Registers a new 5 number bet.
     * <p>3 => Displays every bet registered and its relevant data.
     * <p>4 => Finishes the current edition by drawing wining numbers and checking for 
     *          winners.
     * <p>5 => Checks if user is a winner and, if they are, gives them a prize.
     * <p>{@code SPECIAL_OCASION_NUMBER} => Exits the program.
     * @param input is the users choice
     */
    private void userChoice(int input) {
        switch (input) {
            case 1:
                startNewEdition();
                break;
            case 2:
                registerBet();
                break;
            case 3:
                displayBetData();
                break;
            case 4:
                drawWinners();
                break;
            case 5:
                getPrize();
                break;
            case SPECIAL_OCASION_NUMBER:
                System.exit(0);
            default:
        }
    }

    /**
     * Starts a new edition, if there already is a running edition, ask for confirmation
     * first and stops the current edition.
     * 
     * <p>Stopped editions don't have winners.
     */
    private void startNewEdition() {
        if (currentRaffle != null) {
            UI.printEndRaffleConfirmationMessage();
            if (UI.userInput(INPUT_TYPE.RAW_TEXT).equalsIgnoreCase("sim")){
                UI.printRaffleInterruptionMessage();

                currentRaffle = new Raffle();
                numberOfRaffles++;
                UI.printRaffleCreationMessage();
                return;
            }
            UI.printEndRaffleCancelationMessage();
            return;
        }
        currentRaffle = new Raffle();
        numberOfRaffles++;
        UI.printRaffleCreationMessage();
    }

    /**
     * Registers a new bet from info given by the user.
     * 
     * <p>Displays an error message if there are no editions running.
     */
    private void registerBet() {
        if (currentRaffle == null) {
            UI.printNoRunningRafflesMessage();
            return;
        }
        String[] betterInfo; // [0] stores the user's name and [1] stores user's cpf
        Bet newBet;
        int betId;

        betId = BET_ID_STARTING_NUMBER + currentRaffle.getNumberOfBets();
        betterInfo = askUserInfo();
        newBet = new Bet(numberOfRaffles ,betId, betterInfo[0], betterInfo[1]);

        askBetNumbers(newBet);
        currentRaffle.addBet(newBet);
        UI.printBetRegisterConfirmationMessage(newBet);
    }

    /**
     * Asks the user's name and cpf.
     * @return An array that holds the user's name at [0] and 
     *              the user's cpf at [1].
     */
    private String[] askUserInfo() {
        String[] returnVariable = new String[2];
        String betterName;
        String betterCpf;

        UI.printAskingUserNameMessage();
        betterName = UI.userInput(INPUT_TYPE.RAW_TEXT);
        UI.printAskingUserCpfMessage();
        betterCpf = UI.userInput(INPUT_TYPE.CPF);

        returnVariable[0] = betterName;
        returnVariable[1] = betterCpf;
        return returnVariable;
    }

    /**
     * Asks for the numbers the user wants to bet on.
     * Does not allow to input the same number twice.
     * @param bet will store the given numbers
     */
    private void askBetNumbers(Bet bet) {
        int[] returnVariable = new int[NUMBERS_PER_BET];
        int input = -1;
        boolean flagChooseForMe = false;

        for (int i = 0; i < NUMBERS_PER_BET; i++) {
            if (!flagChooseForMe) {
                UI.printAskingBetNumberMessage(i + 1);
                input = Integer.parseInt(UI.userInput(INPUT_TYPE.BET_NUMBER));
            }
            if (input == SPECIAL_OCASION_NUMBER) {
                flagChooseForMe = true;
                returnVariable[i] = bet.chooseNumberForMe();
                UI.printChosenNumberMessage(returnVariable[i]);
            } else {
                returnVariable[i] = input;
                if (!bet.bet(input)) { // if this number is already on this bet
                    i--; //  then keep the numerical iterator
                    UI.printRepeatedNumberInBetMessage();
                }
            }
        }
    }

    /**
     * Displays data from every bet registered.
     * 
     * <p>Displays an error message if there are no editions running.
     */
    private void displayBetData() {
        if (currentRaffle == null) {
            UI.printNoRunningRafflesMessage();
            return;
        }
        UI.printRaffleData(currentRaffle.getBets());
    }

    /**
     * Asks for confirmation and starts the drawing and inveitigation phases.
     * 
     * <p>Displays every number drawn,
     * how many drawing (and investigation) phases there were
     * how many bets won
     * lists info from winning bets (if aplicable) and displays a table
     * with every nuber bet this edition and how many times it was bet on, 
     * in descending order.
     * 
     * <p>Displays an error message if there are no editions running.
     */
    private void drawWinners() {
        if (currentRaffle == null) {
            UI.printNoRunningRafflesMessage();
            return;
        }

        UI.printDrawRafflesNumbersConfirmationMessage();
        if (!(UI.userInput(INPUT_TYPE.RAW_TEXT).equalsIgnoreCase("sim"))) {
            UI.printDrawRafflesNumbersCancelationMessage();
            return;
        }

        Set<Bet> currentWinners;
        int[] winnerNumbers;
        PairedInt[] everyBetNumber; //[0] stores the numbers bet and [1] stores their respective frequencies
        int pointsAWinnerHas;


        currentWinners = currentRaffle.drawWinners();
        winnerBets.addAll(currentWinners);
        winnerNumbers = currentRaffle.getWinnerNumbers();
        everyBetNumber = sortByFrequency(currentRaffle.getAllBetNumbers());
        Arrays.sort(winnerNumbers);  // sorts in  ascending order
        Arrays.sort(everyBetNumber); // sorts in descending order
        pointsAWinnerHas = (POINTS_FOR_WINNER_BETS + PrizePool.getLastDistributedPoints());
        currentRaffle = null;

        UI.printDrawingNumbersAnnouncementMessage(winnerNumbers);
        if (!(currentWinners.isEmpty())) {
            UI.printSomeoneWonMessage(currentWinners, pointsAWinnerHas);
            UI.printEveryBetNumber(everyBetNumber);
            return;
        }
        UI.printNobodyWonMessage(pointsAWinnerHas);
        UI.printEveryBetNumber(everyBetNumber);
    }

    /**
     * Uses {@code PairedInt} to match the {@code keys} and {@code values} 
     * of a map and stores those in an array ordered by the values in 
     * descending order.
     * @param everyBetNumber is the {@code HashMap} with the keys and values
     * @return An array storing the paired values.
     */
    private PairedInt[] sortByFrequency(HashMap<Integer, Integer> everyBetNumber) {
        int[] betNumbers = everyBetNumber.keySet().stream().mapToInt(Integer::intValue).toArray();
        int[] frequency = everyBetNumber.values().stream().mapToInt(Integer::intValue).toArray();
        
        PairedInt[] returnValue = new PairedInt[betNumbers.length];

        for (int i = 0; i < betNumbers.length; i++) {
            returnValue[i] = new PairedInt(betNumbers[i], frequency[i]);
        }
        Arrays.sort(returnValue);
        return returnValue;
    }

    /**
     * <p>Checks if the user knows:
     * <ul>
     *  <li>Raffle edition</li>
     *  <li>Bet Id</li>
     *  <li>Better CPF</li>
     * </ul>
     *  of a winnig bet.
     * 
     * <p>If the user inputs correct info, gives them a prize.
     */
    private void getPrize() {
        int raffleId;
        int betId;
        String cpf;

        UI.printAskingRaffleIdMessage();
        raffleId = Integer.parseInt(UI.userInput(INPUT_TYPE.RAFFLE_ID));
        if (raffleId > numberOfRaffles) {
            UI.printNoRaffleWithIdMessage();
            return;
        }
        UI.printAskingBetIdMessage();
        betId = Integer.parseInt(UI.userInput(INPUT_TYPE.BET_ID));
        UI.printAskingCpfConfirmationMessage();
        cpf = Bet.formatCpf(UI.userInput(INPUT_TYPE.CPF));

        for (Bet b : winnerBets) {
            if (    (b.getRaffleId() == raffleId) && 
                    (b.getId() == betId) && 
                    (cpf.equals(b.getBetterCpf())) &&
                    (b.getPoints() >= REWARD_POINT_PRIZE)) {
                b.removePoints(REWARD_POINT_PRIZE);
                URL_LINKS link = URL_LINKS.getRandomLink();
                UI.printCongratulatoryMessage();
                UI.printReward(link.id, link.message, link.url);
                Rewards.giveReward(link);
                if (b.getPoints() < REWARD_POINT_PRIZE) {
                    winnerBets.remove(b);
                    UI.printRemovedFromRewardsSet();
                    return;
                }
                UI.printStillHasAPrize();
                return;
            }
        }
        UI.printWrongBetIdOrCpfMessage();
    }
}