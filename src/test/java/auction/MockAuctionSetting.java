package auction;

import engine.Strategy;

public record MockAuctionSetting(int quantityTotal,
                                 int cashPerBot,
                                 Class<? extends Strategy> playerStrategyClass,
                                 Class<? extends Strategy> otherStrategyClass) {}
