package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trading.BotState;

import static org.junit.jupiter.api.Assertions.*;

class FlipStrategyTest {

    private FlipStrategy strategy;
    private BotState playerState;
    private BotState otherState;

    @BeforeEach
    void setUp() {
        playerState = new BotState(100, 1000);
        otherState = new BotState(100, 1000);
        strategy = (FlipStrategy) StrategyFactory.createStrategy(FlipStrategy.class).init(playerState, otherState);
    }

    @Test
    void testDetermineBid() {
        int bid = strategy.determineBid(playerState, otherState);
        assertTrue(bid == 0 || bid == strategy.calculateBidSize(playerState));
    }

    @Test
    void testUpdateBidSizeAfterUpdate() {
        playerState.update(0, 500, 2);
        strategy.updateBidSize(playerState);
        int bid = strategy.determineBid(playerState, otherState);
        assertTrue(bid == 0 || bid == 10);
    }
}
