package engine;

import java.lang.reflect.InvocationTargetException;
import engine.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrategyFactory {

    private static final Logger logger = LogManager.getLogger();

    public static Strategy createStrategy(Class<? extends Strategy> strategyClass) {

        try {

            return strategyClass.getDeclaredConstructor().newInstance();

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {

            logger.error(e.getMessage());

            return new MixedStrategy();

        }

    }

}
