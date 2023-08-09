package trading;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    private Bot bot;

    @BeforeEach
    void setUp() {
        bot = new Bot();
    }

    @Test
    void testInit() {
        bot.init(100, 1000);
        assertEquals(100, bot.getPlayerState().getStartQuantity());
        assertEquals(1000, bot.getPlayerState().getStartCash());
        assertEquals(100, bot.getOtherState().getStartQuantity());
        assertEquals(1000, bot.getOtherState().getStartCash());
    }

    @Test
    void testPlaceBid() {
        bot.init(100, 1000);
        assertTrue(bot.placeBid() >= 0);
        assertTrue(bot.placeBid() <= bot.getPlayerState().getCash());
    }

    @Test
    void testBids() {
        bot.init(100, 1000);
        bot.bids(500, 500);
        assertEquals(1, bot.getPlayerState().getQuantity());
        assertEquals(500, bot.getPlayerState().getCash());
        assertEquals(1, bot.getOtherState().getQuantity());
        assertEquals(500, bot.getOtherState().getCash());
        assertEquals(98, bot.getPlayerState().getAvailableQuantity());
        assertEquals(98, bot.getOtherState().getAvailableQuantity());
    }

    @Test
    void testBidsNegative() {
        bot.init(100, 1000);
        assertThrows(IllegalArgumentException.class, () -> bot.bids(-100, 500));
        assertThrows(IllegalArgumentException.class, () -> bot.bids(500, -100));
    }

    @Test
    void testInitNegativeValues() {
        assertThrows(IllegalArgumentException.class, () -> bot.init(-100, 1000));
        assertThrows(IllegalArgumentException.class, () -> bot.init(100, -1000));
    }
}