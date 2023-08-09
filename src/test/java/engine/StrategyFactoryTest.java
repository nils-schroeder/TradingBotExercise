package engine;

import org.junit.jupiter.api.Test;
import trading.Bot;

import static org.junit.jupiter.api.Assertions.*;

class StrategyFactoryTest {

    @Test
    void testCreateStrategy() {
        assertTrue(StrategyFactory.createStrategy(ConstantStrategy.class) instanceof ConstantStrategy);
    }

    @Test
    void testCreateStrategyWithErrorStrategy() {
        assertTrue(StrategyFactory.createStrategy(Strategy.class) instanceof MixedStrategy);
    }
}
