package cyclechronicles;

/** An order for a bike shop. */
public record Order(String customer, Type bicycleType) {

    // Muss so nicht viel ändern (faul)
    public Type getBicycleType() {
        throw new UnsupportedOperationException();
    }
    public String getCustomer() {
        throw new UnsupportedOperationException();
    }
}
