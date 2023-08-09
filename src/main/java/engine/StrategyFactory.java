package engine;

import java.lang.reflect.InvocationTargetException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factory class responsible for creating instances of different bidding strategies.
 * This factory allows the creation of a strategy object given a class type. If there's an error during the
 * instantiation, a default strategy (`MixedStrategy`) is returned.
 */
public class StrategyFactory {

    /** Logger instance for logging events and errors. */
    private static final Logger logger = LogManager.getLogger();

    /**
     * Creates a strategy object based on the provided strategy class type.
     *
     * @param strategyClass The class type of the strategy to be instantiated.
     * @return An instance of the specified strategy, or a default strategy (`MixedStrategy`) if there's an error.
     */
    public static Strategy createStrategy(Class<? extends Strategy> strategyClass) {

        try {
            return strategyClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getMessage());
            // Return a default strategy in case of instantiation errors.
            return new MixedStrategy();
        }
    }
}
