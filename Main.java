import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive Ultimate Frisbee Stats Analyzer
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            // Load data
            System.out.println("Loading player stats data...");
            Map<String, StatList> playerStats = StatsFileParser.parseFile("data/Rook_Snooze_Full_Data.csv");
            FrisbeeStatsAnalyzer analyzer = new FrisbeeStatsAnalyzer(playerStats);
            
            // Main menu loop
            while (true) {
                System.out.println("\n=== Ultimate Frisbee Stats Analyzer ===");
                System.out.println("1. View player averages");
                System.out.println("2. Analyze team trends");
                System.out.println("3. View plus-minus ratings");
                System.out.println("4. Find most impactful players");
                System.out.println("5. Exit");
                System.out.print("Select an option (1-5): ");
                
                int choice = getIntInput(1, 5);
                
                switch (choice) {
                    case 1:
                        analyzePlayerAverages(analyzer);
                        break;
                    case 2:
                        analyzeTeamTrends(analyzer);
                        break;
                    case 3:
                        viewPlusMinus(analyzer);
                        break;
                    case 4:
                        findImpactfulPlayers(analyzer);
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        return;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data file: " + e.getMessage());
        }
    }
    
    private static void analyzePlayerAverages(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== Player Averages ===");
        System.out.println("Available stats: completed throws, uncompleted throws, catches, drops, forced ds, plusminus, assist, score");
        System.out.print("Enter stat to analyze: ");
        String stat = scanner.nextLine().toLowerCase();
        
        System.out.println("\nResults:");
        for (String player : analyzer.getPlayerStats().keySet()) {
            double avg = analyzer.averagePlayerStat(player, stat);
            System.out.printf("%s: %.2f %s%n", player, avg, stat);
        }
    }
    
    private static void analyzeTeamTrends(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== Team Trends ===");
        System.out.println("Available stats: completed throws, uncompleted throws, catches, drops, forced ds, plusminus, assist, score");
        System.out.print("Enter stat to analyze: ");
        String stat = scanner.nextLine().toLowerCase();
        
        System.out.print("Enter number of recent games to consider: ");
        int games = getIntInput(1, analyzer.getTotalGames());
        
        System.out.println("\nResults (last " + games + " games):");
        Map<String, Double> trends = analyzer.calculateTeamTrends(stat, games);
        trends.forEach((player, avg) -> System.out.printf("%s: %.2f %s%n", player, avg, stat));
    }
    
    private static void viewPlusMinus(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== Player Plus-Minus Ratings ===");
        Map<String, Integer> plusMinus = analyzer.calculateAllPlusMinus();
        plusMinus.forEach((player, pm) -> System.out.printf("%s: %+d%n", player, pm));
    }
    
    private static void findImpactfulPlayers(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== Most Impactful Players ===");
        System.out.print("Enter minimum games played: ");
        int minGames = getIntInput(1, analyzer.getTotalGames());
        
        System.out.println("\nResults (min " + minGames + " games):");
        List<String> players = analyzer.getMostImpactfulPlayers(minGames);
        players.forEach(System.out::println);
    }
    
    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}