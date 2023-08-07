package engine;

import trading.BotState;

public class InverseGreedyStrategy extends Strategy{

    @Override
    protected int calculateBidSize(BotState playerState){

        return playerState.getCash() / ((playerState.getAvailableQuantity() / 4) + 2);

    }

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        return playerState.getCash() >= bidSize ? bidSize : 0;

    }
}