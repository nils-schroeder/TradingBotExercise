package engine;

import trading.BotState;

public abstract class Strategy {


    protected int betSize;

    public abstract int determineBid(BotState playerState, BotState otherState);

    public void updateBetSize(BotState playerState){

        betSize = playerState.getCash() / (playerState.getAvailableQuantity() / 2);

    }

    public Strategy init(BotState playerState, BotState otherState){

        updateBetSize(playerState);

        return this;

    }

}
