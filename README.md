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


### Author
Nils Schröder