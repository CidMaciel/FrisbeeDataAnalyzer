import java.util.Collection;
import java.util.List;

public interface PlayerStatsHashMap<Player, Stats> {
    
    void put(Player player, Stats stats);
    List<Stats> getStats(Player player);
    void removePlayer(Player player);
    boolean containsPlayer(Player player);
    int totalPlayers();

    void addStat(Player player, Stats stats);
    void removeLatestStat(Player player);
    void mergeStats(Player player, List<Stats> additionalStats);

    Collection<Stats> getAllStatsByType(String type);
    double getStatTrend(Player player, String type);

    List<Stats> getStatsForGame(Player player);
    Integer calculatePlusMinus(Player player);
    Player findImpactfulnessTrend (Player player);

    void cleanOldStats(int maxStatsPerPlayer);
    void resize(int newCapacity);
}
