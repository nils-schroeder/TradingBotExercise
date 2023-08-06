package auction;


import engine.StrategyName;
import org.junit.Test;
import trading.Bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockAuctionTest {

    public static final List<MockAuctionSetting> mockAuctionSetups = List.of(
        new MockAuctionSetting(100, 10000, StrategyName.CONSTANT, StrategyName.DEFAULT)
      //  new MockAuctionSetting(1000, 1000, "any", "any")
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
}
