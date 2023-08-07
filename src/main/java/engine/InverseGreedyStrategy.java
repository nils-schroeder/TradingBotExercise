package engine;

import trading.BotState;

public class InverseGreedyStrategy extends Strategy{

    @Override
    protected int calculateBidSize(BotState playerState){

        return playerState.getCash() / ((playerState.getAvailableQuantity() / 4) + 1);

    }

    private boolean bidWindow(BotState playerState) {

        int numberOfRounds = playerState.getStartQuantity() / 2;

        int roundsLeft = playerState.getAvailableQuantity() / 2;

        return roundsLeft <= (numberOfRounds / 2) + 1;

    }

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        if (bidWindow(playerState)) {

            return playerState.getCash() >= bidSize ? bidSize : 0;

        } else {

            return 0;

        }

    }
}