package engine;

import trading.BotState;

/**
 * This strategy will flip a coin to determine whether to bid or not.
 */
public class FlipStrategy extends Strategy{

    private int betSize = -1;

    @Override
    public int determineBid(BotState myState, BotState enemyState) {

        if(betSize == -1){

            betSize = myState.getStartCash() / (myState.getStartQuantity() / 2);

        }

        if(myState.getCash() >= betSize && Math.random() > 0.3){

            return betSize;

        }else {

            return 0;

        }

    }
}
