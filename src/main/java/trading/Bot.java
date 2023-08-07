package trading;

import auction.Bidder;
import engine.Strategy;
import engine.StrategyFactory;
import engine.StrategyName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger();

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

        try{

            BotStateResolver.resolveBids(
                    own,
                    other,
                    playerState,
                    otherState
            );

        }catch (IllegalArgumentException e){

            logger.error(e.getMessage());

        }



    }
}
