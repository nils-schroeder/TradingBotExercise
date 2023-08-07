package auction;


import engine.MockRougeBot;
import engine.StrategyName;
import org.junit.jupiter.api.Test;
import trading.Bot;
import trading.BotState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockAuctionTest {

    public static final List<MockAuctionSetting> mockAuctionSetups = List.of(
            new MockAuctionSetting(100, 10000, StrategyName.GREEDY, StrategyName.VALUE_BASED),
            new MockAuctionSetting(100, 10000, StrategyName.GREEDY, StrategyName.FLIP),
            new MockAuctionSetting(100, 10000, StrategyName.INVERSE_GREEDY, StrategyName.FLIP),
            new MockAuctionSetting(100, 10000, StrategyName.VALUE_BASED, StrategyName.FLIP),
            new MockAuctionSetting(100, 10000, StrategyName.VALUE_BASED, StrategyName.CONSTANT)
    );

    @Test
    public void MockAuctionIntegrationTest(){

        mockAuctionSetups.forEach(
            mockAuctionSetting -> {
                MockAuction auction = new MockAuction(mockAuctionSetting);
                auction.run();
            }
        );
    }

    @Test
    public void MockAuctionRougeStrategyTest(){

        Bot rougeBot = new MockRougeBot();
        Bot otherBot = new Bot();

        MockAuction auction = new MockAuction(100, 10000, rougeBot, otherBot);

        auction.run();

        assertEquals(10000, rougeBot.getPlayerState().getCash());
        assertEquals(10000, otherBot.getOtherState().getCash());

    }


}
