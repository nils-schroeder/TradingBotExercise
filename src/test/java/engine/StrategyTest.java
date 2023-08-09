package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trading.BotState;

import static org.junit.jupiter.api.Assertions.*;

class StrategyTest {

    private Strategy strategy;
    private BotState playerState;
    private BotState otherState;

    @BeforeEach
    void setUp() {
        playerState = new BotState(100, 1000);
        otherState = new BotState(100, 1000);
        strategy = new TestStrategy().init(playerState, otherState);
    }

    @Test
    void testCalculateBidSize() {
        assertEquals(20, strategy.calculateBidSize(playerState));
    }

    @Test
    void testUpdateBidSize() {
        playerState.update(0, 500, 2);
        strategy.updateBidSize(playerState);
        assertEquals(10, strategy.calculateBidSize(playerState));
    }

    private static class TestStrategy extends Strategy {
        @Override
        public int determineBid(BotState playerState, BotState otherState) {
            return bidSize;
        }
    }
}