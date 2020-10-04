/**
Oct 3, 2020

Betspread management software
 Takes command line args and runs simulated blackjack games to determine risk of ruin, expected value, etc...
 for the given input args.

**/

public class App{
    public static void main(String[] args){
        int table_min;
        int spread;
        int hands_per_hr;
        int shoe_size;
        double penetration;
        boolean hit_on_soft;
        double original_bankroll;
        double target_hours;
        int run_count;

        //parse args if necessary
        if(args.length > 0){
            table_min = Integer.parseInt(args[0]);
            spread = Integer.parseInt(args[1]);
            hands_per_hr = Integer.parseInt(args[2]);
            shoe_size = Integer.parseInt(args[3]);
            penetration = Double.parseDouble(args[4]);
            if(args[5] == "true"){hit_on_soft = true;}
            else{hit_on_soft = false;}
            original_bankroll = Double.parseDouble(args[6]);
            target_hours = Double.parseDouble(args[7]);
            run_count = Integer.parseInt(args[8]);
        }
        //otherwise use default values
        else{
            table_min = 25;
            spread = 12;
            hands_per_hr = 60;
            shoe_size = 6;
            penetration = 0.75;
            hit_on_soft = true;
            original_bankroll = 1000000;
            target_hours = 100;
            run_count = 1;
        }
        int num_runs = 0;
        while(num_runs < run_count) {
            Table table = new Table(table_min, spread, hands_per_hr, shoe_size, penetration, hit_on_soft, original_bankroll);
            Stats stats = table.play_target_hours(target_hours);
            System.out.print(stats.toString());
            num_runs++;
        }
    }
}