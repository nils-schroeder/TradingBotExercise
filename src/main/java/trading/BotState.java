package trading;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

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

    public ArrayList<BidLogEntry> getHistory() {
        return history;
    }

    private final ArrayList<BidLogEntry> history;

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
