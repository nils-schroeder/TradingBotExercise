package auction;

import engine.StrategyName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import trading.Bot;

import java.util.stream.IntStream;

public class MockAuction {

    private final int quantityTotal;
    private final int cashPerBot;
    private Bot playerBot;
    private Bot otherBot;

    private static final Logger logger = LogManager.getLogger();

    public MockAuction(int quantityTotal, int cashPerBot){
        this.quantityTotal = quantityTotal;
        this.cashPerBot = cashPerBot;

        this.playerBot = new Bot();
        this.otherBot = new Bot();
    }

    public MockAuction(MockAuctionSetting settings){
        this.quantityTotal = settings.quantityTotal();
        this.cashPerBot = settings.cashPerBot();

        this.playerBot = new Bot(settings.playerStrategyName());
        this.otherBot = new Bot(settings.otherStrategyName());
    }

    public MockAuction(int quantityTotal, int cashPerBot, StrategyName playerStrategyName, StrategyName otherStrategyName){
        this.quantityTotal = quantityTotal;
        this.cashPerBot = cashPerBot;

        this.playerBot = new Bot(playerStrategyName);
        this.otherBot = new Bot(otherStrategyName);
    }

    public void run(){

        logger.info("Running MockAuction with quantityTotal: [{}], cashPerBot: [{}]", quantityTotal, cashPerBot);

        playerBot.init(quantityTotal, cashPerBot);
        otherBot.init(quantityTotal, cashPerBot);

        IntStream.range(0, (int) quantityTotal / 2).forEach(
            i -> {
                int playerBid = playerBot.placeBid();
                int otherBid = otherBot.placeBid();

                logger.info("[{}] {} bid: [{}], {} bid: [{}]",
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

        logger.info("RESULT: {}: [Quantity: {} | Cash: {}], {}: [Quantity: {} | Cash: {}]",
                playerBot.getStrategyName(),
                playerBot.getPlayerState().getQuantity(),
                playerBot.getPlayerState().getCash(),
                otherBot.getStrategyName(),
                otherBot.getPlayerState().getQuantity(),
                otherBot.getPlayerState().getCash()
        );



    }

}
