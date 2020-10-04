/**
 Oct 3, 2020

 Stats class
 Book keeping class that tracks stats across the table's play session.
 A single Stat will be created per Table.

 **/
public class Stats {
    private double final_bankroll;
    private double bankroll_delta;
    private int rounds_played;
    private double hours_played;
    private double biggest_win;
    private double biggest_loss;
    private int number_of_splits;
    private int number_of_doubles;
    private int number_of_surrenders;
    private int number_of_hits;
    private int number_of_stands;
    private double avg_bet_size;

    public String toString(){
        String str = "";
        str = str + "FINAL BANKROLL: " + get_final_bankroll() + "\n";
        str = str + "BANKROLL DELTA: " + get_bankroll_delta() + "\n";
        str = str + "ROUNDS PLAYED: " + get_rounds_played() + "\n";
        str = str + "HOURS PLAYED: " + get_hours_played() + "\n";
        str = str + "BIGGEST WIN: " + get_biggest_win() + "\n";
        str = str + "BIGGEST LOSS: " + get_biggest_loss() + "\n";
        str = str + "NUMBER OF SPLITS: " + get_number_of_splits() + "\n";
        str = str + "NUMBER OF DOUBLES: " + get_number_of_doubles() + "\n";
        str = str + "NUMBER OF SURRENDERS: " + get_number_of_surrenders() + "\n";
        str = str + "NUMBER OF HITS: " + get_number_of_hits() + "\n";
        str = str + "NUMBER OF STANDS: " + get_number_of_stands() + "\n";
        str = str + "AVERAGE BET SIZE: " + get_avg_bet_size() + "\n";

        return str;
    }

    public double get_final_bankroll() {
        return final_bankroll;
    }

    public double get_bankroll_delta() {
        return bankroll_delta;
    }

    public int get_rounds_played() {
        return rounds_played;
    }

    public double get_hours_played() {
        return hours_played;
    }

    public double get_biggest_win() {
        return biggest_win;
    }

    public double get_biggest_loss() {
        return biggest_loss;
    }

    public int get_number_of_splits() {
        return number_of_splits;
    }

    public int get_number_of_doubles() {
        return number_of_doubles;
    }

    public int get_number_of_surrenders() {
        return number_of_surrenders;
    }

    public int get_number_of_hits() {
        return number_of_hits;
    }

    public int get_number_of_stands() {
        return number_of_stands;
    }

    public double get_avg_bet_size() {
        return avg_bet_size;
    }

    public void set_final_bankroll(double final_bankroll) {
        this.final_bankroll = final_bankroll;
    }

    public void set_bankroll_delta(double bankroll_delta) {
        this.bankroll_delta = bankroll_delta;
    }

    public void set_rounds_played(int rounds_played) {
        this.rounds_played = rounds_played;
    }

    public void set_hours_played(double hours_played) {
        this.hours_played = hours_played;
    }

    public void set_biggest_win(double biggest_win) {
        this.biggest_win = biggest_win;
    }

    public void set_biggest_loss(double biggest_loss) {
        this.biggest_loss = biggest_loss;
    }

    public void set_number_of_splits(int number_of_splits) {
        this.number_of_splits = number_of_splits;
    }

    public void set_number_of_doubles(int number_of_doubles) {
        this.number_of_doubles = number_of_doubles;
    }

    public void set_number_of_surrenders(int number_of_surrenders) {
        this.number_of_surrenders = number_of_surrenders;
    }

    public void set_number_of_hits(int number_of_hits) {
        this.number_of_hits = number_of_hits;
    }

    public void set_number_of_stands(int number_of_stands) {
        this.number_of_stands = number_of_stands;
    }

    public void set_avg_bet_size(double avg_bet_size) {
        this.avg_bet_size = avg_bet_size;
    }
}