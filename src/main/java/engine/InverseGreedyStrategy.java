package engine;

import trading.BotState;

/**
 * Implements the Inverse Greedy Strategy for determining bids.
 * This strategy uses the principle of bidding aggressively during the latter half of the game,
 * while placing no bids during the early phases.
 */
public class InverseGreedyStrategy extends Strategy{

    /**
     * Override of calculateBidSize to better suit the strategy.
     * Here, the bid size is inversely proportional to the number of rounds left.
     *
     * @param playerState The state of the bot/player.
     * @return The calculated bid size.
     */
    @Override
    protected int calculateBidSize(BotState playerState){
        return playerState.getCash() / ((playerState.getAvailableQuantity() / 4) + 1);
    }

    /**
     * Determines if the current round is within the aggressive bid window.
     *
     * @param playerState The state of the bot/player.
     * @return True if within the bid window, False otherwise.
     */
    private boolean bidWindow(BotState playerState) {
        int numberOfRounds = playerState.getStartQuantity() / 2;
        int roundsLeft = playerState.getAvailableQuantity() / 2;
        return roundsLeft <= (numberOfRounds / 2) + 1;
    }

    /**
     * Determines the bid based on the current state of the game and the strategy.
     *
     * @param playerState The state of the bot/player.
     * @param otherState The state of the opposing bot/player.
     * @return The determined bid amount.
     */
    @Override
    public int determineBid(BotState playerState, BotState otherState) {
        if (bidWindow(playerState)) {
            return playerState.getCash() >= bidSize ? bidSize : 0;
        } else {
            return 0;
        }
    }
}
