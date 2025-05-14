
/** 
 * class that represents the stata from each game for each player
 * @Author Cid Maciel and Emmett Levine
 */
public class GameStats {
    private final String playerName;
    private final int gameNumber;
    private final int pointInGame;
    private final boolean startedOnOffense;
    private final int uncompletedThrows;
    private final int completedThrows;
    private final int catches;
    private final int drops;
    private final int forcedDs;
    private final boolean lineWon;
    private final int plusMinus;
    

    /**
     * constructor for GameStats, takes each stat and a name as input and creates a GameStat
     * 
     * @param playerName 
     * @param gameNumber
     * @param pointInGame
     * @param startedOnOffense
     * @param uncompletedThrows
     * @param completedThrows
     * @param catches
     * @param drops
     * @param forcedDs
     * @param lineWon
     */
    public GameStats(String playerName, int gameNumber, int pointInGame, boolean startedOnOffense,
    int uncompletedThrows, int completedThrows, int catches, int drops, int forcedDs, boolean lineWon) {
        this.playerName = playerName;
        this.gameNumber = gameNumber;
        this.pointInGame = pointInGame;
        this.startedOnOffense = startedOnOffense;
        this.uncompletedThrows = uncompletedThrows;
        this.completedThrows = completedThrows;
        this.catches = catches;
        this.drops = drops;
        this.forcedDs = forcedDs;
        this.lineWon = lineWon;
        this.plusMinus = calculatePlusMinus();

    }

    /**
     * returns the calculation of a player's plus minus
     * 
     * @return an int equal to the player's plus minus
     */
    private int calculatePlusMinus() {
        return completedThrows + catches + forcedDs - uncompletedThrows - drops;
    }

    // getters for GameStats
    public String getPlayerName() {return playerName;}
    public int getGameNumber() {return gameNumber;}
    public int getPointInGame() {return pointInGame;}
    public boolean isStartedOnOffense() { return startedOnOffense; }
    public int getUncompletedThrows() { return uncompletedThrows; }
    public int getCompletedThrows() { return completedThrows; }
    public int getCatches() { return catches; }
    public int getDrops() { return drops; }
    public int getForcedDs() { return forcedDs; }
    public boolean isLineWon() { return lineWon; }
    public int getPlusMinus() { return plusMinus; }
}

