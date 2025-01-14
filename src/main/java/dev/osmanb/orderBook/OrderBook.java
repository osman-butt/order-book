package dev.osmanb.orderBook;

import java.util.*;

/**
 * OrderBook class to store and process orders
 * Orders are stored in two treemaps, one for buy orders and one for sell orders
*/
class OrderBook {
    // Use treemap to store orders sorted by price
    // For buy orders the best price is the highest price
    // For sell orders the best price is the lowest price

    private final TreeMap<Double, Queue<Order>> buyOrders;
    private final TreeMap<Double, Queue<Order>> sellOrders;
    private final MatchingEngine matchingEngine;

    public OrderBook(MatchingEngine matchingEngine) {
        this.matchingEngine = matchingEngine;
        // TODO: Extract the buy/sell datastructures to a separate class
        // TODO: The buy/sell datastructures should depend on the choosen matching rule
        // Note: Current datastructures are FIFO based

        // Buy orders sorted in descending order of price
        buyOrders = new TreeMap<>(Comparator.reverseOrder());
        // Sell orders sorted in ascending order of price
        sellOrders = new TreeMap<>();
    }

    public void addOrder(Order order) {
        TreeMap<Double, Queue<Order>> targetBook = (order.type == Order.Type.BUY) ? buyOrders : sellOrders;

        // TODO: Check if there exists a matching order -> call matchingEngine.processMarketOrder()
        // TODO: If there are unfilled orders, add the order to the order book


        // If there isn't a queue for the price, create one
        targetBook.putIfAbsent(order.price, new LinkedList<>());
        // Add the order to the queue
        targetBook.get(order.price).add(order);
    }

    public void processMarketOrder(Order.Type type, int quantity) {
        // TODO: Handle unfilled orders
        matchingEngine.processMarketOrder(type, quantity, buyOrders, sellOrders);
    }

    private double getBidAskSpread() {
        double bestBid = buyOrders.isEmpty() ? 0 : buyOrders.firstKey();
        double bestAsk = sellOrders.isEmpty() ? 0 : sellOrders.firstKey();
        return bestAsk - bestBid;
    }

    private int getTotalBuyOrders() {
        return buyOrders.values().stream().flatMap(Queue::stream).mapToInt(o -> o.quantity).sum();
    }

    private int getTotalSellOrders() {
        return sellOrders.values().stream().flatMap(Queue::stream).mapToInt(o -> o.quantity).sum();
    }

    private String showRelativeQuantity(int quantity, int totalQuantity) {
        int numberOfBoxes = 20;
        if (totalQuantity == 0) { // Avoid division by zero
            return "";
        }
        int boxSize = totalQuantity / numberOfBoxes;
        int boxesToDisplay = quantity / boxSize;
        return "█".repeat(Math.max(1, boxesToDisplay));
    }

    public void printDepth() {
        int totalQuantity = getTotalBuyOrders()+getTotalSellOrders();

        System.out.println(AnsiTextFormatter.formatText("=================== Order Book Depth ===================",AnsiTextFormatter.TextStyle.BOLD));

//        System.out.println(AnsiTextFormatter.formatText("Ask", AnsiTextFormatter.TextStyle.BOLD));
        sellOrders.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))  // Sort by descending price
                .forEach(entry -> {
                    int quantityAtLevel = entry.getValue().stream().mapToInt(o -> o.quantity).sum();
                    printPriceLevel(entry.getKey(), quantityAtLevel, totalQuantity, Order.Type.SELL);
                });

        System.out.printf(AnsiTextFormatter.formatText("================= Bid-Ask spread: %4.2f =================\n", AnsiTextFormatter.TextStyle.BLUE, AnsiTextFormatter.TextStyle.BOLD), getBidAskSpread());

//        System.out.println(AnsiTextFormatter.formatText("Bid", AnsiTextFormatter.TextStyle.BOLD));
        for (Map.Entry<Double, Queue<Order>> entry : buyOrders.entrySet()) {
            int quantityAtLevel = entry.getValue().stream().mapToInt(o -> o.quantity).sum();
            printPriceLevel(entry.getKey(), quantityAtLevel, totalQuantity, Order.Type.BUY);
        }
    }

    private void printPriceLevel(double price, int quantity, int totalQuantity, Order.Type type) {
        var colorStyle = (type == Order.Type.BUY) ? AnsiTextFormatter.TextStyle.GREEN : AnsiTextFormatter.TextStyle.RED;
        System.out.printf(AnsiTextFormatter.formatText("kr %7.2f     %8d      %s%n", colorStyle), price, quantity, showRelativeQuantity(quantity,totalQuantity));
    }
}