package dev.osmanb.orderBook;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MatchingEngine matchingEngine = new MatchingEngineFIFO();
        OrderBook orderBook = new OrderBook(matchingEngine);
        createExampleOrders(orderBook);

        while (true) {
            System.out.println("\nOptions: \n1. Add Limit Order \n2. Execute Market Order \n3. Show Order Book Depth \n4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter order type (BUY/SELL): ");
                    Order.Type type = Order.Type.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    orderBook.addOrder(new Order(type, price, quantity));
                    break;
                case 2:
                    System.out.print("Enter market order type (BUY/SELL): ");
                    Order.Type marketType = Order.Type.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter quantity: ");
                    int marketQuantity = scanner.nextInt();
                    orderBook.processMarketOrder(marketType, marketQuantity);
                    break;
                case 3:
                    orderBook.printDepth();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void createExampleOrders(OrderBook orderBook) {

        // Use a random number generator to create example orders
        Random random = new Random();

        // Generate buy orders between 96 and 100
        for (int i = 0; i < 5; i++) {
            double price = 96 + random.nextDouble() * 4;
            int quantity = 1 + random.nextInt(250);
            orderBook.addOrder(new Order(Order.Type.BUY, price, quantity));
        }

        // Generate sell orders between 100 and 105
        for (int i = 0; i < 5; i++) {
            double price = 100 + random.nextDouble() * 4;
            int quantity = 1 + random.nextInt(250);
            orderBook.addOrder(new Order(Order.Type.SELL, price, quantity));
        }
    }
}
