package trading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BotStateResolverTest {

    private BotState botState1;
    private BotState botState2;

    @BeforeEach
    void setUp() {
        botState1 = new BotState(100, 1000);
        botState2 = new BotState(100, 1000);
    }

    @Test
    void testResolveBidsTie() {
        BotStateResolver.resolveBids(500, 500, botState1, botState2);
        assertEquals(1, botState1.getQuantity());
        assertEquals(500, botState1.getCash());
        assertEquals(1, botState2.getQuantity());
        assertEquals(500, botState2.getCash());
        assertEquals(botState1.getAvailableQuantity(), botState2.getAvailableQuantity());
        assertEquals(98, botState1.getAvailableQuantity());
    }

    @Test
    void testResolveBidsWin() {
        BotStateResolver.resolveBids(600, 500, botState1, botState2);
        assertEquals(2, botState1.getQuantity());
        assertEquals(400, botState1.getCash());
        assertEquals(0, botState2.getQuantity());
        assertEquals(500, botState2.getCash());
        assertEquals(botState1.getAvailableQuantity(), botState2.getAvailableQuantity());
        assertEquals(98, botState1.getAvailableQuantity());
    }

    @Test
    void testResolveBidsLoss() {
        BotStateResolver.resolveBids(400, 500, botState1, botState2);
        assertEquals(0, botState1.getQuantity());
        assertEquals(600, botState1.getCash());
        assertEquals(2, botState2.getQuantity());
        assertEquals(500, botState2.getCash());
        assertEquals(botState1.getAvailableQuantity(), botState2.getAvailableQuantity());
        assertEquals(98, botState1.getAvailableQuantity());
    }

    @Test
    void testResolveBidsNegativeCash() {
        assertThrows(IllegalArgumentException.class, () -> BotStateResolver.resolveBids(-100, 500, botState1, botState2));
        assertThrows(IllegalArgumentException.class, () -> BotStateResolver.resolveBids(500, -100, botState1, botState2));
    }
}
