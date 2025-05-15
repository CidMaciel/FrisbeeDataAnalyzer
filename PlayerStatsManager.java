import java.util.List;
import java.util.Map;

/**
 * interface for managing and analyzing Ultimate Frisbee player statistics
 */
public interface PlayerStatsManager {
    
    // core statistics methods
    double averagePlayerStat(String playerName, String statName);
    Map<String, Double> calculateTeamTrends(String statName, int lastNGames);
    Map<String, Integer> calculateAllPlusMinus();
    List<String> getMostImpactfulPlayers(int minGames);
    Map<String, List<String>> recommendLines(int playersPerLine);
    
    // data access methods
    Map<String, StatList> getPlayerStats();
    int getTotalGames();
    
    // utility methods
    double getStatValue(GameStats game, String statName);
    
}