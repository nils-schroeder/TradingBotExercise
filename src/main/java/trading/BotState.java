package trading;

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

    public BotState(int startQuantity, int startCash) {

        this.startQuantity = startQuantity;
        this.startCash = startCash;

        this.quantity = 0;
        this.availableQuantity = startQuantity;
        this.cash = startCash;

    }

    public void update(int receivedQuantity, int paidCash){

        this.quantity += receivedQuantity;
        this.availableQuantity -= 2;
        this.cash -= paidCash;

    }

}
