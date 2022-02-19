import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table;
    private int table_min = 25;
    private int spread = 12;
    private int hands_per_hr = 100;
    private int shoe_size = 6;
    private double penetration = 0.75;
    private boolean hit_on_soft = true;
    private double original_bankroll = 10000;
    private boolean counting = true;
    private boolean big_small = true;


    @BeforeEach
    void setUp() {
        ArrayList<Integer> spread_array = new ArrayList<Integer>();
        spread_array.add(1);
        spread_array.add(2);
        spread_array.add(4);
        spread_array.add(16);
        spread_array.add(32);
        ArrayList<Integer> player_array = new ArrayList<Integer>();
        player_array.add(1);
        player_array.add(1);
        player_array.add(1);
        player_array.add(1);
        player_array.add(1);
        Table table = new Table(table_min, spread, hands_per_hr, shoe_size, penetration, hit_on_soft, original_bankroll, counting, big_small, spread_array, player_array);
        this.table = table;
    }

    /**
     * Helper method that returns a single hand with the specified cards
     *
     * @param carda
     * @param cardb
     * @return
     */
    ArrayList<Integer> create_hand(int carda, int cardb) {
        ArrayList<Integer> hand = new ArrayList<Integer>();
        hand.add(carda);
        hand.add(cardb);
        return hand;
    }

    /**
     * Given a hand, set the cards to the new values
     *
     * @param hand
     * @param carda
     * @param cardb
     */
    void set_hand(List<Integer> hand, int carda, int cardb) {
        hand.clear();
        hand.add(carda);
        hand.add(cardb);
    }

    /**
     * basic test to test the test functionality. test.
     */
    @Test
    void one_plus_one() {
        assertEquals(1, 1);
    }

    @Test
    void should_split_test() {
        ArrayList<Integer> dealer_hand = create_hand(1, 1);
        table.set_dealer_hand(dealer_hand);
        ArrayList<Integer> player_hand = create_hand(1, 2);

        //TEST THAT YOU CAN'T SPLIT INVALID HANDS (DIFFERENT CARDS)
        assertFalse(table.should_split(player_hand));

        player_hand.add(5);
        //TEST THAT YOU CAN'T SPLIT INVALID HANDS (TOO MANY CARDS)
        assertFalse(table.should_split(player_hand));

        // TESTING PLAYER ACES
        set_hand(player_hand, 1, 1);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Regardless of dealer hand or hit on soft, always split aces
            assertTrue(table.should_split(player_hand));
        }

        //TESTING PLAYER 2s
        set_hand(player_hand, 2, 2);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Split 2s into 2-7, hit otherwise
            if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
                assertTrue(table.should_split(player_hand));
            } else {
                assertFalse(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 3s
        set_hand(player_hand, 3, 3);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Split 3s into 2-7, hit otherwise
            if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
                assertTrue(table.should_split(player_hand));
            } else {
                assertFalse(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 4s
        set_hand(player_hand, 4, 4);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Split 4s into 5 and 6, hit otherwise
            if (i == 5 || i == 6) {
                assertTrue(table.should_split(player_hand));
            } else {
                assertFalse(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 5s
        set_hand(player_hand, 5, 5);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Never split 5s
            assertFalse(table.should_split(player_hand));
        }

        //TESTING PLAYER 6s
        set_hand(player_hand, 6, 6);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Split 6s into 2-6, hit otherwise
            if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
                assertTrue(table.should_split(player_hand));
            } else {
                assertFalse(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 7s
        set_hand(player_hand, 7, 7);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Split 7s into 2-7, hit otherwise
            if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7) {
                assertTrue(table.should_split(player_hand));
            } else {
                assertFalse(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 8s
        set_hand(player_hand, 8, 8);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Always split 8s unless it's hit on soft and dealer has an Ace
            if (i == 1 && table.is_hit_on_soft()) {
                assertFalse(table.should_split(player_hand));
            } else {
                assertTrue(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 9s
        set_hand(player_hand, 9, 9);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Split 9s into everything except 7, 9, and 10
            if (i == 7 || i == 10 || i == 1) {
                assertFalse(table.should_split(player_hand));
            } else {
                assertTrue(table.should_split(player_hand));
            }
        }

        //TESTING PLAYER 10s
        set_hand(player_hand, 10, 10);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            //Never split 10s
            assertFalse(table.should_split(player_hand));
        }
    }

    @Test
    void should_double_hard_test() {
        ArrayList<Integer> dealer_hand = create_hand(1, 1);
        table.set_dealer_hand(dealer_hand);
        ArrayList<Integer> player_hand = create_hand(1, 2);

        //TEST THAT YOU CAN'T DOUBLE INVALID HANDS (TOO MANY CARDS)
        player_hand.add(5);
        assertFalse(table.should_split(player_hand));

        // TESTING PLAYER HARD <9
        for (int i = 2; i < 7; i++) {
            set_hand(player_hand, 2, i);
            for (int j = 1; j < 11; j++) {
                set_hand(dealer_hand, j, j);
                //never double a hand below 9
                assertFalse(table.should_double(player_hand));
            }
        }
        // TESTING PLAYER HARD 9
        set_hand(player_hand, 2, 7);
        for (int count = 0; count < 6; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Double 9 on 3, 4, 5, and 6
                if (i == 2 && count >= 1){
                    assertTrue(table.should_double(player_hand));
                }
                else if (i == 3 || i == 4 || i == 5 || i == 6) {
                    assertTrue(table.should_double(player_hand));
                }
                else if (i == 7 && count >= 3){
                    assertTrue(table.should_double(player_hand));
                }
                else {
                    assertFalse(table.should_double(player_hand));
                }
            }
        }

        // TESTING PLAYER HARD 10
        set_hand(player_hand, 2, 8);
        for (int count = 0; count < 5; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Double 10 except on dealer 10 or Ace
                //Double into everything if count > 4
                if (i == 10 || i == 1) {
                    if (count >= 4) {
                        assertTrue(table.should_double(player_hand));
                    } else {
                        assertFalse(table.should_double(player_hand));
                    }
                } else {
                    assertTrue(table.should_double(player_hand));
                }
            }
        }

        // TESTING PLAYER HARD 11
        set_hand(player_hand, 2, 9);
        for (int count = 0; count < 2; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Double A into everything except dealer Ace in S17 on count < 1
                if (i == 1 && !table.is_hit_on_soft() && count < 1) {
                    assertFalse(table.should_double(player_hand));
                } else {
                    assertTrue(table.should_double(player_hand));
                }
            }
        }

        // TESTING PLAYER HARD >12
        for (int j = 2; j < 11; j++) {
            set_hand(player_hand, j, 10);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Never double 12 and up
                assertFalse(table.should_double(player_hand));
            }
        }
    }

    @Test
    void should_double_soft_test() {
        ArrayList<Integer> dealer_hand = create_hand(1, 1);
        table.set_dealer_hand(dealer_hand);
        ArrayList<Integer> player_hand = create_hand(1, 2);

        //TEST THAT YOU CAN'T DOUBLE INVALID HANDS (TOO MANY CARDS)
        player_hand.add(5);
        assertFalse(table.should_split(player_hand));


        // TESTING PLAYER SOFT 12
        set_hand(player_hand, 1, 1);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Never double soft 12 (should split)
            assertFalse(table.should_double(player_hand));
        }

        // TESTING PLAYER SOFT 13 and 14
        for(int j = 2; j < 4; j++){
            set_hand(player_hand, 1, j);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Double soft 13/14 only on 5 and 6
                if(i == 5 || i == 6){
                    assertTrue(table.should_double(player_hand));
                }
                else{
                    assertFalse(table.should_double(player_hand));
                }
            }
        }
        // TESTING PLAYER SOFT 15 and 16
        for(int j = 4; j < 6; j++){
            set_hand(player_hand, 1, j);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Double soft 15/16 only on 4, 5 and 6
                if(i == 4 || i == 5 || i == 6){
                    assertTrue(table.should_double(player_hand));
                }
                else{
                    assertFalse(table.should_double(player_hand));
                }
            }
        }
        // TESTING PLAYER SOFT 17
        set_hand(player_hand, 1, 6);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Double soft 17 only on 3, 4, 5 and 6
            if(i ==3 || i == 4 || i == 5 || i == 6){
                assertTrue(table.should_double(player_hand));
            }
            else{
                assertFalse(table.should_double(player_hand));
            }
        }
        // TESTING PLAYER SOFT 18
        set_hand(player_hand, 1, 7);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Double soft 18 on 3, 4, 5 and 6
            //Double soft 18 on 2 if the table is H17
            if(i == 3 || i == 4 || i == 5 || i == 6){
                assertTrue(table.should_double(player_hand));
            }
            else if (i == 2 && table.is_hit_on_soft()){
                assertTrue(table.should_double(player_hand));
            }
            else{
                assertFalse(table.should_double(player_hand));
            }
        }
        // TESTING PLAYER SOFT 19
        set_hand(player_hand, 1, 8);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Double soft 19 only 6 if table is H17
            if(i == 6 && table.is_hit_on_soft()){
                assertTrue(table.should_double(player_hand));
            }
            else{
                assertFalse(table.should_double(player_hand));
            }
        }
        //TESTING PLAYER SOFT >20
        set_hand(player_hand, 1, 9);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Never double Soft 20 or 21
            assertFalse(table.should_double(player_hand));
        }
        set_hand(player_hand, 1, 10);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Never double Soft 20 or 21
            assertFalse(table.should_double(player_hand));
        }
    }

    @Test
    void should_surrender_test() {
        ArrayList<Integer> dealer_hand = create_hand(1, 1);
        table.set_dealer_hand(dealer_hand);
        ArrayList<Integer> player_hand = create_hand(1, 2);

        ArrayList<ArrayList<Integer>> multiple_hands = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 4; i++){
            multiple_hands.add(new ArrayList<Integer>());
        }
        multiple_hands.get(0).add(4);
        multiple_hands.get(0).add(4);
        multiple_hands.get(1).add(4);
        multiple_hands.get(1).add(4);

        //TEST THAT YOU CAN'T SURRENDER INVALID HANDS (TOO MANY CARDS)
        player_hand.add(5);
        assertFalse(table.should_split(player_hand));

        //TEST THAT YOU CAN'T SURRENDER A SPLIT HAND
        table.set_player_hands(0, multiple_hands);
        assertFalse(table.should_surrender(multiple_hands.get(0)));
        assertFalse(table.should_surrender(multiple_hands.get(1)));

        ArrayList<ArrayList<Integer>> clear_hands = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 4; i++){
            clear_hands.add(new ArrayList<Integer>());
        }

        //CLEAR PLAYER HANDS
        table.set_player_hands(0, clear_hands);

        //TEST PLAYER HARD <12
        for(int second_card = 2; second_card < 11; second_card++){
            set_hand(player_hand, 2, second_card);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Never surrender anything 12 or lower
                assertFalse(table.should_surrender(player_hand));
            }
        }
        //TEST PLAYER HARD 13
        set_hand(player_hand, 3, 10);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Never surrender 13
            assertFalse(table.should_surrender(player_hand));
        }
        //TEST PLAYER HARD 14
        set_hand(player_hand, 4, 10);
        for (int count = 2; count < 5; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //surrender 14 into 10 at TC 3
                if (i == 10 && count >= 3) {
                    assertTrue(table.should_surrender(player_hand));
                } else {
                    assertFalse(table.should_surrender(player_hand));
                }
            }
        }

        //TEST PLAYER HARD 15
        set_hand(player_hand, 5, 10);
        for (int count = -1; count < 5; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //surrender 15 into 10 at positive counts (not at negative counts)
                if (i == 10 && count >= 0) {
                    assertTrue(table.should_surrender(player_hand));
                }
                // surrender 15 into Ace at count >= 1 or if H17
                else if(i == 1 && (count >= 1 || table.is_hit_on_soft())){
                    assertTrue(table.should_surrender(player_hand));
                }
                // surrender 15 into 9 at count >= 2
                else if(i == 9 && count >= 2){
                    assertTrue(table.should_surrender(player_hand));
                }
                else {
                    assertFalse(table.should_surrender(player_hand));
                }
            }
        }

        //TEST PLAYER HARD 16
        set_hand(player_hand, 6, 10);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //Surrender 16 into 9 10 and Ace
            if(i == 9 || i == 10 || i == 1){
                assertTrue(table.should_surrender(player_hand));
            }
            else{
                assertFalse(table.should_surrender(player_hand));
            }
        }

        //TEST PLAYER HARD 17
        set_hand(player_hand, 7, 10);
        for (int i = 1; i < 11; i++) {
            set_hand(dealer_hand, i, i);
            table.set_dealer_hand(dealer_hand);
            //surrender 17 into Ace if H17
            if(i == 1 && table.is_hit_on_soft()){
                assertTrue(table.should_surrender(player_hand));
            }
            else{
                assertFalse(table.should_surrender(player_hand));
            }
        }

        //TEST PLAYER HARD >17
        for(int first_card = 8; first_card < 11; first_card++){
            set_hand(player_hand, first_card, 10);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Never surrender 18 or higher
                assertFalse(table.should_surrender(player_hand));
            }
        }

        //TEST ALL SOFT TOTALS
        for(int first_card = 2; first_card < 11; first_card++){
            set_hand(player_hand, first_card, 1);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                //Never surrender a soft total
                assertFalse(table.should_surrender(player_hand));
            }
        }
    }

    @Test
    void should_hit_hard_test() {
        ArrayList<Integer> dealer_hand = create_hand(1, 1);
        table.set_dealer_hand(dealer_hand);
        ArrayList<Integer> player_hand = create_hand(1, 2);

        //TEST PLAYER HARD <12 (ASSUMES DOUBLING CORRECTLY ON 9, 10, and 11)
        for(int second_card = 2; second_card < 10; second_card++) {
            set_hand(player_hand, 2, second_card);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                assertTrue(table.should_hit(player_hand));
            }
        }

        //TEST PLAYER HARD 12
        set_hand(player_hand, 2, 10);
        for (int count = -3; count < 4; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                // Hit 12 into dealer 2 if count < 3, otherwise stand
                if(i == 2 && count < 3){
                    assertTrue(table.should_hit(player_hand));
                } else if(i == 2){
                    assertFalse(table.should_hit(player_hand));
                }
                // Hit 12 into dealer 3 if count < 2, otherwise stand
                if(i == 3 && count < 2){
                    assertTrue(table.should_hit(player_hand));
                } else if(i == 3){
                    assertFalse(table.should_hit(player_hand));
                }
                // Hit 12 into dealer 4 if count <= 0, otherwise stand
                if(i == 4 && count < 0){
                    assertTrue(table.should_hit(player_hand));
                } else if(i == 4){
                    assertFalse(table.should_hit(player_hand));
                }
                // Hit 12 into dealer 5 if count <= -2, otherwise stand
                if(i == 5 && count <= -2){
                    assertTrue(table.should_hit(player_hand));
                } else if(i == 5){
                    assertFalse(table.should_hit(player_hand));
                }
                // Hit 12 into dealer 6 if count <= -1, otherwise stand
                if(i == 6 && count <= -1){
                    assertTrue(table.should_hit(player_hand));
                } else if(i == 6){
                    assertFalse(table.should_hit(player_hand));
                }
                // Hit 12 into dealer 7, 8, 9, 10, and Ace
                if(i > 6 || i == 1){
                    assertTrue(table.should_hit(player_hand));
                }
            }
        }

        //TEST PLAYER HARD 13, 14, 15, 16 (ASSUMES YOU SURRENDER WITH 14, 15, AND 16 AT APPROPRIATE COUNTS)
        for (int count = -3; count < 4; count++) {
            //Set the count. Multiply by shoe size to convert to true count since no cards have been dealt
            table.set_running_count(count * shoe_size);
            for (int first_card = 3; first_card < 7; first_card++) {
                set_hand(player_hand, first_card, 10);
                for (int i = 1; i < 11; i++) {
                    set_hand(dealer_hand, i, i);
                    table.set_dealer_hand(dealer_hand);
                    // Stand on dealer 6 and lower, otherwise hit
                    if (i < 7 && i != 1) {
                        if (count < 0 && i == 2 && first_card == 3){
                            assertTrue(table.should_hit(player_hand));
                        }
                        else if (count <= -2 && i == 3 && first_card == 3){
                            assertTrue(table.should_hit(player_hand));
                        }
                        else {
                            assertFalse(table.should_hit(player_hand));
                        }
                    }
                    // Stand on 16 into 10 at counts 0 and lower
                    else if (first_card == 6 && i == 10){
                        if(count >= 0) {
                            assertFalse(table.should_hit(player_hand));
                        }
                        else{
                            assertTrue(table.should_hit(player_hand));
                        }
                    }
                    //Stand 15 into 10 at counts 4 and higher
                    else if (first_card == 5 && i == 10){
                        if(count >= 4){
                            assertFalse(table.should_hit(player_hand));
                        }
                        else{
                            assertTrue(table.should_hit(player_hand));
                        }
                    }
                    //Stand 16 into 9 at counts 5 and higher
                    else if (first_card == 6 && i == 9){
                        if(count >= 5){
                            assertFalse(table.should_hit(player_hand));
                        }
                        else{
                            assertTrue(table.should_hit(player_hand));
                        }
                    }
                    else{
                        assertTrue(table.should_hit(player_hand));
                    }
                }
            }
        }

        //TEST PLAYER HARD >=17 (ASSUMES YOU SURRENDER INTO ACE FOR H17 GAMES)
        for(int first_card = 7; first_card < 11; first_card++) {
            set_hand(player_hand, first_card, 10);
            for (int i = 1; i < 11; i++) {
                set_hand(dealer_hand, i, i);
                table.set_dealer_hand(dealer_hand);
                // Always stand hard 17
                assertFalse(table.should_hit(player_hand));
            }
        }
    }
}
