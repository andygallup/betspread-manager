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
    private double bankroll;
    private int running_count;
    private double[] pot;
    private List<Integer> deck;
    private List<Integer> dealer_hand;
    private List<ArrayList<Integer>> player_hands;

    //Stat trackers
    private double biggest_win;
    private double biggest_loss;
    private int number_of_splits;
    private int number_of_surrenders;
    private int number_of_doubles;
    private int number_of_hits;
    private int number_of_stands;
    private int hands_played;
    private double total_bet_amount;

    public Table(int table_min, int spread, int hands_per_hr, int shoe_size, double penetration, boolean hit_on_soft, double original_bankroll) {
        this.table_min = table_min;
        this.spread = spread;
        this.hands_per_hr = hands_per_hr;
        this.shoe_size = shoe_size;
        this.penetration = penetration;
        this.hit_on_soft = hit_on_soft;
        this.bankroll = original_bankroll;
        this.original_bankroll = original_bankroll;
        this.running_count = 0;
        this.deck = new ArrayList<Integer>();
        this.dealer_hand = new ArrayList<Integer>();
        this.player_hands = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 4; i++){
            player_hands.add(new ArrayList<Integer>());
        }
        this.pot = new double[4];

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
     *  Rounded (floored) true count (int)
    */
    public double calculate_true_count(){
        return (double)(running_count)/((double)(deck.size())/52.0);
    }

    /**
     * Adds money from the bankroll to the pot based on the true count and the
     * Assumes a 1-12 spread
     */
    public void make_bet(){
        // Clear the pots
        for (int i = 0; i < player_hands.size(); i++) {
            pot[i] = 0.0;
        }

        double count = calculate_true_count();
        int rounded_count = (int)(count);
        double max_bet = spread * table_min;
        double bet = 0.0;

        // determine our bet based on the count
        if(rounded_count <= 1){bet = table_min;}
        if(rounded_count == 2){bet = (1.0/3.0) * max_bet;}
        if(rounded_count == 3){bet = (2.0/3.0) * max_bet;}
        if(rounded_count >= 4){bet = max_bet;}

        bankroll -= bet;
        pot[0] = bet;
        total_bet_amount += bet;
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
        add_card_to_hand(player_hands.get(0));
        add_card_to_hand(player_hands.get(0));
        add_card_to_hand(dealer_hand);

        int fourth_card = deck.remove(deck.size()-1);
        // Do not add dealer's second card to count until make_dealer_decision method
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
     * given a hand, draw a card from the shoe and update the running count
     * @param hand
     * @return
     */
    private int add_card_to_hand(List<Integer> hand) {
        int card = deck.remove(deck.size() - 1);
        add_card_to_count(card);
        hand.add(card);
        return card;
    }

    /**
     * sums the hand, assuming A = 1.
     */
    private int sum_hand(List<Integer> hand) {
        int total = 0;
        for (Integer i : hand) {
            total += i;
        }
        return total;
    }

    /**
     * Updates the dealer hand in accordance with the given ruleset.
     */
    public void make_dealer_decision() {
        int carda = dealer_hand.get(0);
        int cardb = dealer_hand.get(1);
        // Add the hidden dealer card to the count
        add_card_to_count(cardb);

        if (dealer_hand.contains(1) && dealer_hand.contains(10)) {
            //TODO: WHAT TO DO IN BLACKJACK CASE
            return;
        }

        boolean isSoft = (carda == 1) || (cardb == 1);

        if (!isSoft) {
            while (sum_hand(dealer_hand) < 17) {
                int card = add_card_to_hand(dealer_hand);
                if (card == 1 && sum_hand(dealer_hand) < 12) {
                    isSoft = true;
                    break;
                }
            }
        }
        if (isSoft) {
            while (sum_hand(dealer_hand) < 7) {
                add_card_to_hand(dealer_hand);
            }
            int curr_sum = sum_hand(dealer_hand);
            if (curr_sum == 7 && hit_on_soft) {
                int card = add_card_to_hand(dealer_hand);
                curr_sum += card;
            }
            if (curr_sum <= 11) {
                return;
            } else { // hard totals again
                while (sum_hand(dealer_hand) < 17) {
                    add_card_to_hand(dealer_hand);
                }
                return;
            }
        }
        return;
    }

    /**
     * Given the dealer and player hands, plays the game optimally
     * Iterates until an end condition is reached (stand, double, bust or blackjack)
     * Keeps track of running count, and constantly makes best decision as count changes
     */
    public void make_player_decision() {
        // Make player decision
        for(int i = 0; i < 4; i++){
            List<Integer> hand = player_hands.get(i);
            if (hand.isEmpty()) {break;}
            play_hand(hand, i);
        }
    }

    /**
     * plays a single hand. If necessary, will add a player_hand and deal out the extra cards.
     */
    public void play_hand(List<Integer> hand, int index){
        //play the hand
        boolean keep_playing = true;
        while(keep_playing){
            // HANDLE ALL SPLITS
            if(should_split(hand)){
                split(hand, index);
                number_of_splits++;
                continue;
            }

            // HANDLE SURRENDER (assumes no late surrender)
            if(should_surrender(hand)){
                surrender(index);
                number_of_surrenders++;
                return;
            }

            // HANDLE ALL DOUBLES
            if(should_double(hand)){
                doubleHand(hand, index);
                number_of_doubles++;
                return;
            }

            // HANDLE HITS
            if(should_hit(hand)){
                add_card_to_hand(hand);
                number_of_hits++;
            }
            else{
                number_of_stands++;
                return;
            }
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
        double count = calculate_true_count();

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
        if(carda == 8) {
            if(hit_on_soft && dealer_hand.get(0) == 1){
                return false;
            }
            return true;
        }
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
    private void split(List<Integer> hand, int index){
        //split if necessary
        for(int i = index + 1; i < 4; i++){
            if (player_hands.get(i).isEmpty()){
                // remove second card from first hand
                int split_card = hand.remove(1);
                // add second card to second hand
                player_hands.get(i).add(split_card);
                // get a card from the deck to add to the first hand
                add_card_to_hand(hand);
                // get a card from the deck to add to the second hand
                add_card_to_hand(player_hands.get(i));
                // put more money in the pot for the second hand
                bankroll -= pot[index];
                pot[i] = pot[index];
                total_bet_amount += pot[index];
                return;
            }
        }
    }

    /**
     * given a hand, determine if the player should double
     * @param hand
     * @return boolean
     * Returns true if player should double, false if otherwise
     */
    private boolean should_double(List<Integer> hand) {
        // can only double initial (two card) hands
        if (hand.size() > 2){
            return false;
        }
        // Soft total
        if (hand.contains(1) && sum_hand(hand) < 12){
            int sum = sum_hand(hand) + 10;
            int dealer = dealer_hand.get(0);
            if ((sum == 13 || sum == 14) && (dealer == 5 || dealer == 6)){
                return true;
            }
            if ((sum == 15 || sum == 16) && (dealer == 4 || dealer == 5 || dealer == 6)){
                return true;
            }
            if ((sum == 17 || sum == 18) && (dealer == 3 || dealer == 4 || dealer == 5 || dealer == 6)){
                return true;
            }
            if (sum == 18 && dealer == 2 && hit_on_soft) {
                return true;
            }
            if (sum == 19 && dealer == 6 && hit_on_soft) {
                return true;
            }
            return false;
        }
        // hard totals
        else{
            int sum = sum_hand(hand);
            int dealer = dealer_hand.get(0);
            double count = calculate_true_count();

            if (sum == 9){
                if (dealer == 3 || dealer == 4 || dealer == 5 || dealer == 6){
                    return true;
                }
                if (dealer == 2 && count >= 1){
                    return true;
                }
                if (dealer == 7 && count >= 4){
                    return true;
                }
            }
            if (sum == 10){
                if (dealer != 10 && dealer != 1){
                    return true;
                }
                if (count >= 4){
                    //TODO: check with ben about doing this at TC3 in a H17 game
                    return true;
                }
            }
            if (sum == 11){
                if (hit_on_soft && count < 0 && dealer == 1) {
                    return false;
                }
                if (!hit_on_soft && count < 1 && dealer == 1) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    /**
     * Adds a card to the hand and doubles the correct pot.
     */
    private void doubleHand(List<Integer> hand, int index) {
        add_card_to_hand(hand);
        if (!(pot[index] > 0)) {
            throw new RuntimeException("Trying to double an uninitiated hand, index: " + index);
        }
        bankroll -= pot[index];
        pot[index] = pot[index] * 2;
        total_bet_amount += pot[index];
    }

    /**
     * given a hand, determine if the player should surrender
     * @param hand
     * @return
     */
    private boolean should_surrender(List<Integer> hand){
        // can only surrender 2 card hands (TODO: allow late surrender?)
        if (hand.size() > 2) {return false;}
        // Soft total
        if (hand.contains(1) && sum_hand(hand) < 12){
            return false;
        }
        int dealer = dealer_hand.get(0);
        // Handle pair of 8s
        if (hand.get(0) == 8 && hand.get(1) == 8){
            if(dealer == 1 && hit_on_soft){
                return true;
            }
            else{
                return false;
            }
        }
        // Hard total
        int sum = sum_hand(hand);
        double count = calculate_true_count();

        if (sum == 14 && dealer == 10 && count >= 3){
            return true;
        }
        if (sum == 15){
            if (dealer == 10 && count > 0){
                return true;
            }
            if (dealer == 1 && (hit_on_soft || count >= 1)){
                return true;
            }
            if (dealer == 9 && count >= 2){
                return true;
            }
        }
        if(sum == 16 && (dealer == 9 || dealer == 10 || dealer == 1)){
            return true;
        }
        if(sum == 17 && dealer == 1 && hit_on_soft){
            return true;
        }
        return false;
    }

    /**
     * Makes hand disappear so that it isn't picked up by pay_out and returns half the pot.
     */
    private void surrender(int index) {
        player_hands.get(index).clear();
        bankroll += pot[index] / 2;
    }

    /**
     * given a hand, determine if the player should hit
     * @param hand
     * @return
     */
    private boolean should_hit(List<Integer> hand){
        int sum = sum_hand(hand);
        int dealer = dealer_hand.get(0);
        double count = calculate_true_count();

        //Soft totals
        if(hand.contains(1) && sum < 12){
            if(sum < 8){
                return true;
            }
            if(sum == 8 && (dealer == 9 || dealer == 10 || dealer == 1)){
                return true;
            }
            return false;
        }
        //Hard totals
        if(sum < 12){return true;}
        if(sum == 12){
            if(count >= 3 && (dealer > 6 || dealer == 1)) {return true;}
            if(count >= 2 && (dealer > 6 || dealer < 3)) {return true;}
            if(count >= 0 && (dealer > 6 || dealer < 4)) {return true;}
            if(count <= 0 && (dealer > 6 || dealer < 5)) {return true;}
            if(count <= -1 && dealer != 5) {return true;}
            if(count <= -2) {return true;}
            return false;
        }
        if(sum == 13 || sum == 14){
            if(dealer > 6 || dealer == 1) {return true;}
            return false;
        }
        if(sum == 15){
            if(count >= 4 && dealer == 10) {return false;}
            if(dealer > 6 || dealer == 1) {return true;}
            return false;
        }
        if(sum == 16){
            if(count > 0 && dealer == 10) {return false;}
            if(count >= 5 && dealer == 9) {return false;}
            if(dealer > 6 || dealer == 1) {return true;}
            return false;
        }
        if(sum >= 17){return false;} //Assumes you've already surrendered in a H17 game into A
        else{return false;}
    }

    /**
     * Resets the table status.
     * Convenience method to avoid creating new table instances for every loop in App.java
     */
    public void reset(){
        bankroll = original_bankroll;
        running_count = 0;
        deck.clear();
        dealer_hand.clear();
        for (int i = 0; i < 4; i++){
            player_hands.get(i).clear();
        }
        // Clear the pots
        for (int i = 0; i < player_hands.size(); i++) {
            pot[i] = 0.0;
        }

        shuffle();
    }

    /**
     * Generates a Stats object from the table state
     * @return
     */
    public Stats get_stats(double hours_played){
        Stats stats = new Stats();
        stats.set_final_bankroll(bankroll);
        stats.set_bankroll_delta(bankroll-original_bankroll);
        stats.set_hours_played(hours_played);
        stats.set_biggest_win(-1.0);
        stats.set_biggest_loss(-1.0);
        stats.set_rounds_played(hands_played);
        stats.set_number_of_splits(number_of_splits);
        stats.set_number_of_doubles(number_of_doubles);
        stats.set_number_of_surrenders(number_of_surrenders);
        stats.set_number_of_hits(number_of_hits);
        stats.set_number_of_stands(number_of_stands);
        stats.set_avg_bet_size(total_bet_amount/(double)(hands_played));
        return stats;
    }

    /**
     * Compares Player hand(s) to Dealer and pays winning: losses were already subtracted.
     */
    public void pay_out() {
        for (int i = 0; i < player_hands.size(); i++) {
            List<Integer> hand = player_hands.get(i);
            if (hand.isEmpty()) {
                continue;
            }
            int player_sum = sum_hand(hand);

            if (player_sum > 21) {
                continue;
            }
            if (player_sum < 12 && hand.contains(1)) {
                player_sum += 10;
            }
            int dealer_sum = sum_hand(dealer_hand);

            if (dealer_sum > 21) {
                bankroll += 2 * pot[i];
                continue;
            }
            if (dealer_sum < 12 && dealer_hand.contains(1)) {
                dealer_sum += 10;
            }

            if (dealer_sum > player_sum) {
                continue;
            } else if (dealer_sum < player_sum) {
                bankroll += 2 * pot[i];
                if (player_sum == 21 && hand.size() == 2){
                    bankroll += 0.5 * pot[i];
                }
                continue;
            } else {
                bankroll += pot[i];
                continue;
            }
        }
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
        double hours_played = 0.0;
        while(hours_played < target_hours && bankroll > 0.0){
            //THE BIG LOOP
            if (deck_needs_shuffle()){
                shuffle();
            }

            make_bet();

            deal_cards();

            make_player_decision();

            make_dealer_decision();

            pay_out();

            hours_played += 1/(double)hands_per_hr;
            hands_played++;
        }
        return get_stats(hours_played);
    }
}
