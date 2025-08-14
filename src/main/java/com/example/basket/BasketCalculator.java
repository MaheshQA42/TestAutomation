
package com.example.basket;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * Prices :
 *  - Apple: 35
 *  - Banana: 20
 *  - Melon: 50 (buy one get one free)
 *  - Lime: 15 (3 for 2)
 */

public class BasketCalculator {

    public enum Item {
        APPLE("Apple", 35),
        BANANA("Banana", 20),
        MELON("Melon", 50),
        LIME("Lime", 15);

        private final String displayName;
        private final int pricePence;

        Item(String displayName, int pricePence) {
            this.displayName = displayName;
            this.pricePence = pricePence;
        }

        public int price() {
            return pricePence;
        }

        public String displayName() {
            return displayName;
        }

        public static Optional<Item> fromName(String raw) {
            if (raw == null) return Optional.empty();
            String norm = raw.trim().toLowerCase(Locale.ROOT);
            for (Item i : values()) {
                if (i.displayName.toLowerCase(Locale.ROOT).equals(norm)) {
                    return Optional.of(i);
                }
            }
            return Optional.empty();
        }
    }

    /**
     * Calculates the total cost in pence.
     */
    public int calculateTotalPence(List<String> items) {
        if (items == null || items.isEmpty()) return 0;

        // Map names to Item enums, validating along the way
        List<Item> mapped = new ArrayList<>();
        for (String s : items) {
            Optional<Item> maybe = Item.fromName(s);
            if (maybe.isEmpty()) {
                throw new IllegalArgumentException("Unknown item: " + s);
            }
            mapped.add(maybe.get());
        }

        // Count per item type
        Map<Item, Long> counts = mapped.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        int total = 0;
        for (Map.Entry<Item, Long> e : counts.entrySet()) {
            Item item = e.getKey();
            int count = e.getValue().intValue();
            switch (item) {
                case APPLE -> total += count * item.price();
                case BANANA -> total += count * item.price();
                case MELON -> {
                    // pay for half rounded up
                    int payable = (count / 2) + (count % 2);
                    total += payable * item.price();
                }
                case LIME -> {
                    // 3 for 2: groups of 3 cost price*2, remainder cost price each
                    int groups = count / 3;
                    int remainder = count % 3;
                    total += (groups * 2 * item.price()) + (remainder * item.price());
                }
            }
        }
        return total;
    }

    public static String formatPence(int pence) {
        boolean negative = pence < 0;
        int abs = Math.abs(pence);
        int pounds = abs / 100;
        int pennies = abs % 100;
        return (negative ? "-" : "") + "£" + pounds + "." + String.format("%02d", pennies);
    }
}
