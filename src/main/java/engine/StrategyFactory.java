package engine;

public class StrategyFactory {

    private static final StrategyFactory instance = new StrategyFactory();

    public static StrategyFactory getInstance() {
        return instance;
    }

    public Strategy createStrategy(String name){

        return new FlipStrategy();

    }

}
