package engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trading.BotState;

import static org.junit.jupiter.api.Assertions.*;

class InverseGreedyStrategyTest {

    private InverseGreedyStrategy strategy;
    private BotState playerState;
    private BotState otherState;

    @BeforeEach
    void setUp() {
        playerState = new BotState(100, 1000);
        otherState = new BotState(100, 1000);
        strategy = (InverseGreedyStrategy) StrategyFactory.createStrategy(InverseGreedyStrategy.class).init(playerState, otherState);
    }

    @Test
    void testDetermineBidBeforeWindow() {
        assertEquals(0, strategy.determineBid(playerState, otherState));
    }

    @Test
    void testCalculateBidSize() {
        assertEquals(38, strategy.calculateBidSize(playerState));
    }

    @Test
    void testDetermineBidAfterWindow() {

        for (int i = 0; i < 25; i++) {
            playerState.update(0, 10, 2);
        }

        assertEquals(38, strategy.determineBid(playerState, otherState));
    }
}
