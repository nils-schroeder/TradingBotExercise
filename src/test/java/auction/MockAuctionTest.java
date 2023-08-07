package auction;


import engine.FlipStrategy;
import engine.MockRougeStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockAuctionTest {

    public static final List<MockAuctionSetting> mockAuctionSetups = List.of(
 /*           new MockAuctionSetting(100, 10000, StrategyName.GREEDY, StrategyName.VALUE_BASED),
            new MockAuctionSetting(100, 10000, StrategyName.GREEDY, StrategyName.FLIP),
            new MockAuctionSetting(100, 10000, StrategyName.INVERSE_GREEDY, StrategyName.FLIP),
            new MockAuctionSetting(100, 10000, StrategyName.VALUE_BASED, StrategyName.FLIP),
            new MockAuctionSetting(100, 10000, StrategyName.VALUE_BASED, StrategyName.CONSTANT)*/
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
                new MockAuctionSetting(100, 10000, MockRougeStrategy.class, FlipStrategy.class)
        );

        auction.run();

        assertEquals(10000, auction.getPlayerBot().getPlayerState().getCash());
        assertEquals(10000, auction.getOtherBot().getOtherState().getCash());

    }


}
