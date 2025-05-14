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
    private StatNode prev;
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
    
    /**
     * adds a new GameStats game to the StatList
     * 
     * @param stats the stats to be added
     */
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
    
    /**
     * 
     * @return the size of the StatList
     */
    public int getSize() {
        return size;
    }
    
    /**
     * finds the average stat for the team for a specified stat type
     * 
     * @param statName name of stat
     * @return total stats under that name
     */
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

    /**
     * returns all games in a certain period
     * 
     * @param start first game
     * @param end last game
     * @return all games within this time period
     */
    public List<GameStats> getGamesInPeriod(int start, int end) {
        List<GameStats> result = new ArrayList<>();
        
        StatNode current = head;
        while (current != null) {
            if (current.stats.getGameNumber() >= start && 
                current.stats.getGameNumber() <= end) {
                result.add(current.stats);
            }
            current = current.next;
        }
        return result;
    }
    
    public int calculateTotalPlusMinus() {
        int total = 0;
        StatNode current = head;
        
        while (current != null) {
            total += current.stats.getPlusMinus();
            current = current.next;
        }
        
        return total;
    }


}
