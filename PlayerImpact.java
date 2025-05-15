/**
 * @Author Cid Maciel and Emmett Levine
 */
public class PlayerImpact {
    private String name;
    private double impact;

    /**
     * constructor, stores the player's name and impact
     * 
     * @param name
     * @param impact
     */
    public PlayerImpact(String name, double impact) {
        this.name = name;
        this.impact = impact;
    }
    
    /**
     * 
     * @return the name of the player
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return the impact of the player
     */
    public double getImpact() {
        return impact;
    }
}
