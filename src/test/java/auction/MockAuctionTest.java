package auction;


import engine.ConstantStrategy;
import engine.FlipStrategy;
import engine.Strategy;
import org.junit.jupiter.api.Test;
import trading.BotState;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockAuctionTest {

    public static final List<MockAuctionSetting> mockAuctionSetups = List.of(
            new MockAuctionSetting(100, 10000, FlipStrategy.class, FlipStrategy.class)
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

        MockAuction auction = new MockAuction(
                new MockAuctionSetting(100, 10000, MockRougeStrategy.class, ConstantStrategy.class)
        );

        auction.run();

        assertEquals(10000, auction.getPlayerBot().getPlayerState().getCash());
        assertEquals(0, auction.getOtherBot().getPlayerState().getCash());

    }

}
