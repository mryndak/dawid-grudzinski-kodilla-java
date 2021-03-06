package com.kodilla.rps;

public class GameProcessor {
    private final Player player1;
    private final Player player2;
    private final Rules rules;
    private final int winningRounds;
    private static int playerOption;


    public GameProcessor(final Player player1, final Player player2, final Rules rules, final int winningRounds) {
        this.player1 = player1;
        this.player2 = player2;
        this.rules = rules;
        this.winningRounds = winningRounds;
    }

    public static void startGame() {
        Player player1;
        Player player2;
        IOService.showWelcomeMessage();

        playerOption = IOService.getGamePlayerOption();
        if(playerOption == 1) {
            String playerName = IOService.getPlayerName();
            player1 = new HumanPlayer(playerName);
            player2 = new ComputerPlayer();
        } else if (playerOption == 2) {
            player1 = new ComputerPlayer("Komputer1");
            player2 = new ComputerPlayer("Komputer2");
        } else {
            String playerName1 = IOService.getPlayersName("pierwszego");
            player1 = new HumanPlayer(playerName1);
            String playerName2 = IOService.getPlayersName("drugiego");
            player2 = new HumanPlayer(playerName2);
        }
        int winningRounds = IOService.setWinningRounds();
        Rules rules = new RpsRules();
        GameProcessor gameProcessor = new GameProcessor(player1, player2, rules, winningRounds);
        gameProcessor.progressGame();
    }

    private void progressGame() {
        while ((player1.getScore() != winningRounds) && (player2.getScore() != winningRounds)) {
            IOService.presentScore(player1, player2);
            int winning;

            if (playerOption == 1) {
                int humanMove = player1.getMove();
                int computerMove = player2.getEnhancedMove(humanMove);
                winning = rules.selectionResult(humanMove, computerMove);
            } else {
                winning = rules.selectionResult(player1.getMove(), player2.getMove());
            }
            if (winning == 1) {
                player1.addScore();
                IOService.roundWinner(player1);
            } else if (winning == 2) {
                player2.addScore();
                IOService.roundWinner(player2);
            } else {
                IOService.verbalValidation(11);
            }
        }

        IOService.gameWinner(player1, player2);
        IOService.ending();
    }
}
