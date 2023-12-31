package auction;


import engine.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MockAuctionTest {

    public static final List<MockAuctionSetting> mockAuctionSetups = List.of(
            new MockAuctionSetting(1000, 100000, MixedStrategy.class, ConstantStrategy.class),
            new MockAuctionSetting(1000, 100000, MixedStrategy.class, FlipStrategy.class),
            new MockAuctionSetting(1000, 100000, MixedStrategy.class, GreedyStrategy.class),
            new MockAuctionSetting(1000, 100000, MixedStrategy.class, InverseGreedyStrategy.class),
            new MockAuctionSetting(1000, 100000, MixedStrategy.class, MixedStrategy.class),
            new MockAuctionSetting(100, 1000, MixedStrategy.class, ConstantStrategy.class),
            new MockAuctionSetting(100, 1000, MixedStrategy.class, FlipStrategy.class),
            new MockAuctionSetting(100, 1000, MixedStrategy.class, GreedyStrategy.class),
            new MockAuctionSetting(100, 1000, MixedStrategy.class, InverseGreedyStrategy.class)
    );


    @Test
    public void MockAuctionIntegrationTest() {
        assertAll("All Mock Auctions with MixedStrategy should win",
                mockAuctionSetups.stream()
                        .map(mockAuctionSetting -> {
                            return () -> {
                                MockAuction auction = new MockAuction(mockAuctionSetting);
                                Class<?> winningStrategy = auction.run();
                                assertEquals(MixedStrategy.class, winningStrategy,
                                        "Expected winning strategy to be MixedStrategy but was " + winningStrategy.getName());
                            };
                        })
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
