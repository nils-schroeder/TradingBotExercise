package engine;

import trading.BotState;

/**
 * This strategy will flip a coin to determine whether to bid or not.
 */
public class FlipStrategy extends Strategy{

    @Override
    public int determineBid(BotState myState, BotState enemyState) {

        if(myState.getCash() > 0){

            return (int) Math.round(Math.random());

        }else {
            return 0;
        }

    }
}
