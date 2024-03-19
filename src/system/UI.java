package system;

// Java library imports
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

// File imports
import static complementaries.Constants.Variables.*;
import complementaries.INPUT_TYPE;
import complementaries.comparators.PairedInt;
import complementaries.exceptions.*;

public interface UI {
    /**
     * Prints the app's menu and asks for an input
     */
    public static void menu() {
        System.out.println(DIVIDER);
        System.out.printf("[%s1%s] Iniciar uma nova edição de sorteio%n",
                                COLOUR_BLUE, COLOUR_RESET);
        System.out.printf("[%s2%s] Registrar nova aposta%n",
                                COLOUR_BLUE, COLOUR_RESET);
        System.out.printf("[%s3%s] Listas apostas do sorteio atual%n",
                                COLOUR_BLUE, COLOUR_RESET);
        System.out.printf("[%s4%s] Finalizar apostas e executar o sorteio%n",
                                COLOUR_BLUE, COLOUR_RESET);
        System.out.printf("[%s5%s] Resgatar prêmio do sorteio%n",
                                COLOUR_BLUE, COLOUR_RESET);
        System.out.printf("[%s%d%s] Sair do programa%n",
                                COLOUR_RED, SPECIAL_OCASION_NUMBER, COLOUR_RESET);
        System.out.printf("%n%sDigite o número equivalente ao que você deseja fazer: %s",
                                    COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Reads the user's input
     * @param option determines which type of input it will read
     * @return input read.
     */
    public static String userInput(INPUT_TYPE option) {
        switch (option) {
            case MENU:
                return "" + userInput(START_OF_OPTIONS_IN_MENU, NUMBER_OF_OPTIONS_IN_MENU);
            case BET_NUMBER:
                return "" + userInput(LOWER_BOUND_OF_VALID_NUMBERS, UPPER_BOUND_OF_VALID_NUMBERS);
            case RAW_TEXT:
                return scanner.nextLine();
            case CPF:
                return inputCPF();
            case BET_ID:
                return "" + userInput(BET_ID_STARTING_NUMBER);
            case RAFFLE_ID:
                return "" + userInput(1); 
        }
        return "";
    }
    
    /**
     * Reads an {@code int} input.
     * <p> Input is only valid if it's in-between {@code lowerBound} and {@code upperBound}.
     * @param lowerBound lowest valid value for the input (inclusive)
     * @param upperBound highest valid value for the input (inclusive)
     * @return The user's valid input
     */
    private static int userInput(int lowerBound, int upperBound) {
        boolean flagInvalidInput;
        int input = 0;

        do {
            flagInvalidInput = false;
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input == SPECIAL_OCASION_NUMBER) {
                    throw new SpecialOccasionException();
                }
                if ((input < lowerBound) || (input > upperBound)) {
                    throw  new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Você não escreveu um número, por favor tente novamente: ");
                flagInvalidInput = true;
            } catch (IllegalArgumentException e) {
                System.out.printf("Você deve escolher um número entre %s%d%s e %s%d%s (ou %s%d%s), " + 
                                  "por favor tente novamente: ",
                                  COLOUR_BLUE, lowerBound, COLOUR_RESET, 
                                  COLOUR_BLUE, upperBound, COLOUR_RESET,
                                  COLOUR_BLUE, SPECIAL_OCASION_NUMBER, COLOUR_RESET);
                flagInvalidInput = true;
            } catch (SpecialOccasionException e) {
                // if it's the 'special ocasion number', let it go.
            } catch (Exception e) {
                System.out.print("Algo deu errado, por favor tente novamente: ");
                flagInvalidInput = true;
            }
        } while (flagInvalidInput);

        return input;
    }

    /**
     * Reads an {@code int} input.
     * <p> Input is only valid if it's higher than {@code lowerBound}.
     * @param lowerBound lowest valid value for the input (inclusive)
     * @return The user's valid input
     */
    private static int userInput(int lowerBound) {
        boolean flagInvalidInput;
        int input = 0;

        do {
            flagInvalidInput = false;
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input < lowerBound) {
                    throw  new IllegalArgumentException();
                }
            } catch (NumberFormatException e) {
                System.out.print("Você não escreveu um número, por favor tente novamente: ");
                flagInvalidInput = true;
            } catch (IllegalArgumentException e) {
                System.out.printf("Você deve escolher um número maior que %s%d%s, por favor tente novamente: ",
                                        COLOUR_BLUE, lowerBound, COLOUR_RESET);
                flagInvalidInput = true;
            } catch (Exception e) {
                System.out.print("Algo deu errado, por favor tente novamente: ");
                flagInvalidInput = true;
            }
        } while (flagInvalidInput);

        return input;
    }

    /**
     * Reads a {@code String} input.
     * <p> Input is only valid if it's formatted acording to {@code REGEX_VALIDATE_CPF}
     * @return The user's valid input
     */
    private static String inputCPF() {
        boolean flagInvalidInput;
        String input = "";

        do {
            flagInvalidInput = false;
            try {
                input = scanner.nextLine();
                if (!input.matches(REGEX_VALIDATE_CPF)) {
                    throw new IllegalStringFormatException();
                }
            } catch (IllegalStringFormatException e) {
                System.out.printf("%n%sO CPF que você digitou não está em um formato válido!%s%n", 
                                    COLOUR_RED, COLOUR_RESET);
                System.out.println("Por favor, tente escrever em um dos seguintes fotmatos:");
                System.out.printf("%s00000000000%s%n", COLOUR_YELLOW, COLOUR_RESET);
                System.out.printf("%s000.000.000-00%s%n", COLOUR_YELLOW, COLOUR_RESET);
                System.out.print("Tente novamente: ");
                flagInvalidInput = true;
            } catch (Exception e) {
                System.out.print("Algo deu errado, por favor tente novamente: ");
                flagInvalidInput = true;
            }
        } while (flagInvalidInput);

        return input;
    }

    /**
     * Waits {@code SECONDS_TO_BUILD_SUSPENSE} seconds, to build suspense.
     */
    private static void waitTimeForSuspense() {
        if (HAS_SUSPENSE) {
            System.out.printf("%n%sAguarde enquanto os números são sorteados%s",
                                    COLOUR_YELLOW, COLOUR_RESET);
            for (int i = 0; i <= SECONDS_TO_BUILD_SUSPENSE; i++) {
                waitSecond(i, true);
            }
        }
    }

    private static void waitSecond(int iterator, boolean displayCountdown) {
        try {
            TimeUnit.SECONDS.sleep(1);
            if (displayCountdown) {
                System.out.printf("%n%ds", SECONDS_TO_BUILD_SUSPENSE - iterator);
            }
        } catch (InterruptedException e) {
            // let it go, not a big deal if count is interrupted
        }
    }

    /**
     * Prints a warning stating that there are currently no raffles running in Brazilian Portuguese.
     */
    public static void printNoRunningRafflesMessage() {
        System.out.printf("%sNão há sorteios ocorrendo no momento%s, tente iniciar uma nova edição%n",
                                COLOUR_RED, COLOUR_RESET);
    }

    /**
     * Prints a warning stating that the current raffle was interrupted in Brazilian Portuguese.
     */
    public static void printRaffleInterruptionMessage() {
        System.out.printf("%sO sorteio atual foi interrompido%s%n",
                                COLOUR_RED, COLOUR_RESET);
        System.out.printf("%sNenhum%s número foi sorteado e %snenhum%s ganhador foi estabelecido%n",
                                COLOUR_PURPLE, COLOUR_RESET,
                                COLOUR_PURPLE, COLOUR_RESET);
        System.out.printf("%sPontos desta rodada serão transferidos para a próxima%s%n",
                                COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Prints a message stating that a new raffle has starded in Brazilian Portuguese.
     */
    public static void printRaffleCreationMessage() {
        System.out.printf("%sUm novo sorteio foi iniciado%s%n", COLOUR_GREEN, COLOUR_RESET);
    }

    /**
     * Prints a message asking for a name input in Brazilian Portuguese.
     */
    public static void printAskingUserNameMessage() {
        System.out.println(DIVIDER);
        System.out.printf("Por favor, digite o seu %snome completo%s: ",
                                COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Prints a message asking for cpf in Brazilian Portuguese.
     */
    public static void printAskingUserCpfMessage() {
        System.out.printf("Por favor, digite o seu %sCPF%s: ", COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Prints a message asking for bet number in Brazilian Portuguese.
     * @param numero represents the user's {@code K}th number 
     */
    public static void printAskingBetNumberMessage(int numero) {
        System.out.printf("Digite o número em que quer apostar (de %s1%s a %s%d%s)",
                                COLOUR_BLUE, COLOUR_RESET,
                                COLOUR_BLUE, UPPER_BOUND_OF_VALID_NUMBERS , COLOUR_RESET);
        System.out.printf(" ou digite %s%d%s aleatorizar os números restantes",
                                COLOUR_BLUE, SPECIAL_OCASION_NUMBER, COLOUR_RESET);
        System.out.printf("%nO seu %s%d°%s número: ", COLOUR_BLUE, numero, COLOUR_RESET);
    }

    /**
     * Prints a message asking for raffle edition number in Brazilian Portuguese.
     */
    public static void printAskingRaffleIdMessage() {
        System.out.printf("Por favor, digite o %snúmero da edição%s do sorteio: ", COLOUR_BLUE, COLOUR_RESET);
    }

    /**
     * Prints a warning stating that there are no raffles with that id in Brazilian Portuguese.
     */
    public static void printNoRaffleWithIdMessage() {
        System.out.printf("%sNão há edições com esse id%s, retornando ao menu...%n",
                                COLOUR_RED, COLOUR_RESET);
    }

    /**
     * Prints a message asking for bet id in Brazilian Portuguese.
     */
    public static void printAskingBetIdMessage() {
        System.out.printf("Por favor, digite o %sID%s da sua aposta: ", COLOUR_BLUE, COLOUR_RESET);
    }

    /**
     * Prints a message asking for CPF confirmation in Brazilian Portuguese.
     */
    public static void printAskingCpfConfirmationMessage() {
        System.out.printf("Se essa aposta existir, precisamos confirmar que você é o apostador.%n");
        printAskingUserCpfMessage();
    }

    /**
     * Prints a message stating that {@code number} was chosen at random in Brazilian Portuguese.
     * @param number the chosen number
     */
    public static void printChosenNumberMessage(int number) {
        System.out.printf("%s%d%s foi escolhido aleatóriamente%n",
                                COLOUR_BLUE, number, COLOUR_RESET);
    }

    /**
     * Prints a message stating that a bet was registered in Brazilian Portuguese.
     * Then displays the bet full data.
     * @param bet will have its info printed
     */
    public static void printBetRegisterConfirmationMessage(Bet bet) {
        System.out.printf("%sA sua aposta foi registrada com sucesso!%s%n",
                                COLOUR_GREEN, COLOUR_RESET);
        System.out.println(bet.toString(true));
    }

    /**
     * Prints a message stating that a number has already been chosen in Brazilian Portuguese.
     */
    public static void printRepeatedNumberInBetMessage() {
        System.out.printf("%sEsse número já foi escolhido para esta aposta, tente outro!%s%n",
                                 COLOUR_RED, COLOUR_RESET);
    }

    /**
     * Prints every regitered bet from the current edition in Brazilian Portuguese.
     * @param bets {@code HashSet} storing the registered bets.
     */
    public static void printRaffleData(HashSet<Bet> bets) {
        if (bets.isEmpty()) {
            System.out.printf("Atualmente, %snão há%s apostas registradas neste sorteio%n",
                                COLOUR_PURPLE, COLOUR_RESET);
            return;
        }
        System.out.printf("%sDados das apostas já registradas%s:%n",
                                COLOUR_YELLOW, COLOUR_RESET);
        for (Bet b : bets) {
            System.out.println(b.toString(false));
        }
    }

    /**
     * Prints a message asking for a confirmation to end the current edition in Brazilian Portuguese.
     */
    public static void printEndRaffleConfirmationMessage() {
        System.out.printf("Uma edição já está acontecendo, %siniciar uma nova edição significa",
                                COLOUR_RED);
        System.out.printf(" interromper a edição atual%s%n", COLOUR_RESET);
        System.out.printf("%sPara continuar escreva %sSim%s: ",
                                COLOUR_RED, COLOUR_GREEN, COLOUR_RESET);
    }

    /**
     * Prints a message stating that a new edition didn't start in Brazilian Portuguese.
     */
    public static void printEndRaffleCancelationMessage() {
        System.out.printf("Uma nova edição %snão%s foi iniciada", COLOUR_RED, COLOUR_RESET);
    }

    public static void printDrawRafflesNumbersConfirmationMessage() {
        System.out.printf("Esta ação irá finalizar a edição atual e sortear os %snúmeros vencedores%s%n",
                                COLOUR_BLUE, COLOUR_RESET);
        System.out.printf("%sPara continuar escreva %sSim%s: ",
                                COLOUR_YELLOW, COLOUR_GREEN, COLOUR_RESET);
    }

    /**
     * Prints a message stating that the raffle wasn't stopped in Brazilian Portuguese.
     */
    public static void printDrawRafflesNumbersCancelationMessage() {
        System.out.printf("O sorteio %snão%s foi finalizado!%n", COLOUR_RED, COLOUR_RESET);
    }

    /**
     * Prints a message stating that numbers are being drawn, which numbers were drawn and
     * how many drawing rounds there were in Brazilian Portuguese.
     * @param drawnNumbers stores the numbers that were drawn
     */
    public static void printDrawingNumbersAnnouncementMessage(int[] drawnNumbers) {
        StringBuilder numbers = new StringBuilder("[");

        // System.out.printf("%n%n%n%n");
        System.out.printf(DIVIDER);
        System.out.printf("%s***** O SORTEIO ESTÁ SENDO REALIZADO *****%s",
                                COLOUR_GREEN, COLOUR_RESET);
        waitTimeForSuspense();
        System.out.printf(DIVIDER);
        System.out.printf("%sOs números vencedores são: %s%n", COLOUR_YELLOW, COLOUR_RESET);
        for (int i : drawnNumbers) {
            numbers.append(COLOUR_BLUE + i + COLOUR_RESET + ", ");
        }
        System.out.println(numbers.substring(0, (numbers.length() - 2)) + "]");
        System.out.printf("Ao todo, %s%d%s rodadas foram realizadas%n",
                                COLOUR_BLUE, (drawnNumbers.length - (AMOUNT_OF_INITIAL_DRAW_NUMBERS - 1)),
                                COLOUR_RESET);
    }

    /**
     * Prints a message stating that one or more people have winner bets in Brazilian Portuguese.
     * @param winners stores every winning bet
     */
    public static void printSomeoneWonMessage(Set<Bet> winners, int points) {
        int numberOfRewards = (points / REWARD_POINT_PRIZE);
        // to retrieve elements from the set
        Bet[] winnersArray = new Bet[winners.size()];
        int aux = 0;
        for (Bet b : winners) {
            winnersArray[aux++] = b;
        }

        // sort the array by name (true alphabetical order)
        Comparator<Bet> reference = Comparator.comparing(Bet::getBetterName);
        Arrays.sort(winnersArray, reference);

        if (winners.size() == 1) {
            System.out.printf("Houve apenas %sum%s ganhador no %sSorteio %d%s%n",
                                    COLOUR_BLUE, COLOUR_RESET, COLOUR_YELLOW,
                                    winnersArray[0].getRaffleId(), COLOUR_RESET);
            System.out.printf("%sParabéns %s%s%s, resgate seu prêmio através do %sID%s da sua aposta%n",
                                    COLOUR_YELLOW, COLOUR_CYAN, winnersArray[0].getBetterName(),
                                    COLOUR_YELLOW, COLOUR_BLUE, COLOUR_RESET);
            printWinnerBets(winnersArray);
            System.out.printf("Sua aposta agora possui %s%d%s pontos para serem resgatados.%n",
                                    COLOUR_BLUE, points, COLOUR_RESET);
            System.out.printf("Isso equivale a %s%d%s recompensa",
                                    COLOUR_BLUE, numberOfRewards, COLOUR_RESET);
            if (numberOfRewards != 1) {
                System.out.print("s");
            }
            System.out.println(".\n");
            return;
        }
        
        StringBuilder names = new StringBuilder();

        System.out.printf("Houveram %s%d%s ganhadores no %sSorteio %d%s%n",
                                 COLOUR_BLUE, winnersArray.length, COLOUR_RESET, COLOUR_YELLOW,
                                 winnersArray[0].getRaffleId(), COLOUR_RESET);
        System.out.printf("%sParabéns", COLOUR_YELLOW);
        
        for (int i = 0; true; i++) {
            names.append(COLOUR_CYAN + winnersArray[i].getBetterName() + COLOUR_YELLOW);
            if (i == winnersArray.length - 2) { // if this is the second last
                names.append(" e ");
                names.append(COLOUR_CYAN + winnersArray[i + 1].getBetterName()); // adds the last
                names.append(COLOUR_YELLOW + ", ");
                break;
            }
            names.append(", ");
            if ((i != 0) && (i % NUMBER_OF_NAMES_PER_LINE == 0)) {
                names.append("\n");
            }
        }
        System.out.printf(" %s%sresgatem seus prêmios através dos %sIDs%s das suas apostas%s%n",
                                names.toString(),COLOUR_YELLOW, COLOUR_BLUE, COLOUR_YELLOW, COLOUR_RESET);
        printWinnerBets(winnersArray);
        System.out.printf("Suas apostas agora possuem %s%d%s pontos para serem resgatados.%n",
                                COLOUR_BLUE, points, COLOUR_RESET);
        System.out.printf("Isso equivale a %s%d%s recompensa",
                                COLOUR_BLUE, numberOfRewards, COLOUR_RESET);
        if (numberOfRewards != 1) {
            System.out.print("s");
        }
        System.out.println(".\n");
    }

    /**
     * Prints every winning bet in Brazilian Portuguese.
     * @param winners stores every winning bet
     */
    private static void printWinnerBets(Bet[] winners) {
        for (Bet b : winners) {
            System.out.println(b.toString(false) + "\n");
        }
    }

    /**
     * Prints a message stating that nobody has a winning bet for this edition in Brazilian Portuguese.
     */
    public static void printNobodyWonMessage(int points) {
        System.out.printf("%nParece que %sninguém venceu%s esta edição, que pena!%n",
                                COLOUR_PURPLE, COLOUR_RESET);
        System.out.printf("%sA próxima edição valerá mais pontos%s!%n", COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Prints a table storing every number bet and the frequency it was bet on in Brazilian Portuguese.
     */
    public static void printEveryBetNumber(PairedInt[] everyBetNumber) {
        System.out.println("Esta é uma tabela de todos os números apostados:");
        System.out.print("Número apostado\t|Quantidade de apostas");

        for (PairedInt p : everyBetNumber){
            if (p.frequency == 0) {
                continue;
            }
            System.out.printf("%n%s%d%s\t\t|%s%d%s",
                                    COLOUR_BLUE, p.number, COLOUR_RESET,
                                    COLOUR_BLUE, p.frequency, COLOUR_RESET);
        }
    }

    /**
     * Prints a message congratulating every winner for this edition in Brazilian Portuguese.
     */
    public static void printCongratulatoryMessage() {
        System.out.print(DIVIDER);
        System.out.printf("%s***** PARABÉNS, VOCÊ É O VENCEDOR DE UM DOS SORTEIOS! *****%s%n",
                                COLOUR_GREEN, COLOUR_RESET);
    }

    /**
     * Prints a message displaying which prize the user won in Brazilian Portuguese.
     */
    public static void printReward(int id, String message, String link) {
        System.out.printf("%sSeu prêmio é (%s%d%s/%s%d%s): %s%s%s%n",
                                COLOUR_YELLOW, COLOUR_BLUE, id, COLOUR_YELLOW,
                                COLOUR_BLUE, NUMBER_OF_REWARDS, COLOUR_YELLOW,
                                COLOUR_CYAN, message, COLOUR_RESET);
        System.out.printf("Se uma página da web não abrir em alguns instantes, acesse o seguinte link: %s%s%s%n",
                                COLOUR_CYAN, link, COLOUR_RESET);
        for (int i = 0; i < SECONDS_TO_WAIT_FOR_REWARD; i++) {
            waitSecond(i, false);
        }
    }

    /**
     * Prints a message stating that the user has been removed from the winners list 
     * because they have already received their prize in Brazilian Portuguese.
     */
    public static void printRemovedFromRewardsSet() {
        System.out.printf("Sua recompensa foi resgatada, %ssua aposta foi retirada da lista%s%n",
                                COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Prints a message stating that the user's bet has so many points, it can retrieve 
     * another reward in Brazilian Portuguese.
     */
    public static void printStillHasAPrize() {
        System.out.printf("Sua aposta possui muitos pontos e, por isso, você pode %sresgatar outra " +
                                "recompensa%s%n", COLOUR_YELLOW, COLOUR_RESET);
    }

    /**
     * Prints a message stating that the user didn't input correct info in Brazilian Portuguese.
     */
    public static void printWrongBetIdOrCpfMessage() {
        System.out.printf("Uma ou mais das informações %snão confere%s com os vencedores, retornando ao menu...",
                                COLOUR_RED, COLOUR_RESET);
        System.out.printf("Ou a sua aposta não tem pontos o suficiente para resgatar uma recompensa");
    }
}