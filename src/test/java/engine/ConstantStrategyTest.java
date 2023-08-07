package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trading.BotState;

import static org.junit.jupiter.api.Assertions.*;

class ConstantStrategyTest {

    private ConstantStrategy strategy;
    private BotState playerState;
    private BotState otherState;

    @BeforeEach
    void setUp() {
        playerState = new BotState(100, 1000);
        otherState = new BotState(100, 1000);
        strategy = (ConstantStrategy) StrategyFactory.createStrategy(ConstantStrategy.class).init(playerState, otherState);
    }

    @Test
    void testDetermineBid() {
        assertEquals(20, strategy.determineBid(playerState, otherState));
    }

    @Test
    void testUpdateBidSizeAfterUpdate() {
        playerState.update(0, 500, 2);
        strategy.updateBidSize(playerState);
        assertEquals(10, strategy.determineBid(playerState, otherState));
    }
}
