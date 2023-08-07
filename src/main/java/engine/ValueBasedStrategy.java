package engine;

import trading.BotState;

public class ValueBasedStrategy extends Strategy {

    private final double greedFactor;

    public ValueBasedStrategy(){
        this.greedFactor = 2;
    }
    public ValueBasedStrategy(double greedFactor){
        this.greedFactor = greedFactor;
    }

    // TODO: add if x > 1 clause to avoid performance issues
    private double bellFunction(double x) {

        return 2 / (Math.exp(24 * x) + Math.exp(-24 * x));

    }

    private double quantityLeadScaled(BotState playerState, BotState otherState) {

        int quantityDifference = playerState.getQuantity() - otherState.getQuantity();

        return (double) quantityDifference / playerState.getStartQuantity();

    }

    private double cashLeadInBids(BotState playerState, BotState otherState) {

        return (double) (playerState.getCash() - otherState.getCash()) / bidSize;
    }

    private double cashFactor(BotState playerState, BotState otherState) {

        double cashLead = cashLeadInBids(playerState, otherState);

        double quantityLead = quantityLeadScaled(playerState, otherState);

        return bellFunction(quantityLead) * cashLead;

    }

    private double winDistanceFactor(BotState botState) {

        int quantityRequiredForWin = (int) (botState.getStartQuantity() / greedFactor) + 1;

        int distanceToWin = quantityRequiredForWin - botState.getQuantity();

        double opportunityFactor = 1 - (double) botState.getAvailableQuantity() / botState.getStartQuantity();

        return Math.exp((double) (distanceToWin / botState.getStartQuantity()) ) * Math.exp(opportunityFactor);

    }

    private double cashValueFactor(BotState playerState, BotState otherState) {

        int cashDifference = -1 * (playerState.getCash() - otherState.getCash());

        int quantityDifference = playerState.getQuantity() - otherState.getQuantity();

        double quantityFactor = 1 - Math.abs((double) quantityDifference / playerState.getStartQuantity());

        double cashFactor = (double) cashDifference / playerState.getStartCash();

        return quantityFactor * cashFactor;

    }

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        //updateBidSize(playerState);

        int expectedEnemyBidSize = calculateBidSize(otherState);

        int bidBase = (int) (bidSize * winDistanceFactor(playerState));

        if(playerState.getCash() >= bidBase){
            return bidBase;
        } else {
            return 0;
        }

    }
}
