package auction;

import engine.StrategyName;

public record MockAuctionSetting(int quantityTotal,
                                 int cashPerBot,
                                 StrategyName playerStrategyName,
                                 StrategyName otherStrategyName) {}
