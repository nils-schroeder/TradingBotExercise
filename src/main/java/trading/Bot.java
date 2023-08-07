package trading;

import auction.Bidder;
import engine.Strategy;
import engine.StrategyFactory;
import engine.StrategyName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.SplittableRandom;

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

    protected BotState playerState;
    protected BotState otherState;
    protected Strategy strategy;
    private static final Logger logger = LogManager.getLogger();

    private Class<Strategy> strategyClass;

    protected final StrategyName strategyName;

    public Bot(){
        this.strategyName = StrategyName.DEFAULT;
    }

    public Bot(StrategyName strategyName){
        this.strategyName = strategyName;
    }

    @Override
    public void init(int quantity, int cash) {

        if(quantity < BotStateResolver.WIN_REWARD || cash < 0){
            throw new IllegalArgumentException("Quantity and cash must be positive");
        }

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
        int bid = strategy.determineBid(
                playerState,
                otherState
        );

        if(bid > playerState.getCash() || bid < 0){

            logger.error("Bid is not valid");

            return 0;
        }else{
            return bid;
        }
    }

    @Override
    public void bids(int own, int other) {

        if(own < 0 || other < 0){
            throw new IllegalArgumentException("Bids cannot be negative");
        }

        BotStateResolver.resolveBids(
                own,
                other,
                playerState,
                otherState
        );

    }
}
