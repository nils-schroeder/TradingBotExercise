package trading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class BotStateTest {

    private BotState botState;

    @BeforeEach
    public void setUp() {
        botState = new BotState(100, 1000);
    }

    @Test
    public void testInitialState() {
        assertEquals(100, botState.getStartQuantity());
        assertEquals(1000, botState.getStartCash());
        assertEquals(0, botState.getQuantity());
        assertEquals(100, botState.getAvailableQuantity());
        assertEquals(1000, botState.getCash());
        assertTrue(botState.getHistory().isEmpty());
    }

    @Test
    public void testUpdate() {
        botState.update(10, 200, 20);

        assertEquals(10, botState.getQuantity());
        assertEquals(80, botState.getAvailableQuantity());
        assertEquals(800, botState.getCash());

        ArrayList<BidLogEntry> history = botState.getHistory();
        assertEquals(1, history.size());

        BidLogEntry entry = history.get(0);
        assertEquals(200, entry.cash());
        assertEquals(10, entry.reward());
    }

    @Test
    public void testMultipleUpdates() {
        botState.update(10, 200, 20);
        botState.update(5, 100, 10);

        assertEquals(15, botState.getQuantity());
        assertEquals(70, botState.getAvailableQuantity());
        assertEquals(700, botState.getCash());

        ArrayList<BidLogEntry> history = botState.getHistory();
        assertEquals(2, history.size());

        BidLogEntry entry1 = history.get(0);
        assertEquals(200, entry1.cash());
        assertEquals(10, entry1.reward());

        // Check the second entry
        BidLogEntry entry2 = history.get(1);
        assertEquals(100, entry2.cash());
        assertEquals(5, entry2.reward());
    }

    @Test
    public void testUpdateWithNegativePayout() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            botState.update(-10, 200, 20);
        });

        assertEquals("Negative values are not allowed", exception.getMessage());
        assertEquals(0, botState.getQuantity());
        assertEquals(100, botState.getAvailableQuantity());
        assertEquals(1000, botState.getCash());
        assertTrue(botState.getHistory().isEmpty());
    }

    @Test
    public void testUpdateWithNegativePaidCash() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            botState.update(10, -200, 20);
        });

        assertEquals("Negative values are not allowed", exception.getMessage());
        assertEquals(0, botState.getQuantity());
        assertEquals(100, botState.getAvailableQuantity());
        assertEquals(1000, botState.getCash());
        assertTrue(botState.getHistory().isEmpty());
    }

    @Test
    public void testUpdateWithNegativeTotalPayout() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            botState.update(10, 200, -20);
        });

        assertEquals("Negative values are not allowed", exception.getMessage());
        assertEquals(0, botState.getQuantity());
        assertEquals(100, botState.getAvailableQuantity());
        assertEquals(1000, botState.getCash());
        assertTrue(botState.getHistory().isEmpty());
    }

    @Test
    public void testUpdateWithNegativeValuesInHistory() {
        botState.update(10, 200, 20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            botState.update(5, -100, 10);
        });

        assertEquals("Negative values are not allowed", exception.getMessage());
        assertEquals(10, botState.getQuantity());
        assertEquals(80, botState.getAvailableQuantity());
        assertEquals(800, botState.getCash());

        ArrayList<BidLogEntry> history = botState.getHistory();
        assertEquals(1, history.size());

        BidLogEntry entry1 = history.get(0);
        assertEquals(200, entry1.cash());
        assertEquals(10, entry1.reward());
    }
}