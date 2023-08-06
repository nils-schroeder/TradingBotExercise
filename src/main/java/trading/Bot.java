package trading;

import auction.Bidder;
import engine.Strategy;
import engine.StrategyFactory;
import engine.StrategyName;

public class Bot implements Bidder{

    public BotState getOwnState() {
        return ownState;
    }

    public BotState getOtherState() {
        return otherState;
    }

    private BotState ownState;
    private BotState otherState;
    private Strategy strategy;

    private final StrategyName strategyName;

    public Bot(){
        this.strategyName = StrategyName.DEFAULT;
    }

    public Bot(StrategyName strategyName){
        this.strategyName = strategyName;
    }

    @Override
    public void init(int quantity, int cash) {

        strategy = StrategyFactory
                .getInstance()
                .createStrategy(strategyName);

        ownState = new BotState(
                quantity,
                cash
        );

        otherState = new BotState(
                quantity,
                cash
        );

    }

    @Override
    public int placeBid() {
        return strategy.determineBid(
                ownState,
                otherState
        );
    }

    @Override
    public void bids(int own, int other) {

        BotStateResolver
                .getInstance()
                .resolveBids(
                    own,
                    other,
                    ownState,
                    otherState
            );

    }
}
