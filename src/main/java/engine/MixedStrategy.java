package engine;

import trading.BidLogEntry;
import trading.BotState;
import trading.BotStateResolver;

import java.util.List;
import java.util.stream.IntStream;

public class MixedStrategy extends Strategy{

    public int getInitialBidSize() {
        return initialBidSize;
    }

    public int getRollingAverageWindowSize() {
        return rollingAverageWindowSize;
    }

    public int getBidOffset() {
        return bidOffset;
    }

    private int initialBidSize;
    public static final double NO_BID_PROBABILITY = 0.2;
    public static final double NO_BID_FACTOR = 0.01;

    private int rollingAverageWindowSize;
    public static final double BID_FACTOR = 1.2;
    private int bidOffset;

    protected int lowerThreshold(int quantityLeft) {

        int noOfRounds = quantityLeft / BotStateResolver.WIN_REWARD;

        return noBidAmount() * noOfRounds;

    }

    protected int noBidAmount() {

        return (int) Math.round(initialBidSize * NO_BID_FACTOR);

    }

    @Override
    public Strategy init(BotState playerState, BotState otherState){

        updateBidSize(playerState);
        updateRollingAverageWindowSize(playerState.getStartQuantity());
        initialBidSize = bidSize;
        bidOffset = (int) Math.round(Math.sqrt(initialBidSize));
        return this;

    }

    private void updateRollingAverageWindowSize(int startQuantity){

        int noOfRounds = startQuantity / BotStateResolver.WIN_REWARD;

        rollingAverageWindowSize = (int) Math.round(Math.sqrt(noOfRounds));

    }

    protected double rollingAverage(List<Integer> list) {

        return list.stream().mapToDouble(Integer::doubleValue).sum() / list.size();

    }

    public static double standardDeviation(List<Integer> numbers) {

        double mean = numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        double variance = numbers.stream()
                .mapToDouble(
                        n -> Math.pow(n - mean, 2)
                ).sum() / numbers.size();

        return Math.sqrt(variance);
    }



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
