package engine;

import trading.BotState;

public class GreedyStrategy extends Strategy{

    @Override
    protected int calculateBidSize(BotState playerState){

        return playerState.getCash() / ((playerState.getAvailableQuantity() / 4) + 1);

    }

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        return playerState.getCash() >= bidSize ? bidSize : 0;

    }
}