package engine;

import trading.BidLogEntry;
import trading.BotState;
import trading.BotStateResolver;

import java.util.List;
import java.util.stream.Stream;

public class MixedStrategy extends Strategy{

    private int initialBidSize;
    private static final double NO_BET_PROBABILITY = 0.2;
    private int rollingAverageWindowSize;
    private double bidFactor = 1.2;
    private int bidOffset;

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

    private double rollingAverage(Stream<Integer> stream, int n) {

        return stream.mapToDouble(Integer::doubleValue).sum() / n;

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

        double rollingAverage = this.rollingAverage(
                otherState.getLatestBids(rollingAverageWindowSize),
                rollingAverageWindowSize);

        double standardDeviation = standardDeviation(
                otherState.getHistory().stream().map(BidLogEntry::cash).toList()
        );

        bidSize = (int) Math.round(rollingAverage) + bidOffset + (int) (standardDeviation * bidFactor);

        bidSize = bidSize > 0 ? bidSize : (int) (initialBidSize * 0.5);

        if(bidSize > playerState.getCash()){

            return 0;

        }

        if(Math.random() > NO_BET_PROBABILITY){

            return bidSize;

        }else {

            return (int) Math.round(initialBidSize * 0.01);

        }

    }

}
