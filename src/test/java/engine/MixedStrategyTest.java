package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trading.BotState;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MixedStrategyTest {

    private MixedStrategy strategy;
    private BotState playerState;
    private BotState otherState;

    @BeforeEach
    void setUp() {
        playerState = new BotState(100, 1000);
        otherState = new BotState(100, 1000);
        strategy = (MixedStrategy) StrategyFactory.createStrategy(MixedStrategy.class).init(playerState, otherState);
    }

    @Test
    void testInitMethod() {
        assertNotNull(strategy, "Strategy should not be null after initialization");
    }

    @Test
    void testNoBidAmount() {
        int expectedNoBidAmount = (int) Math.round(strategy.getInitialBidSize() * MixedStrategy.NO_BID_FACTOR);
        assertEquals(expectedNoBidAmount, strategy.noBidAmount(), "NoBidAmount calculation is incorrect");
    }

    @Test
    void testRollingAverage() {
        // Sample data for testing purposes
        List<Integer> numbers = List.of(100, 200, 300, 400, 500);
        double expectedAverage = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
        assertEquals(expectedAverage, strategy.rollingAverage(numbers), "Rolling average calculation is incorrect");
    }

    @Test
    void testDetermineBidLogic() {
        int bid = strategy.determineBid(playerState, otherState);
        assertTrue(bid >= 0, "Bid should be non-negative");

        // Assuming, for instance, 25 is your window size for testing the strategy after window:
        for (int i = 0; i < 25; i++) {
            playerState.update(0, 10, 2); // Adjust parameters as required for your logic.
        }

        int afterWindowBid = strategy.determineBid(playerState, otherState);
        assertTrue(afterWindowBid >= 0, "Bid should be non-negative even after window");
    }
}