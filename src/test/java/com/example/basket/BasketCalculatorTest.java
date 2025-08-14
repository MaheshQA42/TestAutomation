
package com.example.basket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasketCalculatorTest {

    private final BasketCalculator calc = new BasketCalculator();

    @Test
    @DisplayName("Empty basket costs 0")
    void emptyBasket() {
        int total = calc.calculateTotalPence(List.of());
        assertEquals(0, total);
    }

    @ParameterizedTest(name = "{0} x Apple(s)")
    @CsvSource({
            "1,35",
            "2,70",
            "3,105"
    })
    void applesCost(int qty, int expected) {
        int total = calc.calculateTotalPence(List.nCopies(qty, "Apple"));
        assertEquals(expected, total);
    }

    @ParameterizedTest(name = "{0} x Banana(s)")
    @CsvSource({
            "1,20",
            "2,40",
            "5,100"
    })
    void bananasCost(int qty, int expected) {
        int total = calc.calculateTotalPence(List.nCopies(qty, "Banana"));
        assertEquals(expected, total);
    }

    @ParameterizedTest(name = "Melons BOGOF qty={0}")
    @CsvSource({
            "1,50",   // pay for 1
            "2,50",   // pay for 1
            "3,100",  // pay for 2
            "4,100",  // pay for 2
            "5,150"   // pay for 3
    })
    void melonsBogof(int qty, int expected) {
        int total = calc.calculateTotalPence(List.nCopies(qty, "Melon"));
        assertEquals(expected, total);
    }

    @ParameterizedTest(name = "Limes 3-for-2 qty={0}")
    @CsvSource({
            "1,15",
            "2,30",
            "3,30",
            "4,45",
            "5,60",
            "6,60",
            "7,75"
    })
    void limesThreeForTwo(int qty, int expected) {
        int total = calc.calculateTotalPence(List.nCopies(qty, "Lime"));
        assertEquals(expected, total);
    }

    @Test
    @DisplayName("Case-insensitive and trimmed names are accepted")
    void caseInsensitiveNames() {
        int total = calc.calculateTotalPence(List.of("  apple ", "BANANA", "mElOn", "LiMe"));
        // 35 + 20 + 50 + 15 = 120
        assertEquals(120, total);
    }

    @Test
    @DisplayName("Unknown item throws")
    void unknownItemThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> calc.calculateTotalPence(List.of("Apple", "Dragonfruit")));
        assertTrue(ex.getMessage().toLowerCase().contains("unknown item"));
    }

    @Test
    @DisplayName("Mixed basket scenario from brief")
    void mixedBasketExample() {
        int total = calc.calculateTotalPence(List.of("Apple", "Apple", "Banana"));
        // 35 + 35 + 20 = 90
        assertEquals(90, total);
        assertEquals("£0.90", BasketCalculator.formatPence(total));
    }
}
