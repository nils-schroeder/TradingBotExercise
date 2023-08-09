# OptimaxTradingBot

## Description

This is a trading bot that uses the bidder API to trade on virtual exchange.

The repository is a maven project and can be imported. 

## Getting Started


The Bot class in the trading package implements the bidder interface.
It will use a default strategy if no strategy is provided.

````java
import trading.Bot;

Bot bot = new Bot();
````

You can also provide a strategy to the bot.


````java
Bot bot = new Bot(MixedStrategy.class);
````

## Strategies

### ConstantStrategy

This strategy will always bid the same amount of cash equal to the `starting cash \ number of rounds`.

### FlipStrategy

This strategy will randomly with a chance of 30% not bid. The bid amount is increased after every time a bid was omitted.

### GreedyStrategy

This strategy works the same as the Constant Strategy but instead will use `(number of rounds / 2) + 1` to determine its bid each round.

### InverseGreedyStrategy

This strategy works the same as the Greedy Strategy but start bidding only after half of the rounds have passed.

### MixedStrategy

This is the most sophisticated strategy.
The purpose of the other strategies was mainly to test this one against individual "edge" cases.

To throw off other bots this strategy will - with a chance of 20% - only bid 1% of `starting cash / number of rounds`.
It additionally ensures that it can always bid at least that amount until the final round to avoid ties.

Its bids are calculated using a rolling average of the last $\sqrt{total number of rounds}$ rounds of the other bot,  
plus an offset of $\sqrt{starting cash / number of rounds}$ plus the 120% of the standard deviation of all bids of the other bot.

The standard deviation protects against a too low rolling average because of random outliers bids.


### Author
Nils Schröder