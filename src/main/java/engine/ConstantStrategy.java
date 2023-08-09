package engine;

import trading.BotState;

/**
 * Implements a strategy that consistently places a bid of a constant size.
 * This strategy does not adapt to changes in the environment or the actions of the opposing player.
 * It always bids the pre-determined size, irrespective of available cash or other factors.
 */
public class ConstantStrategy extends Strategy{

    /**
     * Determines the bid amount to be the pre-determined bid size.
     * This strategy consistently returns the same bid amount.
     *
     * @param playerState The state of the bot/player (not used in this strategy).
     * @param otherState The state of the opposing bot/player (not used in this strategy).
     * @return The constant bid amount.
     */
    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        return bidSize;
    }
}
