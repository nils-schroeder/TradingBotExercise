package engine;

import trading.BotState;

/**
 * Provides an abstract representation of a bidding strategy.
 * Subclasses of this class must define their own implementation of the {@link #determineBid(BotState, BotState)} method.
 * This class also provides utility methods for calculating and updating bid sizes.
 */
public abstract class Strategy {

    /** The size of the bid to be placed. */
    protected int bidSize;

    /**
     * Determines the bid amount based on the states of the player and the opponent.
     *
     * @param playerState The state of the player bot.
     * @param otherState  The state of the opponent bot.
     * @return The amount to be bid by the player bot.
     */
    public abstract int determineBid(BotState playerState, BotState otherState);

    /**
     * Calculates the bid size based on the player's available resources.
     *
     * @param playerState The state of the player bot.
     * @return The calculated bid size.
     */
    protected int calculateBidSize(BotState playerState){
        return playerState.getCash() / (playerState.getAvailableQuantity() / 2);
    }

    /**
     * Updates the bid size for the strategy based on the player's current state.
     *
     * @param playerState The state of the player bot.
     */
    protected void updateBidSize(BotState playerState){
        bidSize = calculateBidSize(playerState);
    }

    /**
     * Initializes the strategy with given states of the player and opponent.
     * Also updates the bid size based on the player's state.
     *
     * @param playerState The state of the player bot.
     * @param otherState  The state of the opponent bot.
     * @return The initialized strategy.
     */
    public Strategy init(BotState playerState, BotState otherState){
        updateBidSize(playerState);
        return this;
    }

}