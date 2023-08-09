package trading;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents the state of a trading bot, including its starting resources,
 * current resources, and a history of bids.
 */
public class BotState {

    private final int startQuantity;
    private final int startCash;

    private int quantity;
    private int cash;
    private int availableQuantity;

    private final List<BidLogEntry> history;

    /**
     * Constructor initializing the bot state with starting quantity and cash.
     *
     * @param startQuantity Initial quantity available for bidding.
     * @param startCash     Initial cash for the bot.
     */
    public BotState(int startQuantity, int startCash) {
        this.startQuantity = startQuantity;
        this.startCash = startCash;

        this.quantity = 0;
        this.availableQuantity = startQuantity;
        this.cash = startCash;

        this.history = new ArrayList<>();
    }

    /**
     * Gets the starting quantity available for bidding when the bot was initialized.
     *
     * @return Initial quantity for the bot.
     */
    public int getStartQuantity() {
        return startQuantity;
    }

    /**
     * Gets the starting cash available for the bot when it was initialized.
     *
     * @return Initial cash for the bot.
     */
    public int getStartCash() {
        return startCash;
    }

    /**
     * Gets the current quantity held by the bot.
     *
     * @return Current quantity of the bot.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the current cash held by the bot.
     *
     * @return Current cash of the bot.
     */
    public int getCash() {
        return cash;
    }

    /**
     * Gets the quantity left available for the bot to bid on.
     *
     * @return Available quantity for bidding.
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }


    /**
     * Returns the bid history of the bot.
     *
     * @return A list of bid log entries.
     */
    public List<BidLogEntry> getHistory() {
        return history;
    }

    /**
     * Retrieves all latest bids from the bot's history.
     *
     * @return A list of bid amounts.
     */
    public List<Integer> getLatestBids() {
        return getLatestBids(history.size());
    }

    /**
     * Retrieves the latest 'n' bids from the bot's history.
     *
     * @param n Number of latest bids to retrieve.
     * @return A list of bid amounts (the latest bid will be at index 0).
     */
    public List<Integer> getLatestBids(int n) {
        return IntStream.rangeClosed(
                1, Math.min(history.size(), n)
        ).mapToObj(
                i -> history.get(history.size() - i).cash()
        ).toList();
    }

    /**
     * Updates the bot's state after a trading round.
     *
     * @param payout     Quantity gained by the bot.
     * @param paidCash   Cash paid by the bot.
     * @param totalPayout Total payout in the round.
     * @throws IllegalArgumentException If any provided value is negative.
     */
    public void update(int payout, int paidCash, int totalPayout) throws IllegalArgumentException {
        if (payout < 0 || paidCash < 0 || totalPayout < 0) {
            throw new IllegalArgumentException("Negative values are not allowed");
        }

        this.quantity += payout;
        this.availableQuantity -= totalPayout;
        this.cash -= paidCash;

        this.history.add(
                new BidLogEntry(paidCash, payout)
        );
    }

    @Override
    public String toString() {
        return "BotState{" +
                "startQuantity=" + startQuantity +
                ", startCash=" + startCash +
                ", quantity=" + quantity +
                ", cash=" + cash +
                ", availableQuantity=" + availableQuantity +
                ", history=" + history +
                '}';
    }

}
