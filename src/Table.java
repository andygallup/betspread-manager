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
    private List<ArrayList<Integer>> player_hands;

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
        this.player_hands = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 4; i++){
            player_hands.add(new ArrayList<Integer>());
        }

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
     * Determines if the deck needs to be shuffled based on deck penetration
     * @return
     */
    public boolean deck_needs_shuffle(){
        //if deck_penetration percentage of cards have been dealt, return true, else false
        return (deck.size()/(52*shoe_size) + penetration <= 1);
    }

    /**
    * Calculates true count based on running count and decks_remaining
     *
     * Args:
     *  @param running (int) : Running Count (usually should be running_count)
     *  @param deck_size (int): number of cards remaining in deck (usually should be deck.size())
     *
     * Returns:
     *  Rounded true count (int)
    */
    public int calculate_true_count(){
        double unrounded_count = (double)(running_count)/((double)(deck.size())/52.0);
        return (int)(unrounded_count);
    }

    /**
     * Adds money from the bankroll to the pot based on the true count and the
     */
    public void make_bet(){
      return;
    }

    /**
     * Takes cards from the deck and moves them to the player/dealer hands.
     * increments the count based on cards dealt
     */
    public void deal_cards(){
        //clear the hands
        for (int i = 0; i < 4; i++){
            player_hands.get(i).clear();
        }
        dealer_hand.clear();

        //pop 4 cards off of deck, 2 to each hand
        int first_card = deck.remove(deck.size()-1);
        add_card_to_count(first_card);
        player_hands.get(0).add(first_card);
        int second_card = deck.remove(deck.size()-1);
        add_card_to_count(second_card);
        dealer_hand.add(second_card);
        int third_card = deck.remove(deck.size()-1);
        add_card_to_count(third_card);
        player_hands.get(0).add(third_card);
        int fourth_card = deck.remove(deck.size()-1);
        add_card_to_count(fourth_card);
        dealer_hand.add(fourth_card);
    }

    /**
     * given a card, update the count
     */
    private void add_card_to_count(int card){
        if (card == 10 || card == 1){
            running_count--;
            return;
        }
        if (card < 7){
            running_count++;
            return;
        }
    }


    /**
     * Updates the dealer hand in accordance with the given ruleset.
     */
    public void make_dealer_decision(List<Integer> deck, List<Integer> dealer_hand, int running_count) {
        return;
    }

    /**
     * Given the dealer and player hands, plays the game optimally
     * Recursively iterates until an end condition is reached (stand, double, bust or blackjack)
     * Keeps track of running count, and constantly makes best decision as count changes
     */
    public boolean make_player_decision() {
        // Make player decision
        for(int i = 0; i < 4; i++){
            List<Integer> hand = player_hands.get(i);
            if (hand.isEmpty()) {break;}
            play_hand(hand);
        }

        // Increment the count if hit
        return true;
    }

    /**
     * plays a single hand. If necessary, will add a player_hand and deal out the extra cards.
     */
    public void play_hand(List<Integer> hand){
        //play the hand
        boolean keep_playing = true;
        while(keep_playing){
            // HANDLE SURRENDER

            // HANDLE ALL SPLITS
            if(should_split(hand)){
                split(hand);
                continue;
            }

            // HANDLE ALL DOUBLES

            // HANDLE REMAINING SOFT TOTALS

            // HANDLE REMAINING HARD TOTALS
        }
    }

    /**
     * Returns true if the player should split, false otherwise
     * @param hand
     * @return
     */
    private boolean should_split(List<Integer> hand){
        if (hand.size() != 2) {return false;}

        int carda = hand.get(0);
        int cardb = hand.get(1);
        int dealer = dealer_hand.get(0);
        int count = calculate_true_count();

        if(carda != cardb) {return false;}
        if(carda == 1) {return true;}
        if(carda == 2) {
            return 1 < dealer && dealer < 8;
        }
        if(carda == 3) {
            return 1 < dealer && dealer < 8;
        }
        if(carda == 4) {
            return dealer == 5 || dealer == 6;
        }
        if(carda == 5) {return false;}
        if(carda == 6) {
            //split 6 into 6 and lower
            //stand 12 into 3 at TC 2
            //stand 12 into 2 at TC 3
            if (count >= 2.0 && dealer == 3){
                return false;
            }
            if (count >= 3.0 && dealer == 2){
                return false;
            }
            return 1 < dealer && dealer < 7;
        }
        if(carda == 7) {
            return 1 < dealer && dealer < 8;
        }
        if(carda == 8) {return true;}
        if(carda == 9) {
            return dealer != 7 || dealer != 10 || dealer != 1;
        }
        if(carda == 10) {return false;}
        return false;
    }

    /**
     * Splits the hand into two hands if allowed (max 3 splits)
     * @param hand
     */
    private void split(List<Integer> hand){
        //split if necessary
        for(int i = 0; i < 4; i++){
            if (player_hands.get(i).isEmpty()){
                // remove second card from first hand
                int split_card = hand.remove(1);
                // add second card to second hand
                player_hands.get(i).add(split_card);
                // get a card from the deck to add to the first hand
                int first_card = deck.remove(deck.size()-1);
                add_card_to_count(first_card);
                hand.add(first_card);
                // get a card from the deck to add to the second hand
                int second_card = deck.remove(deck.size()-1);
                add_card_to_count(second_card);
                player_hands.get(i).add(second_card);

                return;
            }
        }
    }

    /**
     * Resets the table status.
     * Convenience method to avoid creating new table instances for every loop in App.java
     */
    public void reset(){
        bankroll = original_bankroll;
        rounds_played = 0;
        running_count = 0;
        deck.clear();
        dealer_hand.clear();
        for (int i = 0; i < 4; i++){
            player_hands.get(i).clear();
        }

        shuffle();
    }

    /**
     * Generates a Stats object from the table state
     * @return
     */
    public Stats get_stats(){
        return new Stats();

    }

    /**
     * Compares Player hand(s) to Dealer and pays winnings and subtracts losses.
     */
    public void pay_out() {
      return;
    }

    /**
     * Plays the game at this table for the given number of hours
     * or until bankroll reaches 0.
     *
     * @param target_hours
     * @param target_profit
     *
     * Returns:
     *  stats (Stats)
     *
     */
    public Stats play_target_hours(double target_hours){
        int hours_played = 0;
        while(hours_played < target_hours && bankroll > 0.0){
            //THE BIG LOOP
            if (deck_needs_shuffle()){
                shuffle();
            }

            deal_cards();

            make_player_decision();

            make_dealer_decision(deck, dealer_hand, running_count);

            pay_out();
        }
        return get_stats();
    }
}


class Stats{

}
