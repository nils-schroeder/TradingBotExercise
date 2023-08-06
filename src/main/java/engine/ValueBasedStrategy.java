package engine;

import trading.BotState;

public class ValueBasedStrategy extends Strategy{

    @Override
    public int determineBid(BotState playerState, BotState otherState) {
        return 0;
    }
}
