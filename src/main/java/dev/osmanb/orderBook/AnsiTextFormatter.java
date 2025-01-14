package dev.osmanb.orderBook;

public class AnsiTextFormatter {
    public enum TextStyle {
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        BOLD("\u001B[1m"),
        RESET("\u001B[0m");

        private final String code;

        TextStyle(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static String formatText(String text, TextStyle... styles) {
        StringBuilder formattedText = new StringBuilder();

        // Apply all styles
        for (TextStyle style : styles) {
            formattedText.append(style.getCode());
        }

        formattedText.append(text);
        // Reset styles
        formattedText.append(TextStyle.RESET.getCode());

        return formattedText.toString();
    }
}
