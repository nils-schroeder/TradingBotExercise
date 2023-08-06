package engine;

public class StrategyFactory {

    public static Strategy createStrategy(StrategyName name) {

        switch (name) {
            case FLIP:
                return new FlipStrategy();
            case CONSTANT:
                return new ConstantStrategy();
            case DEFAULT:
                return new FlipStrategy();
            default:
                return new FlipStrategy();
        }
    }

}
