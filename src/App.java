/**
Oct 3, 2020

Betspread management software
 Takes command line args and runs simulated blackjack games to determine risk of ruin, expected value, etc...
 for the given input args.

**/

import java.util.ArrayList;
import java.util.List;

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
            hands_per_hr = 100;
            shoe_size = 6;
            penetration = 0.75;
            hit_on_soft = false;
            original_bankroll = 5000;
            target_hours = 1000;
            run_count = 1000;
        }
        int num_runs = 0;
        List<Stats> stats_list = new ArrayList<Stats>();
        double avg_bankroll_delta = 0.0;
        double hours_played = 0.0;
        double broke_run_count = 0.0;
        double ror = 0.0;

        while(num_runs < run_count) {
            Table table = new Table(table_min, spread, hands_per_hr, shoe_size, penetration, hit_on_soft, original_bankroll);
            Stats stats = table.play_target_hours(target_hours);
            stats_list.add(stats);
            //System.out.print(stats.toString());
            num_runs++;
            //System.out.println("loop: " + num_runs);
        }

        for(int i = 0; i < run_count; i++){
            avg_bankroll_delta += stats_list.get(i).get_bankroll_delta();
            hours_played += stats_list.get(i).get_hours_played();
            if(stats_list.get(i).get_final_bankroll() <= 0.0){
                broke_run_count++;
            }
            else{
                //System.out.println(stats_list.get(i).get_final_bankroll());
            }
        }
        avg_bankroll_delta = avg_bankroll_delta/(double)run_count;
        ror = broke_run_count/(double)run_count;

        double ev_per_hour = avg_bankroll_delta/(hours_played/(double)run_count);

        System.out.println("AVERAGE BANKROLL DELTA: " + avg_bankroll_delta);
        System.out.println("AVERAGE EV: " + ev_per_hour);
        System.out.println("EV PER HAND: " + ev_per_hour/(double)hands_per_hr);
        System.out.println("ROR: " + ror);
    }
}
