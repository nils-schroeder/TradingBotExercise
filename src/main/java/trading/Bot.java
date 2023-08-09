package trading;

import auction.Bidder;
import engine.MixedStrategy;
import engine.Strategy;
import engine.StrategyFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a trading bot that can place bids based on various strategies.
 */
public class Bot implements Bidder {

    protected BotState playerState;
    protected BotState otherState;
    protected Strategy strategy;
    private static final Logger logger = LogManager.getLogger();

    private final Class<? extends Strategy> strategyClass;

    /**
     * Default constructor. Initializes bot with a mixed strategy.
     */
    public Bot() {
        this.strategyClass = MixedStrategy.class;
    }

    /**
     * Constructor that allows specifying a custom strategy class for the bot.
     *
     * @param strategyClass The strategy class the bot should use.
     */
    public Bot(Class<? extends Strategy> strategyClass) {
        this.strategyClass = strategyClass;
    }

    /**
     * Returns the state of the player/bot.
     *
     * @return Current state of the bot.
     */
    public BotState getPlayerState() {
        return playerState;
    }

    /**
     * Returns the state of the other player.
     *
     * @return Current state of the other player.
     */
    public BotState getOtherState() {
        return otherState;
    }

    /**
     * Returns the class of the strategy the bot is using.
     *
     * @return The class object of the strategy.
     */
    public Class<? extends Strategy> getStrategyClass() {
        return strategyClass;
    }

    /**
     * Returns the name of the strategy the bot is using.
     *
     * @return The simple name of the strategy class.
     */
    public String getStrategyName() {
        return strategyClass.getSimpleName();
    }

    /**
     * Initializes the bot with the given quantity and cash.
     * Also initializes the strategy to be used.
     *
     * @param quantity Quantity available for bidding.
     * @param cash     Initial cash for the bot.
     * @throws IllegalArgumentException If the provided quantity or cash is negative.
     */
    @Override
    public void init(int quantity, int cash) {
        if (quantity < BotStateResolver.WIN_REWARD || cash < 0) {
            throw new IllegalArgumentException("Quantity and cash must be positive");
        }

        playerState = new BotState(quantity, cash);
        otherState = new BotState(quantity, cash);

        strategy = StrategyFactory.createStrategy(strategyClass).init(playerState, otherState);
    }

    /**
     * Determines and places a bid based on the bot's strategy.
     * Ensures the bid is valid before returning.
     *
     * @return The bid amount.
     */
    @Override
    public int placeBid() {
        int bid = strategy.determineBid(playerState, otherState);

        if (bid > playerState.getCash() || bid < 0) {
            logger.error("Bid is not valid");
            return 0;
        } else {
            return bid;
        }
    }

    /**
     * Processes the bids of both players and updates their states accordingly.
     *
     * @param own   The bid of the current bot.
     * @param other The bid of the other player.
     * @throws IllegalArgumentException If the provided bids are negative.
     */
    @Override
    public void bids(int own, int other) {
        if (own < 0 || other < 0) {
            throw new IllegalArgumentException("Bids cannot be negative");
        }

        BotStateResolver.resolveBids(own, other, playerState, otherState);
    }
}
