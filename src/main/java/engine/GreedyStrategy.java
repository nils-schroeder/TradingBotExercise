package engine;

import trading.BotState;

/**
 * Implements the Greedy Strategy for determining bids.
 * This strategy is aggressive in nature and attempts to win the first half + 1 of the rounds.
 */
public class GreedyStrategy extends Strategy{

    /**
     * Overrides the method to calculate bid size based on a greedy strategy.
     * The strategy divides the available cash by a fraction of the available quantity.
     *
     * @param playerState The state of the bot/player.
     * @return The calculated bid size.
     */
    @Override
    protected int calculateBidSize(BotState playerState){
        return playerState.getCash() / ((playerState.getAvailableQuantity() / 4) + 1);
    }

    /**
     * Determines the bid based on the greedy strategy. It places the maximum possible
     * bid (up to bidSize) based on available cash.
     *
     * @param playerState The state of the bot/player.
     * @param otherState The state of the opposing bot/player (not used in this strategy).
     * @return The determined bid amount.
     */
    @Override
    public int determineBid(BotState playerState, BotState otherState) {
        return playerState.getCash() >= bidSize ? bidSize : 0;
    }
}
