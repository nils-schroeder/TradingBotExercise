package trading;

import auction.Bidder;
import engine.Strategy;
import engine.StrategyFactory;
import engine.StrategyName;

public class Bot implements Bidder{

    public BotState getPlayerState() {
        return playerState;
    }

    public BotState getOtherState() {
        return otherState;
    }

    public StrategyName getStrategyName() {
        return strategyName;
    }

    private BotState playerState;
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

        playerState = new BotState(
                quantity,
                cash
        );

        otherState = new BotState(
                quantity,
                cash
        );

        strategy = StrategyFactory
                .createStrategy(strategyName)
                .init(playerState, otherState);

    }

    @Override
    public int placeBid() {
        return strategy.determineBid(
                playerState,
                otherState
        );
    }

    @Override
    public void bids(int own, int other) {

        BotStateResolver.resolveBids(
                    own,
                    other,
                    playerState,
                    otherState
            );

    }
}
