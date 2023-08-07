package engine;

import trading.BotState;

/**
 * This strategy will flip a coin to determine whether to bid or not.
 */
public class FlipStrategy extends Strategy{

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        updateBidSize(playerState);

        if(playerState.getCash() >= bidSize && Math.random() > 0.3){

            return bidSize;

        }else {

            return 0;

        }

    }
}
