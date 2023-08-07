package auction;

import engine.Strategy;
import engine.StrategyName;

public record MockAuctionSetting(int quantityTotal,
                                 int cashPerBot,
                                 Class<? extends Strategy> playerStrategyClass,
                                 Class<? extends Strategy> otherStrategyClass) {}
