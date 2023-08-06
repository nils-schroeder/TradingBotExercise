package engine;

import trading.BotState;

public class ConstantStrategy extends Strategy{

    @Override
    public int determineBid(BotState playerState, BotState otherState) {

        return betSize;

    }
}