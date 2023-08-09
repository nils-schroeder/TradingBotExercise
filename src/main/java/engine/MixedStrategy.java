package engine;

import trading.BidLogEntry;
import trading.BotState;
import trading.BotStateResolver;

import java.util.List;

/**
 * Represents a mixed bidding strategy, which utilizes both rolling averages
 * and standard deviation in its decision-making process.
 * This strategy aims to adapt its bid dynamically by analyzing historical bids,
 * and introducing a degree of randomness to occasionally bid a lower amount.
 */
public class MixedStrategy extends Strategy{

    /**
     * Retrieves the initial bid size set during strategy initialization.
     *
     * @return The initial bid size.
     */
    public int getInitialBidSize() {
        return initialBidSize;
    }

    /**
     * Retrieves the window size used for calculating rolling averages.
     *
     * @return The window size for rolling average.
     */
    public int getRollingAverageWindowSize() {
        return rollingAverageWindowSize;
    }

    /**
     * Retrieves the bid offset which is added during bid determination.
     *
     * @return The bid offset.
     */
    public int getBidOffset() {
        return bidOffset;
    }

    private int initialBidSize;

    /** Probability of not placing a bid. */
    public static final double NO_BID_PROBABILITY = 0.2;

    /** Factor used to adjust the no bid amount. */
    public static final double NO_BID_FACTOR = 0.01;

    private int rollingAverageWindowSize;

    /** Factor used to adjust the bid based on the historical bids' standard deviation. */
    public static final double BID_FACTOR = 1.2;
    private int bidOffset;

    /**
     * Calculates the lower threshold for bidding based on the remaining quantity.
     *
     * @param quantityLeft The remaining quantity of items.
     * @return The computed lower threshold for bidding.
     */
    protected int lowerThreshold(int quantityLeft) {
        int noOfRounds = quantityLeft / BotStateResolver.WIN_REWARD;
        return noBidAmount() * noOfRounds;
    }

    /**
     * Computes the amount to bid when the strategy decides not to actively participate in the round.
     *
     * @return The amount for a "no bid" round.
     */
    protected int noBidAmount() {
        return (int) Math.round(initialBidSize * NO_BID_FACTOR);
    }

    /**
     * Initializes the mixed strategy with initial bid size and rolling average window size.
     *
     * @param playerState The state of the player.
     * @param otherState The state of the opponent.
     * @return The initialized strategy instance.
     */
    @Override
    public Strategy init(BotState playerState, BotState otherState){
        updateBidSize(playerState);
        updateRollingAverageWindowSize(playerState.getStartQuantity());
        initialBidSize = bidSize;
        bidOffset = (int) Math.round(Math.sqrt(initialBidSize));
        return this;
    }

    /**
     * Updates the window size used for calculating rolling averages based on starting quantity.
     *
     * @param startQuantity The starting quantity of items.
     */
    private void updateRollingAverageWindowSize(int startQuantity){
        int noOfRounds = startQuantity / BotStateResolver.WIN_REWARD;
        rollingAverageWindowSize = (int) Math.round(Math.sqrt(noOfRounds));
    }

    /**
     * Computes the rolling average from a list of integers.
     *
     * @param list List of integers for which rolling average is to be computed.
     * @return The calculated rolling average.
     */
    protected double rollingAverage(List<Integer> list) {
        return list.stream().mapToDouble(Integer::doubleValue).sum() / list.size();
    }

    /**
     * Computes the standard deviation for a list of integers.
     *
     * @param numbers List of integers for which standard deviation is to be computed.
     * @return The calculated standard deviation.
     */
    public static double standardDeviation(List<Integer> numbers) {
        double mean = numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        double variance = numbers.stream()
                .mapToDouble(n -> Math.pow(n - mean, 2))
                .sum() / numbers.size();

        return Math.sqrt(variance);
    }

    /**
     * Determines the bid amount based on a combination of rolling average, standard deviation, and the bid offset.
     *
     * @param playerState The current state of the player.
     * @param otherState The current state of the opponent.
     * @return The amount to bid for the current round.
     */
    @Override
    public int determineBid(BotState playerState, BotState otherState) {
        updateBidSize(playerState);
        double rollingAverage = this.rollingAverage(otherState.getLatestBids(rollingAverageWindowSize));
        double standardDeviation = standardDeviation(
                otherState.getHistory().stream().map(BidLogEntry::cash).toList()
        );
        bidSize = (int) Math.round(rollingAverage) + bidOffset + (int) (standardDeviation * BID_FACTOR);
        bidSize = bidSize > 0 ? bidSize : (int) (initialBidSize * 0.5);

        if(bidSize > playerState.getCash() || playerState.getCash() - bidSize < lowerThreshold(playerState.getAvailableQuantity())){
            return noBidAmount();
        } else {
            if(Math.random() > NO_BID_PROBABILITY){
                return bidSize;
            }else {
                return noBidAmount();
            }
        }
    }
}
