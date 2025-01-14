package dev.osmanb.orderBook;

import java.util.Queue;
import java.util.TreeMap;

interface MatchingEngine {
    void processMarketOrder(Order.Type type, int quantity, TreeMap<Double, Queue<Order>> buyOrders, TreeMap<Double, Queue<Order>> sellOrders);
}
