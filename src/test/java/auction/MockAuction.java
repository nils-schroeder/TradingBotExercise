package auction;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import trading.Bot;

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

    public MockAuction(int quantityTotal, int cashPerBot, String playerStrategyName, String otherStrategyName){
        this.quantityTotal = quantityTotal;
        this.cashPerBot = cashPerBot;

        this.playerBot = new Bot(playerStrategyName);
        this.otherBot = new Bot(otherStrategyName);
    }

    public void run(){

        logger.info("test");

    }

}
