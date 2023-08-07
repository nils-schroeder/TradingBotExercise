package auction;

import engine.Strategy;
import trading.BotState;

public class MockRougeStrategy extends Strategy {

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        if(Math.random() > 0.5){

            return playerState.getCash() * 2;

        }else {

            return -1 * playerState.getCash();

        }
    }

}