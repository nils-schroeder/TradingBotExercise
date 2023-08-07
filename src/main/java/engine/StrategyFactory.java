package engine;

public class StrategyFactory {

    public static Strategy createStrategy(StrategyName name) {

        switch (name) {
            case FLIP:
                return new FlipStrategy();
            case CONSTANT:
                return new ConstantStrategy();
            case VALUE_BASED:
                return new ValueBasedStrategy();
            case GREEDY:
                return new GreedyStrategy();
            case INVERSE_GREEDY:
                return new InverseGreedyStrategy();
            default:
                return new FlipStrategy();
        }
    }

}
