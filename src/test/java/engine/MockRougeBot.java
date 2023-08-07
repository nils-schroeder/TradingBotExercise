package engine;

import trading.Bot;
import trading.BotState;

public class MockRougeBot extends Bot{

    @Override
    public void init(int quantity, int cash) {

        playerState = new BotState(
                quantity,
                cash
        );

        otherState = new BotState(
                quantity,
                cash
        );

        strategy = new MockRougeStrategy();

    }

}
