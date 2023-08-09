package trading;

/**
 * Provides utility functions to determine the outcome of bot bid competitions.
 */
public class BotStateResolver {

    // Constants representing the rewards based on competition outcomes.
    /** Reward given when a bot wins in a bid competition. */
    public static final int WIN_REWARD = 2;
    /** Reward given when a bot ties in a bid competition. */
    public static final int TIE_REWARD = 1;
    /** Reward given when a bot loses in a bid competition. */
    public static final int LOSS_REWARD = 0;


    /**
     * Resolves the bids of two bots and updates their respective states based on the outcome.
     * <p>
     * Bids are compared to determine if a bot wins, ties, or loses. Based on the result, each bot's
     * state is updated with rewards and deductions.
     *
     * @param ownCash    The cash bid by the first bot.
     * @param otherCash  The cash bid by the second bot.
     * @param ownState   The current state of the first bot.
     * @param otherState The current state of the second bot.
     * @throws IllegalArgumentException If any bid is negative or exceeds the bot's starting cash.
     */
    public static void resolveBids(int ownCash, int otherCash, BotState ownState, BotState otherState) throws IllegalArgumentException {

        if (ownCash < 0 || otherCash < 0) {
            throw new IllegalArgumentException("Bids cannot be negative");
        } else if (ownCash > ownState.getStartCash() || otherCash > otherState.getStartCash()) {
            throw new IllegalArgumentException("You can only bid up to your cash");
        }

        int ownAward = LOSS_REWARD;
        int otherAward = LOSS_REWARD;

        if (ownCash == otherCash) {
            ownAward = TIE_REWARD;
            otherAward = TIE_REWARD;
        } else if (ownCash > otherCash) {
            ownAward = WIN_REWARD;
        } else {
            otherAward = WIN_REWARD;
        }

        int totalPayout = ownAward + otherAward;

        ownState.update(
                ownAward,
                ownCash,
                totalPayout
        );

        otherState.update(
                otherAward,
                otherCash,
                totalPayout
        );
    }
}