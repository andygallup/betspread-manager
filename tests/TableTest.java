import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    @BeforeEach
    void setUp() {
        int table_min = 25;
        int spread = 12;
        int hands_per_hr = 100;
        int shoe_size = 6;
        double penetration = 0.75;
        boolean hit_on_soft = true;
        double original_bankroll = 10000;
        Table table = new Table(table_min, spread, hands_per_hr, shoe_size, penetration, hit_on_soft, original_bankroll);
    }

    @Test
    void one_plus_one() {
        assertEquals(1, 1);
    }
}
