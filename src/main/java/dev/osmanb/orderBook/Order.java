package dev.osmanb.orderBook;

class Order {
    enum Type { BUY, SELL }

    Type type;
    double price;
    int quantity;
    long timestamp;

    public Order(Type type, double price, int quantity) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.timestamp = System.nanoTime();
    }

    @Override
    public String toString() {
        return String.format("%s: %d @ %.2f", type, quantity, price);
    }
}
