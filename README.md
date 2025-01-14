# Order book

This is a simple order book application that simulates a basic market where you can place limit orders, execute market orders, and view the current state of the order book. The order book is implemented with a FIFO matching engine.

![Order book UI](https://github.com/osman-butt/order-book/blob/main/docs/main_ui.PNG)

## Features
- **Limit Orders**: Users can place limit orders to either buy or sell at a specified price and quantity.
- **Market Orders**: Users can execute market orders, which will attempt to match with the best available orders in the order book.
- **Order Book Depth**: Users can view the current buy and sell orders in the order book, along with a proportional visual representation of the market depth.
- **FIFO Matching Engine**: The matching engine processes orders using the FIFO (First In First Out) rule, where the earliest placed orders are matched first.

## Requirements
- Java 11 or later
- Maven 3.6 or later

## Running the application
1. Clone or download the repository to your local machine.

2. Build the project
   ```bash
   mvn package
   ````

3. Run the application
   ```bash
   mvn exec:java
   ```
## Future Planned Improvements
#### 1. Trade history
- Implement support for trade history of executed trades. This will include storing and displaying details such as the `price`, `quantity`, and `timestamp` for each placed order.
#### 2. Multiple Matching Rules Support
- Implement support for multiple matching rules (e.g., Pro-rata, Price-Time Priority etc).
