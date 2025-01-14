package dev.osmanb.orderBook;

import java.util.*;

public class MatchingEngineFIFO implements MatchingEngine {
    @Override
    public void processMarketOrder(Order.Type type, int quantity, TreeMap<Double, Queue<Order>> buyOrders, TreeMap<Double, Queue<Order>> sellOrders) {
        TreeMap<Double, Queue<Order>> opposingBook = (type == Order.Type.BUY) ? sellOrders : buyOrders;
        int remainingQuantity = quantity;
        double totalCost = 0;
        int filledQuantity = 0;

        Iterator<Map.Entry<Double, Queue<Order>>> it = opposingBook.entrySet().iterator();

        // Iterate through the price levels as long as there are remaining quantity and available
        // orders at the price level
        while (it.hasNext() && remainingQuantity > 0) {
            Map.Entry<Double, Queue<Order>> entry = it.next();
            Queue<Order> ordersAtPrice = entry.getValue();

            // Process orders at the price level until the quantity is filled or there are no more orders
            // at the specific price level
            while (!ordersAtPrice.isEmpty() && remainingQuantity > 0) {
                Order topOrder = ordersAtPrice.peek();

                if (topOrder.quantity <= remainingQuantity) {
                    remainingQuantity -= topOrder.quantity;
                    filledQuantity += topOrder.quantity;
                    totalCost += topOrder.quantity * topOrder.price;
                    ordersAtPrice.poll(); // Remove the fully filled order
                } else {
                    topOrder.quantity -= remainingQuantity;
                    filledQuantity += remainingQuantity;
                    totalCost += remainingQuantity * topOrder.price;
                    remainingQuantity = 0;
                }
            }

            // Remove the price level if all orders are filled
            if (ordersAtPrice.isEmpty()) {
                it.remove();
            }
        }


        if (filledQuantity > 0) {
            double avgPrice = totalCost / filledQuantity;
            System.out.printf(AnsiTextFormatter.formatText("Filled %d shares at an average price of %.2f%n", AnsiTextFormatter.TextStyle.YELLOW, AnsiTextFormatter.TextStyle.BOLD), filledQuantity, avgPrice);
        } else {
            System.out.println(AnsiTextFormatter.formatText("No matching orders available.", AnsiTextFormatter.TextStyle.YELLOW, AnsiTextFormatter.TextStyle.BOLD));
        }

        if (remainingQuantity > 0) {
            System.out.printf(AnsiTextFormatter.formatText("Unfilled quantity: %d%n",AnsiTextFormatter.TextStyle.YELLOW, AnsiTextFormatter.TextStyle.BOLD), remainingQuantity);
        }
    }
}
