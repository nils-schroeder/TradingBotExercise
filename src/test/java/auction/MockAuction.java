package auction;

import engine.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trading.Bot;

import java.util.stream.IntStream;

public class MockAuction {

    private final int quantityTotal;
    private final int cashPerBot;

    public Bot getPlayerBot() {
        return playerBot;
    }

    public Bot getOtherBot() {
        return otherBot;
    }

    private Bot playerBot;
    private Bot otherBot;

    private static final Logger logger = LogManager.getLogger();

    public MockAuction(MockAuctionSetting settings){
        this.quantityTotal = settings.quantityTotal();
        this.cashPerBot = settings.cashPerBot();

        this.playerBot = new Bot(settings.playerStrategyClass());
        this.otherBot = new Bot(settings.otherStrategyClass());
    }

    public MockAuction(int quantityTotal, int cashPerBot, Bot playerBot, Bot otherBot){
        this.quantityTotal = quantityTotal;
        this.cashPerBot = cashPerBot;

        this.playerBot = playerBot;
        this.otherBot = otherBot;
    }

    public Class<? extends Strategy> run(){

        logger.debug("Running MockAuction with quantityTotal: [{}], cashPerBot: [{}]", quantityTotal, cashPerBot);

        playerBot.init(quantityTotal, cashPerBot);
        otherBot.init(quantityTotal, cashPerBot);

        IntStream.range(0, quantityTotal / 2).forEach(
            i -> {
                int playerBid = playerBot.placeBid();
                int otherBid = otherBot.placeBid();

                logger.debug("[{}] {} bid: [{}], {} bid: [{}]",
                        i,
                        playerBot.getStrategyName(),
                        playerBid,
                        otherBot.getStrategyName(),
                        otherBid
                );

                playerBot.bids(playerBid, otherBid);
                otherBot.bids(otherBid, playerBid);
            }
        );

        logger.debug("RESULT: {}: [Quantity: {} | Cash: {}], {}: [Quantity: {} | Cash: {}]",
                playerBot.getStrategyName(),
                playerBot.getPlayerState().getQuantity(),
                playerBot.getPlayerState().getCash(),
                otherBot.getStrategyName(),
                otherBot.getPlayerState().getQuantity(),
                otherBot.getPlayerState().getCash()
        );

        if(playerBot.getPlayerState().getQuantity() > otherBot.getPlayerState().getQuantity()){

            return playerBot.getStrategyClass();

        } else if(playerBot.getPlayerState().getQuantity() < otherBot.getPlayerState().getQuantity()){

            return otherBot.getStrategyClass();

        } else {

            if(playerBot.getPlayerState().getCash() > otherBot.getPlayerState().getCash()){

                return playerBot.getStrategyClass();

            } else if(playerBot.getPlayerState().getCash() < otherBot.getPlayerState().getCash()){

                return otherBot.getStrategyClass();
            }

            return null;
        }


    }

}
