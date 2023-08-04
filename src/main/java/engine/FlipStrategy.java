package engine;

import trading.BotState;

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
