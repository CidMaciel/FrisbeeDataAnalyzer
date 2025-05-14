import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UltimateFrisbeeStatsTracker {
    private List<List<String>> pointLineups = new ArrayList<>();
    private List<Map<String, Map<Character, Integer>>> pointPlayerStats = new ArrayList<>(); 
    private List<Boolean> pointResults = new ArrayList<>(); 
    private List<Boolean> pointStartedOnOffense = new ArrayList<>();       
    private Scanner scanner;
    private HashMap<String, DoublyLinkedList> playerStats;
    private Map<String, Integer> teamStats;
    private Map<String, String> nameMapping; //allows us to access names regardless of case
    private List<String> players;
    private String opposingTeam;
    private String dateOfGame;
    private int gameToScore;
    private boolean startingOnOffense;
    private boolean isOnOffense;
    private int ourScore;
    private int theirScore;
    private boolean isHalfTime;
    private boolean isGameOver;
    
    // "currents" allow us to modify, update and access lines, actions, players, stats during game
    private List<String> currentLine;
    private List<String> currentPointActions;
    private List<String> currentPointPlayers;
    private List<String> currentPointStatTypes;

    public UltimateFrisbeeStatsTracker() {
        scanner = new Scanner(System.in);
        playerStats = new HashMap<>();
        teamStats = new HashMap<>();
        nameMapping = new HashMap<>();
        initializeTeamStats();
        players = new ArrayList<>();
        currentLine = new ArrayList<>();
        currentPointActions = new ArrayList<>();
        currentPointPlayers = new ArrayList<>();
        currentPointStatTypes = new ArrayList<>();
        ourScore = 0;
        theirScore = 0;
        isHalfTime = false;
        isGameOver = false;
    }

    private void initializeTeamStats() {
        teamStats.put("completedCatches", 0);
        teamStats.put("droppedCatches", 0);
        teamStats.put("completedThrows", 0);
        teamStats.put("unsuccessfulThrows", 0);
        teamStats.put("forcedTurnovers", 0);
        teamStats.put("assists", 0);
        teamStats.put("scores", 0);
    }

    public void startGame() {
        System.out.println("\n\nWelcome to Ultimate Frisbee Stats Tracker! Good luck in the game!");
        
        // before game starts, we need to know players and game details
        enterPlayersForGame();
        editRoster();
        enterGameDetails(); 
        
        // starting the game! this loop continues and plays points until the game is over
        while (!isGameOver) {
            playPoint();
            
            // Check if halftime; if halftime, diplays team and player stats and switch offense/defense depending on starting position
            if (!isHalfTime && ((gameToScore == 13 && (ourScore == 7 || theirScore == 7)) || 
                              (gameToScore == 15 && (ourScore == 8 || theirScore == 8)))) {
                isHalfTime = true;
                isOnOffense = !startingOnOffense;
                System.out.println("\n----- Halftime -----");
                displayHalftimeStats();
                displayPlayerRatings();
            }
            
            // Check if game is over; if game is over, diplay final team and player stats and player ratings
            if (ourScore == gameToScore || theirScore == gameToScore) {
                isGameOver = true;
                System.out.println("\n----- Game Over -----");
                displayFinalStats();
                displayPlayerRatings();
                displayFullPlayerStats();
                System.out.println("Final Score: Braineaters " + ourScore + " - " + theirScore + " " + opposingTeam);
            }
        }
    }
    private void enterPlayersForGame() {
        System.out.println("\nEnter the players for this game, separated by comma:");
        String input = scanner.nextLine();
        String[] playerNames = input.split(",");
    
        //for each player entered by user, update players, nameMapping, and playerStats with that name
        for (String playerName : playerNames) {
            String name = playerName.trim();
            if (!name.isEmpty()) {
                String player = name.toLowerCase(); 
                players.add(player);              
                nameMapping.put(player, name);     
                playerStats.put(player, new DoublyLinkedList());
            }
        }
    }
    
    private void editRoster() {
        while (true) {
            System.out.println("\nConfirm Roster:");
            if (players.isEmpty()) {
                System.out.println("No players added yet.");
            } else {
                //for the players in the players list, print their nameMapping name 
                for (String player : players) {
                    System.out.println(nameMapping.get(player));
                }
            }
    
            System.out.println("\nEnter 'add' to add players, 'remove' to delete players, or 'done' to continue:");
            String input = scanner.nextLine().trim().toLowerCase();
    
            if (input.equalsIgnoreCase("done")) {
                break;
            } else if (input.equalsIgnoreCase("add")) {
                System.out.println("Enter additional player names, separated by commas:");
                String[] newNames = scanner.nextLine().split(",");

                for (String newName : newNames) {
                    String name = newName.trim();
                    if (!name.isEmpty()) {
                        String player = name.toLowerCase();
    
                        //if players list already contains new player, alert user, else add player to players, nameMapping, playerStats
                        if (players.contains(player)) {
                            System.out.println("Player '" + name + "' already exists, so didn't add it to roster");
                        } else {
                            players.add(player);
                            nameMapping.put(player, name);
                            playerStats.put(player, new DoublyLinkedList());
                            System.out.println("Added: " + name);
                        }
                    }
                }
    
            } else if (input.equalsIgnoreCase("remove")) {
                System.out.println("Enter name of player to remove:");
                String name = scanner.nextLine().trim();
                String player = name.toLowerCase();
                //if the player exists in players, remove the player from players, nameMapping, and playerStats
                if (players.contains(player)) {
                    players.remove(player);
                    nameMapping.remove(player);
                    playerStats.remove(player);
                    System.out.println("Removed: " + name);
                } else {
                    System.out.println("Player not found.");
                }
    
            } else {
                //catch if something else is inputted besides valid given options
                System.out.println("Invalid option. Please enter 'add', 'remove', or 'done'");
            }
        }
    }
    

    private void enterGameDetails() {

        System.out.println("\nWhat is today's date? Please enter in M-D-Y form, ex. 09-20-2004; nothing besides the digits and hypens please!:");
        dateOfGame = scanner.nextLine().trim();

        System.out.println("\nEnter the name of the opposing team:");
        opposingTeam = scanner.nextLine().trim();
        
        System.out.println("\nGame to 13 or 15?");
        String scoreInput = scanner.nextLine().trim();
        

        
        gameToScore = Integer.parseInt(scoreInput);
        if (gameToScore != 13 && gameToScore != 15) {
            System.out.println("Error: Game score must be either 13 or 15. Please try again");
            enterGameDetails();
        }
           
        System.out.println("\nStarting on offense or defense?");
        startingOnOffense = scanner.nextLine().trim().equalsIgnoreCase("offense");
        
        //relay the key information of the game before it starts
        System.out.println("\nBraineaters vs " + opposingTeam);
        System.out.println("Game to " + gameToScore);
        String startingPosition;
        if (startingOnOffense) {
            startingPosition = "offense";
        } else {
            startingPosition = "defense";
        }
        System.out.println("Starting on " + startingPosition);        
    }

    private void playPoint() {
        //clears currents to prepare for point
        currentLine.clear();
        currentPointActions.clear();
        currentPointPlayers.clear();
        currentPointStatTypes.clear();
        
        System.out.println("\n----- New Point -----");
        System.out.println("Current Score: Braineaters " + ourScore + " - " + theirScore + " " + opposingTeam);
        String pointPosition;
        if (startingOnOffense) {
            pointPosition = "offense";
        } else {
            pointPosition = "defense";
        }
        System.out.println("We are on " + pointPosition);

        //track if on offense or defense for csv export
        pointStartedOnOffense.add(isOnOffense);
        
        getLineForPoint();

        //deep copy array list of lineup for the point for csv export
        pointLineups.add(new ArrayList<>(currentLine));
        
        boolean pointEnded = false;
        //while the point isnt over, user can input player-action codes, point won, point lost, or sub)
        while (!pointEnded) {
            System.out.println("\nEnter player-action code, 'point won', 'point lost', or 'sub'):");
            String input = scanner.nextLine().trim();
            
            //if we win the point, add one to our score,  prepare to be on defense, end point, and update player and team stats
            if (input.equalsIgnoreCase("point won")) {
                ourScore++;
                isOnOffense = false;
                pointEnded = true;
                processPointActions();
                //store won point and stats for csv export
                pointResults.add(true);
                storePointStats();
                
            //if we lose the point, add one to their score,  prepare to be on offense, end point, and update player and team stats
            } else if (input.equalsIgnoreCase("point lost")){
                theirScore++;
                isOnOffense = true;
                pointEnded = true;
                processPointActions();  
                //store lost point and stats for csv export
                pointResults.add(false);
                storePointStats();
                
            //if sub, make substitution
            } else if (input.equalsIgnoreCase("sub")) {
                makeSubstitution();
            } else {
            
            //otherwise record the action for the player given by the input of user
                recordAction(input);
            }
        }
        
        // prompt for us as we lead into the next point; allows for continuation to next point, altering of stats from current point, or indicating that game is over early
        boolean readyForNextPoint = false;
        while (!readyForNextPoint || !isGameOver) {
            System.out.println("\nEnter 1 if ready for next point; enter 2 if need to alter this point; enter 'game over' if the game is over.");
            String choice = scanner.nextLine().trim();

            //if 1, we are ready for next point and while loop breaks
            if (choice.equals("1")) {
                readyForNextPoint = true;

            //if 2, alter point actions as determined by user in alterPointActions method
            } else if (choice.equals("2")) {
                alterPointActions();
                //update stored stats for points after altered actions
                updatePointStats(pointPlayerStats.size()-1); 

            //if game over, game is over and display final stats, players stats and rankings
            } else if (choice.equalsIgnoreCase("game over")){
                isGameOver = true;
                displayFinalStats();
                displayFullPlayerStats();
                displayPlayerRatings();

            // Ask if the user wants to export to CSV; specific format to this CSV that allows it to be read and analyzed amongst stats from previous games
            System.out.println("\nDo you want to export stats to CSV? 'Yes' to export, 'no' to terminate program");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
                exportStatsToCSV();
            } else {
                System.out.println("\nSounds good, thank you for using the Ulitmate Frisbee Stats Tracker!");
            }
            }
        }
    }   

    private void storePointStats() {
        Map<String, Map<Character, Integer>> playerPointStats = new HashMap<>();
        //find player actions from this point to store in playerPointStats map
        for (int i = 0; i < currentPointActions.size(); i++) {
            String player = currentPointPlayers.get(i);
            char actionType = currentPointStatTypes.get(i).charAt(0);
            
            //if the player doesn't exist yet start a new hashmap for them in playerPointStats
            if (!playerPointStats.containsKey(player)) {
                playerPointStats.put(player, new HashMap<>());
                for (char c : new char[]{'c', 'd', 't', 'u', 'f', 'a', 's'}) {
                    playerPointStats.get(player).put(c, 0);
                }
            }
            //increment given stat 
            Map<Character, Integer> stats = playerPointStats.get(player);
            stats.put(actionType, stats.get(actionType) + 1);
    }
    //if a player has nothing happen for them in a line, add them with 0 at all values
    for (String player : currentLine) {
        if (!playerPointStats.containsKey(player)) {
            playerPointStats.put(player, new HashMap<>());
            for (char c : new char[]{'c', 'd', 't', 'u', 'f', 'a', 's'}) {
                playerPointStats.get(player).put(c, 0);
            }
        }
    }
    //store stats from this point in the larger pointPlayerStats
    pointPlayerStats.add(playerPointStats);
    }
    
    private void updatePointStats(int pointIndex){
        //if pointIndex in bounds, update temporary updatedStats hashmap 
        if (pointIndex >= 0 && pointIndex < pointPlayerStats.size()) {
            Map<String, Map<Character, Integer>> updatedStats = new HashMap<>();
        
        //populate updatedStats with players and initialized stats of 0
        for (String player : pointLineups.get(pointIndex)) {
            updatedStats.put(player, new HashMap<>());
            for (char c : new char[]{'c', 'd', 't', 'u', 'f', 'a', 's'}) {
                updatedStats.get(player).put(c, 0);
            }
        }

        //populate updatedStats with actual stats from point
        for (int i = 0; i < currentPointActions.size(); i++) {
            String player = currentPointPlayers.get(i);
            char actionType = currentPointStatTypes.get(i).charAt(0);
            Map<Character, Integer> stats = updatedStats.get(player);
            stats.put(actionType, stats.get(actionType) + 1);
        }
        
        //update stored stats for this point
        pointPlayerStats.set(pointIndex, updatedStats);
        }
    }

    private void getLineForPoint() {
        System.out.println("Enter the players on this line, separated by comma:");
        String[] lineNames = scanner.nextLine().split(",");
    
        currentLine.clear();
        List<String> tempLine = new ArrayList<>();
        
        // if more than 7 players given by user, try again
        if (lineNames.length > 7) {
            System.out.println("You must enter 7 players or fewer. Try again.");
            getLineForPoint();
            return;
        }
        //for the players in the list of given names, trim and make their names lower case for continuity
        for (String name : lineNames) {
            String player = name.trim().toLowerCase();
    
            // if user given player doesn't already exist in players list of the roster, try again
            if (!players.contains(player)) {
                System.out.println("Player '" + name + "' not found on roster. Try again.");
                getLineForPoint();
                return;
            }
            // if user given player already exists in that user given line (duplicate player), try again
            if (tempLine.contains(player)) {
                System.out.println("Duplicate player '" + name + "' in line. Try again.");
                getLineForPoint();
                return;
            }
            
            // otherwise, add the player to the tempLine
            tempLine.add(player);
        }
        // once line players make it pass tests and into tempLine, tempLine is set and can pass off players to currentLine
        currentLine.addAll(tempLine);
    
        System.out.println("\nCurrent Line:");
        for (int i = 0; i < currentLine.size(); i++) {
            System.out.println((i + 1) + ". " + nameMapping.get(currentLine.get(i)));
        }
    }
    
    private void makeSubstitution() {
        System.out.println("Enter player number to substitute (1-" + currentLine.size() + "):");
        int playerNumber = Integer.parseInt(scanner.nextLine().trim());
        
        //if user gives a number that is not 1- current line size (likely 7), try again
        if (playerNumber < 1 || playerNumber > currentLine.size()) {
            System.out.println("Invalid player number. Please enter a number between 1 and 7 (or <7 if line size smaller)");
            makeSubstitution();
            return;
        }
        
        System.out.println("Enter name of substitute player:");
        String subName = scanner.nextLine().trim();
        String lcsubName = subName.toLowerCase();
        
        //validate that substitute player is on the roster and show that they are now substitude into the line
        if (players.contains(lcsubName)) {
            System.out.println("Substituting " + nameMapping.get(currentLine.get(playerNumber - 1)) + " with " + nameMapping.get(lcsubName));
            currentLine.set(playerNumber - 1, lcsubName);
            
            //display the updated line
            System.out.println("\nUpdated Line:");
            for (int i = 0; i < currentLine.size(); i++) {
                System.out.println((i + 1) + ". " + nameMapping.get(currentLine.get(i)));
            }
            //otherwise, subName not found on roster and we move on
        } else {
            System.out.println("Substitute player not found on roster.");
        }
    }

    private void recordAction(String input) {
        //if more than 2 characters, invalid
        if (input.length() < 2) {
            System.out.println("Invalid input. Please use format 'player#' 'action', ex. '1f' or '2c', as described in the readme");
            return;
        }
        
        try {
            int playerIndex = Character.getNumericValue(input.charAt(0)) - 1;
            char action = input.charAt(1);
            //if player index given by user is outside of line size, invalid
            if (playerIndex < 0 || playerIndex >= currentLine.size()) {
                System.out.println("Invalid player number. Please enter a number between 1 and 7 (or <7 if line size smaller)");
                return;
            }
            
            String playerName = currentLine.get(playerIndex);
            String actionDescription = getActionDescription(action);
            
            //if no given actionDescription, invalid
            if (actionDescription == null) {
                System.out.println("Invalid action code. Valid codes: d, c, t, u, f, a, s");
                return;
            }
            
            //add action to current point actions
            currentPointActions.add(actionDescription);
            currentPointPlayers.add(playerName);
            currentPointStatTypes.add(String.valueOf(action));
            
            //display the action with its sequence number in the point
            System.out.println(currentPointActions.size() + ". " + nameMapping.get(playerName) + " " + actionDescription);
        
            //catch for if 2 characters but doesn't follow the given format
        } catch (Exception e) {
            System.out.println("Error recording action: " + e.getMessage());
        }
    }

    private String getActionDescription(char action) {
        switch (action) {
            case 'd': return "Dropped Catch";
            case 'c': return "Completed Catch";
            case 't': return "Completed Throw";
            case 'u': return "Unsuccessful Throw";
            case 'f': return "Forced D";
            case 'a': return "Assist";
            case 's': return "Score";
            default: return null;
        }
    }

    private void processPointActions() {
        //process all actions for this point and update player and team stats
        for (int i = 0; i < currentPointActions.size(); i++) {
            String player = currentPointPlayers.get(i);
            String actionType = currentPointStatTypes.get(i);
            
            //update team stats
            updateTeamStats(actionType);
            
            //update player stats
            StatNode statNode = new StatNode(actionType.charAt(0));
            playerStats.get(player).add(statNode);
        }
    }

    private void updateTeamStats(String actionType) {
        switch (actionType.charAt(0)) {
            case 'd':
                teamStats.put("droppedCatches", teamStats.get("droppedCatches") + 1);
                break;
            case 'c':
                teamStats.put("completedCatches", teamStats.get("completedCatches") + 1);
                break;
            case 't':
                teamStats.put("completedThrows", teamStats.get("completedThrows") + 1);
                break;
            case 'u':
                teamStats.put("unsuccessfulThrows", teamStats.get("unsuccessfulThrows") + 1);
                break;
            case 'f':
                teamStats.put("forcedTurnovers", teamStats.get("forcedTurnovers") + 1);
                break;
            case 'a':
                //assists also count as completed throws, so update that as well
                teamStats.put("assists", teamStats.get("assists") + 1);
                teamStats.put("completedThrows", teamStats.get("completedThrows") + 1);
                break;
            case 's':
                //scores also count as completed catches, so update that as well
                teamStats.put("scores", teamStats.get("scores") + 1);
                teamStats.put("completedCatches", teamStats.get("completedCatches") + 1);
                break;
        }
    }

    private void alterPointActions() {
        System.out.println("\nCurrent point actions:");
        //print out all of the actions from the point in order
        for (int i = 0; i < currentPointActions.size(); i++) {
            System.out.println((i + 1) + ". " + currentPointPlayers.get(i) + " " + currentPointActions.get(i));
        }
        
        System.out.println("\nEnter action number to alter, or 'i' to insert an action, or 'd' to delete an action:");
        String input = scanner.nextLine().trim();
        
        if (input.equalsIgnoreCase("i")) {
            insertAction();
        } else if (input.equalsIgnoreCase("d")) {
            deleteAction();
        } else {
            try {
                int actionIndex = Integer.parseInt(input) - 1;
                //if action index is in bounds, ask user for new action and update index with new action
                if (actionIndex >= 0 && actionIndex < currentPointActions.size()) {
                    System.out.println("Enter new action for " + currentPointPlayers.get(actionIndex) + ":");
                    String newAction = scanner.nextLine().trim();
                    updateAction(actionIndex, newAction);
                } else {
                    System.out.println("Invalid action number");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }
    }
    
    private void insertAction() {
        System.out.println("Enter action number to insert after (0 for beginning):");
        int insertPoint = Integer.parseInt(scanner.nextLine().trim());
        //if user input not in bounds, invalid
        if (insertPoint < 0 || insertPoint > currentPointActions.size()) {
            System.out.println("Invalid action number");
            return;
        }
        //prompt user for new action
        System.out.println("Enter new action (ex.'1c'):");
        String input = scanner.nextLine().trim();
        
        //if input length not 2, the proper format, invalid
        if (input.length() != 2) {
            System.out.println("Invalid input. Please use format 'player#' 'action', ex. '1f' or '2c', as described in the readme");
            return;
        }
        
        try {
            //set playerIndex and action based on user input
            int playerIndex = Character.getNumericValue(input.charAt(0)) - 1;
            char action = input.charAt(1);
            // if player index out of bounds, invalid
            if (playerIndex < 0 || playerIndex >= currentLine.size()) {
                System.out.println("Invalid player number");
                return;
            }
            
            String playerName = currentLine.get(playerIndex);
            String actionDescription = getActionDescription(action);
            
            
            //update action, players, and stats based on user input
            currentPointActions.add(insertPoint, actionDescription);
            currentPointPlayers.add(insertPoint, playerName);
            currentPointStatTypes.add(insertPoint, String.valueOf(action));
            
            
            System.out.println("\nUpdated point actions:");
            //show the point actions with the applied updates
            for (int i = 0; i < currentPointActions.size(); i++) {
                System.out.println((i + 1) + ". " + currentPointPlayers.get(i) + " " + currentPointActions.get(i));
            }
            
        } catch (Exception e) {
            System.out.println("Error inserting action: " + e.getMessage());
        }
    }
    
    private void deleteAction() {
        System.out.println("Enter action number to delete:");
        int actionIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
        //if index given is in bounds, remove action from actions, players, and stats
        if (actionIndex >= 0 && actionIndex < currentPointActions.size()) {
            currentPointActions.remove(actionIndex);
            currentPointPlayers.remove(actionIndex);
            currentPointStatTypes.remove(actionIndex);
            
            System.out.println("\nUpdated point actions:");
            //display points' actions including the updated point actions
            for (int i = 0; i < currentPointActions.size(); i++) {
                System.out.println((i + 1) + ". " + currentPointPlayers.get(i) + " " + currentPointActions.get(i));
            }
        } else {
            System.out.println("Invalid action number.");
        }
    }
    
    private void updateAction(int actionIndex, String newAction) {
        if (newAction.length() != 2) {
            System.out.println("Invalid input. Please use format 'player#' 'action', ex. '1f' or '2c', as described in the readme");
            return;
        }
        
        try {
            int playerIndex = Character.getNumericValue(newAction.charAt(0)) - 1;
            char action = newAction.charAt(1);
            //if playerIndex given by user is out of bounds, invalid
            if (playerIndex < 0 || playerIndex >= currentLine.size()) {
                System.out.println("Invalid player number.");
                return;
            }
            //now that we checked index for validity, set playerName and actionDescription
            String playerName = currentLine.get(playerIndex);
            String actionDescription = getActionDescription(action);
            
            
            // update action in actions, players, and stats
            currentPointActions.set(actionIndex, actionDescription);
            currentPointPlayers.set(actionIndex, playerName);
            currentPointStatTypes.set(actionIndex, String.valueOf(action));
            
            System.out.println("\nUpdated point actions:");
            // display points' actions including new actions
            for (int i = 0; i < currentPointActions.size(); i++) {
                System.out.println((i + 1) + ". " + currentPointPlayers.get(i) + " " + currentPointActions.get(i));
            }
            
        } catch (Exception e) {
            System.out.println("Error updating action: " + e.getMessage());
        }
    }

    private void displayHalftimeStats() {
        System.out.println("\n----- Halftime Stats -----");
        System.out.println("Score: Braineaters " + ourScore + " - " + theirScore + " " + opposingTeam);
        displayTeamStats();
    }

    private void displayFinalStats() {
        System.out.println("\n----- Final Game Stats -----");
        displayTeamStats();
    }

    private void displayFullPlayerStats() {
        System.out.println("\n----- Full Player Stats -----");
        //for all of the players, print their stats
        for (String player : players) {
            String name = nameMapping.get(player);
            DoublyLinkedList stats = playerStats.get(player);
            System.out.println("\n" + name + ":");
            Map<Character, Integer> statCounts = new HashMap<>();
            //make new HashMap statcounts to store action codes
            for (char c : new char[]{'c', 'd', 't', 'u', 'f', 'a', 's'}) {
                statCounts.put(c, 0);
            }
            //populate new HashMap with the stats collected over the game
            for (char stat : stats.getAllStats()) {
                statCounts.put(stat, statCounts.get(stat) + 1);
            }
          
            System.out.println("  Completed Catches:     " + (statCounts.get('c')  + statCounts.get('s')));
            System.out.println("  Dropped Catches:       " + statCounts.get('d'));
            System.out.println("  Completed Throws:      " + (statCounts.get('t') + statCounts.get('a')));
            System.out.println("  Unsuccessful Throws:   " + statCounts.get('u'));
            System.out.println("  Forced D's:            " + statCounts.get('f'));
            System.out.println("  Assists:               " + statCounts.get('a'));
            System.out.println("  Scores:                " + statCounts.get('s'));
        }
    }
    

    private void displayPlayerRatings() {
        System.out.println("\nPlayer Ratings:");
        //for all players, retrieve stats per player from playerStats HashMap, then calculate and print their player ratings one at a time
        for (String player : players) {
            DoublyLinkedList stats = playerStats.get(player); 
        int rating = 0;
        rating += 1 * stats.countStatType('c');
        rating += 1 * stats.countStatType('t');
        rating += 1 * stats.countStatType('f');
        rating -= 1 * stats.countStatType('d');
        rating -= 1 * stats.countStatType('u');
        rating += 3 * stats.countStatType('a');
        rating += 3 * stats.countStatType('s');
        System.out.println(nameMapping.get(player) + " rating: " + rating);
    }
}

    private void displayTeamStats() {
        System.out.println("Team Stats:");
        System.out.println("Completed Catches: " + teamStats.get("completedCatches"));
        System.out.println("Dropped Catches: " + teamStats.get("droppedCatches"));
        System.out.println("Completed Throws: " + teamStats.get("completedThrows"));
        System.out.println("Unsuccessful Throws: " + teamStats.get("unsuccessfulThrows"));
        System.out.println("Forced Turnovers: " + teamStats.get("forcedTurnovers"));
        System.out.println("Assists: " + teamStats.get("assists"));
        System.out.println("Scores: " + teamStats.get("scores"));
        
        int totalThrows = teamStats.get("completedThrows") + teamStats.get("unsuccessfulThrows");
        double throwCompletionRate;
        //if more than 1 throw, get teamStats of completedThrows * 100 divided by total throws to find throwCompletionRate
        if (totalThrows > 0) {
            throwCompletionRate = teamStats.get("completedThrows") * 100.0 / totalThrows;
        } else {
            throwCompletionRate = 0;
        }

        int totalCatches = teamStats.get("completedCatches") + teamStats.get("droppedCatches");
        double catchCompletionRate;
         //if more than 1 catch, get teamStats of completedCatches * 100 divided by total throws to find catchCompletionRate
        if (totalCatches > 0) {
            catchCompletionRate = teamStats.get("completedCatches") * 100.0 / totalCatches;
        } else {
            catchCompletionRate = 0;
        }

        System.out.println("Throw Completion Rate: " + throwCompletionRate + "%");
        System.out.println("Catch Completion Rate: " + catchCompletionRate + "%");

    }

    private void exportStatsToCSV() {
        try {
            String filename = "Braineatersvs" + opposingTeam + "_" + dateOfGame + ".csv";
            FileWriter fileWriterStatsToCSV = new FileWriter(filename);

            //title line which designates the categories of recorded data
            fileWriterStatsToCSV.append("Game Number,Point in Game by Player,\"Started on offense/defense (O = 1, D = 0)\",# of Uncompleted Throws,# of Completed Throws,# of Catches,# of Drops (hits body but not completed catch) ,# of Forced Ds (stalled out opponent or block),\"Line won (T = 1, F = 0)\"\n");

            //for each point in the game, retrieve pointWon, startedOnOffense, players, and stats
            for (int pointIndex = 0; pointIndex < pointResults.size(); pointIndex++) {
            boolean pointWon = pointResults.get(pointIndex);
            boolean startedOnOffense = pointStartedOnOffense.get(pointIndex);
            List<String> lineup = pointLineups.get(pointIndex);
            Map<String, Map<Character, Integer>> pointStats = pointPlayerStats.get(pointIndex);
            
            //write which point it is and if we won the point in the left and rightmost columns, respectively
            fileWriterStatsToCSV.append("0,");
            fileWriterStatsToCSV.append(String.valueOf(pointIndex + 1));
            fileWriterStatsToCSV.append(",,,,,,," + (pointWon ? "1" : "0") + "\n");
            
            // Write player rows for this point
            for (String player : lineup) {
                Map<Character, Integer> stats = pointStats.get(player);
                
                if (stats == null) {
                    // If no stats for this player (should not happen), use empty stats
                    stats = new HashMap<>();
                    for (char c : new char[]{'c', 'd', 't', 'u', 'f'}) {
                        stats.put(c, 0);
                    }
                }
                //add each player's stat across the row for their point
                fileWriterStatsToCSV.append("0,");
                fileWriterStatsToCSV.append(nameMapping.get(player) + ",");
                fileWriterStatsToCSV.append(startedOnOffense ? "1," : "0,");
                fileWriterStatsToCSV.append(stats.getOrDefault('u', 0) + ",");
                fileWriterStatsToCSV.append((stats.getOrDefault('t', 0) + stats.getOrDefault('a', 0)) + ",");
                fileWriterStatsToCSV.append((stats.getOrDefault('c', 0) + stats.getOrDefault('s', 0)) + ",");
                fileWriterStatsToCSV.append(stats.getOrDefault('d', 0) + ",");
                fileWriterStatsToCSV.append(stats.getOrDefault('f', 0) + ",");
       
                fileWriterStatsToCSV.append((pointWon ? "1" : "0") + "\n");
            }
        }
        
        fileWriterStatsToCSV.close();
        
        System.out.println("Stats exported successfully to " + filename);
        
    } catch (IOException e) {
        System.out.println("Error exporting stats: " + e.getMessage());
    }
}
        
            

    public static void main(String[] args) {
        UltimateFrisbeeStatsTracker tracker = new UltimateFrisbeeStatsTracker();
        tracker.startGame();
    }

    class StatNode {
        //stores type of stat, represented by the characters
        private char statType; 
        private StatNode prev;
        private StatNode next;
        
        //constructor for new stat entry
        public StatNode(char statType) {
            this.statType = statType;
            this.prev = null;
            this.next = null;
        }
        
        public char getStatType() {
            return statType;
        }
    }
    
    class DoublyLinkedList {
        private StatNode head;
        private StatNode tail;
        private int size;

        public List<Character> getAllStats() {
            List<Character> stats = new ArrayList<>();
            StatNode current = head;
            //adds stats in order to arraylist stats
            while (current != null) {
                stats.add(current.getStatType());
                current = current.next;
            }
            return stats;
        }
        
        //constructor for new empty stats list
        public DoublyLinkedList() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public void add(StatNode node) {
           //if list empty, new node is head and tail, otherwise add to end of list
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
            size++;
        }
        
        public int getSize() {
            return size;
        }
                
        public int countStatType(char statType) {
            int count = 0;
            StatNode current = head;
            while (current != null) {
                //if the stat is of the observed type, increment count
                if (current.getStatType() == statType) {
                    count++;
                }
                current = current.next;
            }
            
            return count;
        }
    }
}
