package engine;

import trading.BotState;

/**
 * Implements a strategy that flips a coin (random chance) to decide on placing a bid.
 * The strategy checks if the bot has enough cash to place a bid and then, with 70% probability,
 * decides to place that bid. Otherwise, it bids zero.
 */
public class FlipStrategy extends Strategy{

    /**
     * If the player has enough cash and a random number is greater than 0.3 (70% chance),
     * the determined bid will be the calculated bid size. Otherwise, the bid will be 0.
     *
     * @param playerState The state of the bot/player.
     * @param otherState The state of the opposing bot/player (not used in this strategy).
     * @return The determined bid amount.
     */
    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        updateBidSize(playerState);

        if(playerState.getCash() >= bidSize && Math.random() > 0.3){

            return bidSize;

        }else {

            return 0;

        }

    }
}
