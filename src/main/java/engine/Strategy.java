package engine;

import trading.BotState;

public abstract class Strategy {


    protected int bidSize;

    public abstract int determineBid(BotState playerState, BotState otherState);

    protected int calculateBidSize(BotState playerState){

        return playerState.getCash() / (playerState.getAvailableQuantity() / 2);

    }

    protected void updateBidSize(BotState playerState){

        bidSize = calculateBidSize(playerState);

    }

    public Strategy init(BotState playerState, BotState otherState){

        updateBidSize(playerState);

        return this;

    }

}
