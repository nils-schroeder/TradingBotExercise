package trading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BotState {

    // private static final Logger logger = LogManager.getLogger();

    public int getStartQuantity() {
        return startQuantity;
    }

    public int getStartCash() {
        return startCash;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCash() {
        return cash;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    private final int startQuantity;
    private final int startCash;

    private int quantity;
    private int cash;

    private int availableQuantity;

    public List<BidLogEntry> getHistory() {
        return history;
    }

    public List<Integer> getLatestBids() {

        return getLatestBids(history.size());

    }

    public List<Integer> getLatestBids(int n) {

        return IntStream.rangeClosed(
                1, Math.min(history.size(), n)
        ).mapToObj(
                i -> history.get(history.size() - i).cash()
        ).toList();

    }

    private final List<BidLogEntry> history;

    public BotState(int startQuantity, int startCash) {

        this.startQuantity = startQuantity;
        this.startCash = startCash;

        this.quantity = 0;
        this.availableQuantity = startQuantity;
        this.cash = startCash;

        this.history = new ArrayList<>();

    }

    public void update(int payout, int paidCash, int totalPayout) throws IllegalArgumentException{

        if(payout < 0 || paidCash < 0 || totalPayout < 0){
            throw new IllegalArgumentException("Negative values are not allowed");
        }

        this.quantity += payout;
        this.availableQuantity -= totalPayout;
        this.cash -= paidCash;

        this.history.add(
            new BidLogEntry(
                paidCash,
                payout
            )
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
