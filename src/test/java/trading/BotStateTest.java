package trading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BotStateTest {

    private BotState botState;

    @BeforeEach
    void setUp() {
        botState = new BotState(100, 1000);
    }

    @Test
    void getLatestBidsTest() {
        botState.update(10, 50, 10);
        botState.update(20, 60, 15);
        botState.update(25, 70, 20);

        List<Integer> latestBids = botState.getLatestBids(2);

        assertEquals(2, latestBids.size());
        assertEquals(70, latestBids.get(0));
        assertEquals(60, latestBids.get(1));
    }

    @Test
    void testInitialQuantityAndCash() {
        assertEquals(100, botState.getStartQuantity());
        assertEquals(1000, botState.getStartCash());
    }

    @Test
    void testInitialValues() {
        assertEquals(0, botState.getQuantity());
        assertEquals(1000, botState.getCash());
        assertEquals(100, botState.getAvailableQuantity());
    }

    @Test
    void testUpdate() {
        botState.update(10, 100, 20);
        assertEquals(10, botState.getQuantity());
        assertEquals(900, botState.getCash());
        assertEquals(80, botState.getAvailableQuantity());
    }

    @Test
    void testHistory() {
        botState.update(10, 100, 20);
        assertEquals(1, botState.getHistory().size());

        BidLogEntry entry = botState.getHistory().get(0);
        assertEquals(10, entry.reward());
        assertEquals(100, entry.cash());
    }

    @Test
    void testUpdateWithNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> botState.update(-10, -100, -20));
    }
}