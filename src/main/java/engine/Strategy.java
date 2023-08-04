package engine;

import trading.BotState;

public abstract class Strategy {

    public abstract int determineBid(BotState myState, BotState enemyState);

}
