import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * Custom doubly linked list to store player stats for each game
 */
public class StatList {
    private StatNode head;
    private StatNode tail;
    private int size;
    
    // Inner node class
    private class StatNode {
        GameStats stats;
        StatNode prev;
        StatNode next;
        
        StatNode(GameStats stats) {
            this.stats = stats;
        }
    }
    
    // Public API methods
    public void addGame(GameStats stats) {
        StatNode newNode= new StatNode(stats);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size ++;
    }
    
    public int getSize() {
        return size;
    }
    
    public GameStats getGame(int index) {

    }
    public double averageStat(String statName) {
        if (size == 0) { return 0.0; }

        double total = 0.0;
        StatNode current = head;

        while (current != null) {
            switch (statName.toLowerCase()) {
                case "completed throws" :
                    total += current.stats.getCompletedThrows();
                    break;
                case "uncompleted throws":
                    total += current.stats.getUncompletedThrows();
                    break;
                case "catches":
                    total += current.stats.getCatches();
                    break;
                case "drops":
                    total += current.stats.getDrops();
                    break;
                case "forced ds":
                    total += current.stats.getForcedDs();
                    break;
                case "plusminus":
                    total += current.stats.getPlusMinus();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown stat: " + statName);
            }
            current = current.next;
            }
        return total/size;
    }
    public List<GameStats> getGamesInPeriod(LocalDate start, LocalDate end) {
        List<GameStats> result = new ArrayList<>();
        StatNode current = head;
    }
    public int size() { return size; }
}
