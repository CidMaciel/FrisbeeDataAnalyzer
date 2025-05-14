import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StatsFileParser {
    public static Map<String, StatList> parseFile(String filePath) throws IOException {
        Map<String, StatList> playerStats = new HashMap<>();
        int currentGame = 0;
        int currentPoint = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            br.readLine();
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Check if this is a game header line (e.g., "1,,,,,,,")
                if (values[0].trim().equals("**Game 1**")) {
                    currentGame = Integer.parseInt(values[0].trim());
                    continue;
                }

                // Check if this is a point header line (e.g., "1,1,,,,,,,0")
                if (values[1].matches("\\d+") && values[2].trim().isEmpty()) {
                    currentPoint = Integer.parseInt(values[1].trim());
                    continue;
                }
                
                // This must be a player stats line
                currentGame = Integer.parseInt(values[0].trim());
                String playerName = values[1].trim();
                boolean startedOnOffense = Integer.parseInt(values[2].trim()) == 1;
                int uncompletedThrows = parseIntWithDefault(values[3].trim(), 0);
                int completedThrows = parseIntWithDefault(values[4].trim(), 0);
                int catches = parseIntWithDefault(values[5].trim(), 0);
                int drops = parseIntWithDefault(values[6].trim(), 0);
                int forcedDs = parseIntWithDefault(values[7].trim(), 0);
                boolean lineWon = values.length > 8 && Integer.parseInt(values[8].trim()) == 1;
                
                GameStats stats = new GameStats(
                    playerName, 
                    currentGame, 
                    currentPoint,
                    startedOnOffense, 
                    uncompletedThrows, 
                    completedThrows,
                    catches, 
                    drops, 
                    forcedDs, 
                    lineWon
                );
                
                // Add to player's stat list
                playerStats.computeIfAbsent(playerName, k -> new StatList()).addGame(stats);
            }
        }
        return playerStats;
    }
    
    private static int parseIntWithDefault(String value, int defaultValue) {
        try {
            return value.trim().isEmpty() ? defaultValue : Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}