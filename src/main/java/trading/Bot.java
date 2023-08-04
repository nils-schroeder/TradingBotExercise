package trading;

import auction.Bidder;
import engine.Strategy;
import engine.StrategyFactory;

public class Bot implements Bidder{

    private BotState ownState;
    private BotState otherState;
    private Strategy strategy;

    private static final String STRATEGY_NAME = "any";

    @Override
    public void init(int quantity, int cash) {

        this.strategy = StrategyFactory
                .getInstance()
                .createStrategy(STRATEGY_NAME);

        this.ownState = new BotState(
                quantity,
                cash
        );

        this.otherState = new BotState(
                quantity,
                cash
        );

    }

    @Override
    public int placeBid() {
        return 0;
    }

    @Override
    public void bids(int own, int other) {

        BotStateResolver.getInstance().resolveBids(
                own,
                other,
                ownState,
                otherState
        );

    }
}
