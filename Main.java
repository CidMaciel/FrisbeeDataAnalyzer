import java.io.IOException;
import java.util.Map;
import java.util.List;

/**
 * Main class to run the analysis
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Load data
            Map<String, StatList> playerStats = StatsFileParser.parseFile("data/Rook_Snooze_Full_Data.csv");
            FrisbeeStatsAnalyzer analyzer = new FrisbeeStatsAnalyzer(playerStats);
            
            // Example usage of features
            System.out.println("=== Average Completed Throws ===");
            for (String player : playerStats.keySet()) {
                double avg = analyzer.averagePlayerStat(player, "completed throws");
                System.out.printf("%s: %.2f%n", player, avg);
            }
            
            System.out.println("\n=== Team Trends (Last 5 Games) ===");
            Map<String, Double> trends = analyzer.calculateTeamTrends("plusminus", 5);
            trends.forEach((player, avg) -> System.out.printf("%s: %.2f%n", player, avg));
            
            System.out.println("\n=== Player Plus-Minus ===");
            Map<String, Integer> plusMinus = analyzer.calculateAllPlusMinus();
            plusMinus.forEach((player, pm) -> System.out.printf("%s: %d%n", player, pm));
            
            System.out.println("\n=== Most Impactful Players (min 5 games) ===");
            List<String> impactful = analyzer.getMostImpactfulPlayers(5);
            impactful.forEach(System.out::println);
            
        } catch (IOException e) {
            System.err.println("Error loading data file: " + e.getMessage());
        }
    }
}
