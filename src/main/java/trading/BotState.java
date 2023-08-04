package trading;

import java.util.ArrayList;

public class BotState {

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

    public void update(int payout, int paidCash, int totalPayout){

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

}
