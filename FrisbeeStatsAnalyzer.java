import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class FrisbeeStatsAnalyzer {
    private final Map<String, StatList> playerStats;
    private int totalGames;

    public FrisbeeStatsAnalyzer(Map<String, StatList> playerStats) {
        this.playerStats = playerStats;
        this.totalGames = calculateTotalGames();


    }

    /**
     * Made to calculate the total games that were played
     * 
     * @return total number of games played
     */
    int calculateTotalGames() {
        int maxGame = 0;
        for (StatList stats : playerStats.values()) {
            // assuming the last game in any player's list is the total, iterates through the stats.
            if (stats.getSize() > 0) {
                GameStats lastGame = stats.getGamesInPeriod(0, stats.getSize() - 1).get(maxGame);
                maxGame = Math.max(maxGame, lastGame.getGameNumber());
            }
        }
        return maxGame;
    }

    /**
     * Finds the total stat of a certain stat for a specified player
     * 
     * @param playerName name of a player
     * @param statName the stat to be analyzed
     * @return
     */
    double averagePlayerStat (String playerName, String statName) {
        
        // if the players name is not in the map, throws an exception
        if (!playerStats.containsKey(playerName)) {
            throw new IllegalArgumentException("Player not found: " + playerName);
        }

        // gets the players average stat using the averageStat function from GameStats
        return playerStats.get(playerName).averageStat(statName);
    }

    public Map<String, Double> calculateTeamTrends(String statName, int lastNGames) {
        Map<String, Double> trends = new HashMap<>();
        
        if (playerStats.isEmpty() || lastNGames <= 0) {
            return trends; // Return empty for invalid inputs
        }
        
        int startGame = Math.max(1, totalGames - lastNGames + 1);
        
        for (Map.Entry<String, StatList> entry : playerStats.entrySet()) {
            List<GameStats> recentGames = entry.getValue().getGamesInPeriod(startGame, totalGames);
            
            if (!recentGames.isEmpty()) {
                double sum = 0.0;
                int count = 0;
                
                for (GameStats game : recentGames) {
                    try {
                        sum += getStatValue(game, statName);
                        count++;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid stat for " + entry.getKey() + ": " + e.getMessage());
                    }
                }
                
                if (count > 0) {
                    trends.put(entry.getKey(), sum / count);
                }
            }
        }
        
        return trends;
    }

    public Map<String, Integer> calculateAllPlusMinus() {
        Map<String, Integer> plusMinusMap = new HashMap<>();
        
        for (Map.Entry<String, StatList> entry : playerStats.entrySet()) {
            plusMinusMap.put(entry.getKey(), entry.getValue().calculateTotalPlusMinus());
        }
        
        return plusMinusMap;
    }

    public List<String> getMostImpactfulPlayers(int minGames) {
        List<PlayerImpact> impactList = new ArrayList<>();
        
        for (Map.Entry<String, StatList> entry : playerStats.entrySet()) {
            if (entry.getValue().getSize() >= minGames) {
                double impact = (double) entry.getValue().calculateTotalPlusMinus() / entry.getValue().getSize();
                impactList.add(new PlayerImpact(entry.getKey(), impact));
            }
        }
        
        impactList.sort((a, b) -> Double.compare(b.getImpact(), a.getImpact()));
        
        List<String> result = new ArrayList<>();
        for (PlayerImpact pi : impactList) {
            result.add(pi.getName());
        }
        
        return result;
    }

    /**
    * Recommends optimal offensive and defensive lines based on available stats
    * @param playersPerLine Number of players needed per line (typically 7)
    * @return Map containing "Offensive Line" and "Defensive Line" lists
    */
    public Map<String, List<String>> recommendLines(int playersPerLine) {
        Map<String, List<String>> lines = new HashMap<>();
    
        // Get all qualified players (minimum 3 games)
        List<String> players = playerStats.keySet().stream()
            .filter(p -> playerStats.get(p).getSize() >= 3)
            .collect(Collectors.toList());
    
        if (players.isEmpty()) return lines;
    
        // Offensive score weights: 50% completions, 30% catches, 20% plus-minus
        List<String> offenseRank = rankPlayers(players, 
            p -> playerStats.get(p).averageStat("completed throws") * 0.5 +
             playerStats.get(p).averageStat("catches") * 0.3 +
             playerStats.get(p).averageStat("plusminus") * 0.2 -
             playerStats.get(p).averageStat("drops") * 0.2);
    
        // Defensive score weights: 60% forced Ds, 40% plus-minus
        List<String> defenseRank = rankPlayers(players, 
        p -> playerStats.get(p).averageStat("forced ds") * 0.6 +
             playerStats.get(p).averageStat("plusminus") * 0.4);
    
        // Build lines ensuring no player is on both
        lines.put("Offensive Line", offenseRank.stream()
        .limit(playersPerLine)
        .collect(Collectors.toList()));
    
        lines.put("Defensive Line", defenseRank.stream()
        .filter(p -> !lines.get("Offensive Line").contains(p))
        .limit(playersPerLine)
        .collect(Collectors.toList()));
    
        return lines;
    }

    private List<String> rankPlayers(List<String> players, ToDoubleFunction<String> scorer) {
        return players.stream()
        .sorted(Comparator.comparingDouble(scorer).reversed())
        .collect(Collectors.toList());
    }

    private double getStatValue(GameStats game, String statName) {
        switch (statName.toLowerCase()) {
            case "completed throws": return game.getCompletedThrows();
            case "uncompleted throws": return game.getUncompletedThrows();
            case "catches": return game.getCatches();
            case "drops": return game.getDrops();
            case "forced ds": return game.getForcedDs();
            case "plusminus": return game.getPlusMinus();
            default: throw new IllegalArgumentException("Unknown stat: " + statName);
        }
    }

    public Map<String, StatList> getPlayerStats() {
        return playerStats;
    }
    
    public int getTotalGames() {
        return totalGames;
    }


}
