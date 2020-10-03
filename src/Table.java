/**
 Oct 3, 2020

 Table class
 Holds data for blackjack play. Includes shoe, table rules, player/dealer hands.
 "plays the game"

 **/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Table {
    // Class variables
    private final int table_min;
    private final int spread;
    private final int hands_per_hr;
    private final int shoe_size;
    private final double penetration;
    private final boolean hit_on_soft;
    private final double original_bankroll;
    private int rounds_played;
    private double bankroll;
    private int pot;
    private int running_count;
    private List<Integer> deck;
    private List<Integer> dealer_hand;
    private List<Integer> player_hand;

    public Table(int table_min, int spread, int hands_per_hr, int shoe_size, double penetration, boolean hit_on_soft, double original_bankroll) {
        this.table_min = table_min;
        this.spread = spread;
        this.hands_per_hr = hands_per_hr;
        this.shoe_size = shoe_size;
        this.penetration = penetration;
        this.hit_on_soft = hit_on_soft;
        this.bankroll = original_bankroll;
        this.original_bankroll = original_bankroll;
        this.rounds_played = 0;
        this.running_count = 0;
        this.deck = new ArrayList<Integer>();
        this.dealer_hand = new ArrayList<Integer>();
        this.player_hand = new ArrayList<Integer>();

        shuffle();
    }

    /**
    Shuffles the deck.
    Empties the ArrayList, then populates it with a full deck's content
    **/
    public void shuffle(){
        //Empty the deck
        deck.clear();

        //Repopulate the deck
        List<Integer> fresh_deck = Arrays.asList(1,1,1,1,
        2,2,2,2,
        3,3,3,3,
        4,4,4,4,
        5,5,5,5,
        6,6,6,6,
        7,7,7,7,
        8,8,8,8,
        9,9,9,9,
        10,10,10,10,
        10,10,10,10,
        10,10,10,10,
        10,10,10,10);
        for (int i = 0; i < shoe_size; i++) {
            deck.addAll(fresh_deck);
        }

        //Shuffle
        Collections.shuffle(deck);
    }

    /**
    Calculates true count based on running count and remaining shoe size.
    **/
    public double calculate_true_count(){
        return -1.0;
    }

    /**
     * Resets the table status.
     * Convenience method to avoid creating new table instances for every loop in app.java
     */
    public void reset(){
        bankroll = original_bankroll;
        rounds_played = 0;
        running_count = 0;
        deck.clear();
        dealer_hand.clear();
        player_hand.clear();

        shuffle();
    }
}