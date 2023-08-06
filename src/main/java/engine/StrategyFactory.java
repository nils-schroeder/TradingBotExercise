package engine;

public class StrategyFactory {

    private static final StrategyFactory instance = new StrategyFactory();

    public static StrategyFactory getInstance() {
        return instance;
    }

    public Strategy createStrategy(StrategyName name){

        switch (name){
            case FLIP:
                return new FlipStrategy();
            case CONSTANT:
              //  return new ConstantStrategy();
            case ADVANCED_FLIP:
           //     return new AdvancedFlipStrategy();
            case DEFAULT:
                return new FlipStrategy();
            default:
                return new FlipStrategy();
        }
    }

}
