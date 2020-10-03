/**
 Oct 3, 2020

 Table class
 Holds data for blackjack play. Includes shoe, table rules, player/dealer hands.
 "plays the game"

 **/

import java.util.List;

public class Table {
    // Class variables
    private final int table_min;
    private final int spread;
    private final int hands_per_hr;
    private final double penetration;
    private final boolean hit_on_soft;
    private int rounds_played;
    private int bankroll;
    private int pot;
    private double count;
    private List deck;
    private List dealer_hand;
    private List player_hand;

    public Table(int table_min, int spread, int hands_per_hr, double penetration, boolean hit_on_soft) {

    }
}