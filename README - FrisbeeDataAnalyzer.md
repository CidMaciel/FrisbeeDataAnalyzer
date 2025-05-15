# FrisbeeDataAnalyzer
Welcome to FrisbeeDataAnalyzer, a data analyzer for ultimate frisbee teams! With this program, you can input live stats as well as read from a csv of stats to recieve a large amont of information such as the most impactful players, player plus-minus, overall team trends, and even suggesting new lines.

## One-Paragraph Description

FrisbeeDataAnalyzer is meant to be used to caclulate many different aspects about a team's performance. When you run main, you will be asked to input a csv file name, and choose what stats you want to be calculated. After you have chosen your inputs, the program will calculate five items: the average team stat of the user's choice, team trends in a specified period, the most impactful players, and the suggested new line. You can choose which operation you would like to use, and what you would like to view. Here is an example of the program running:

<img width="304" alt="Screenshot 2025-05-14 at 10 46 57 AM" src="https://github.com/user-attachments/assets/d696faaa-6896-48d2-96c6-d845ae9a4fa1" />

And here is the result when you select 1 and choose drops:

<img width="653" alt="Screenshot 2025-05-14 at 10 48 57 AM" src="https://github.com/user-attachments/assets/3c764a1b-045c-43cf-baea-45329b54e287" />


## Usage

There are several methods within the FrisbeeDataAnalyzer project. I will begin by going over GameStats, the class in which we store the stats for each individual player.

# Main class

### The main class is where all code should be run. If you run the main class, this will show you the options for analyzing each player and stats.

# GameStats class

The GameStats class is a part of the map used in FrisbeeStatsAnalyzer, and holds the stats for each player. I will go over each method and what they do.

## GameStats constructor

The constructor for GameStats takes 10 inputs: player name, game number, point in game, started on offense, uncompleted throws, completed throws, catches, drops, forced ds, and if the line won. This constructs an object that holds all the information for each player and their stats.

### Example Usage:

As an example of input, one of the lines of stats of a player that could be read and inputted into the constructor is "Rook, 1, 3, yes, 0, 2, 1, 0, 2, true". This data would then be recorded in this GameStats object that corresponds to Rook.

## calculatePlusMinus

calculatePlusMinus takes no inputs. It is called within each player, and calculates the plus minus of a player by adding their completed throws, catches, and forced ds, and subtracts their drops and uncompleted throws. This method will return an Integer value that is equal to the player's plus minus.

### Example Usage:

If you were to call this method on Rook's object that I mentioned in the example for the constructor, the method would return (2 + 1 + 2 - 0 - 0 = 5). This would be the resulting plus-minus for Rook.

## Getters

I thought I would include the getters here, as there are a lot. The getter functions all return the values they are associated with, such as a player's catches or forced ds.

# StatList class

The StatList class is a custom class that I created to store data for each player. It uses an ArrayList, and each node of the list is a GameStats stat. This class is meant to create a list of the stats for each player. I created my own linked list structure.

### Example Usage:

StatList playerStats = new StatList();

## StatNode inner class

The inner class StatNode represents the nodes within the list and contains a constructor that makes a new StatNode that is then added to the StatList.

## getSize method

The getSize method returns the total number of games in the list. It returns an int, the number of stored games within the list.

## addGame method

The addGame method takes one input, being a GameStats stat. It creates a new StatNode within the StatList and adds the game.

### Example Usage:

playerStats.addGame(new GameStats("Rook", 1, 3, true, 0, 5, 3, 1, 2, true));

## averageStat method

This method takes one input, which is the average stat you want to find. It then searches through the StatList for each stat that matches, and adds them to the double value that is returned. It also throws an error if an unsupported stat name is inputted.

### Example Usage:

double avgCatches = playerStats.averageStat("catches");

## getGamesInPeriod method

This method takes two inputs, a start int, and an end int. These represent the first and last game in the period you want to search. It returns all games within that period.

### Example Usage:

List<GameStats> earlyGames = playerStats.getGamesInPeriod(1, 3);

## calculateTotalPlusMinus method

This method is similar to the calculation of plus minus, except it calculates the plus minus for the entire team. It takes no inputs, and returns an int equal to the total plus minus.

### Example Usage:

int totalImpact = playerStats.calculateTotalPlusMinus();

## Example Usage of Class

StatList playerCareer = new StatList();
playerCareer.addGame(new GameStats("Rook", 1, 1, true, 0, 5, 3, 1, 2, true));
playerCareer.addGame(new GameStats("Rook", 2, 3, false, 2, 3, 1, 0, 1, false));

// Perform analysis
int totalGames = playerCareer.getSize();
double avgThrows = playerCareer.averageStat("completed throws");
List<GameStats> game2Stats = playerCareer.getGamesInPeriod(2, 2);
int careerImpact = playerCareer.calculateTotalPlusMinus();

# FrisbeeStatsAnalyzer class

The FrisbeeStatsAnalyzer class is what holds most of the methods that are meant to display the four functions we described in part 1. This class allows us to run each method in the Main class which compiles all of the methods included in this class. It uses a map to store the data that is inputted.

## FrisbeeStatsAnalyzer constructor

The FrisbeeStatsAnalyzer constructor takes one input of a map of type <String, StatList>. It then initializes the playerStats list, and calculates the total games.

## calculateTotalGames method

the calculateTotalGames takes no parameters. It is used to find the total games that were played in the time of the data. It calculates the total number of games by running through the list and tracking the games.

## averagePlayerStat method

The averagePlayerStat method takes two inputs: the name of a player and a stat that you want to average. It simply checks the player that is specified and returns an int of their stat.

### Example Usage:

for (String player : analyzer.getPlayerStats().keySet()) {
      double avg = analyzer.averagePlayerStat(player, stat);
      System.out.printf("%s: %.2f %s%n", player, avg, stat);
}

## calculateTeamTrends method

This method takes two inputs: a stat name, and an amount of games (max 2 for better tracking). It then tracks the average stat for all players within these one or two games to analyze the team trends and how we are doing generally as a team. This is the output of the method.

### Example Usage:

Map<String, Double> trends = analyzer.calculateTeamTrends(stat, games);
trends.forEach((player, avg) -> System.out.printf("%s: %.2f %s%n", player, avg, stat));

## calculateAllPlusMinus method

This method is similar to the calculation of plus minus, except it calculates the plus minus for the entire team. It takes no inputs, and returns an int equal to the total plus minus. We needed to include a method for this in this class as well for it to work.

### Example Usage:

 private static void viewPlusMinus(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== Player Plus-Minus Ratings ===");
        Map<String, Integer> plusMinus = analyzer.calculateAllPlusMinus();
        plusMinus.forEach((player, pm) -> System.out.printf("%s: %+d%n", player, pm));
    }

## getMostImpactfulPlayers method

This method takes one parameter, which is the minimum amount of games the impactful players had to play in. It then makes a list of the most impactful players using calculateTotalPlusMinus as a helper function. It outputs a list of the most impactful players in order.

### Example Usage:

private static void findImpactfulPlayers(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== Most Impactful Players ===");
        System.out.print("Enter minimum games played: ");
        int minGames = getIntInput(1, analyzer.getTotalGames());
        
        System.out.println("\nResults (min " + minGames + " games):");
        List<String> players = analyzer.getMostImpactfulPlayers(minGames);
        players.forEach(System.out::println);
    }

## recommendLines method

This method takes one input, an integer of the number of players that should be recommended for each line. It then access playerstats and uses a calculation combining plus-minus, catches, drops, and forced ds. After analyzation, it returns a Map of type <String, List<String>> that contains the new suggested offensive/defensive lines.

### Example Usage:

private static void suggestNewLine(FrisbeeStatsAnalyzer analyzer) {
        System.out.println("\n=== New Suggested O and D Lines  ===");
        Map<String, List<String>> playerList = analyzer.recommendLines(7);
        System.out.println(playerList);
    }

## rankPlayers method

This method is used as a helper function for getting the most impactful players. It takes a List of player names and a to double function of scorers as input, and returns a sorted list of the players ranked.

### Example Usage:

List<String> offenseRank = rankPlayers(players, 
            p -> playerStats.get(p).averageStat("completed throws") * 0.5 +
             playerStats.get(p).averageStat("catches") * 0.3 +
             playerStats.get(p).averageStat("plusminus") * 0.2 -
             playerStats.get(p).averageStat("drops") * 0.2);

## getStatValue method

Very simple method, takes a GameStats game and a stat and simply returns the value associated to the selected stat. 

### Example Usage:

for (GameStats game : recentGames) {
      try {
          sum += getStatValue(game, statName);
              count++;
          } catch (IllegalArgumentException e) {
              System.err.println("Invalid stat for " + entry.getKey() + ": " + e.getMessage());
          }
      }
}

# StatsFileParser class

The StatsFileParser class has one task which is operates, which is to convert the information from the CSV into GameStats objects. 

## parseFile method

The parseFile method takes one input, a file path to whatever CSV is going to be operated on. It returns the playerstats all compiled together.

### Example Usage:

System.out.println("Loading player stats data...");
            Map<String, StatList> playerStats = StatsFileParser.parseFile("data/Rook_Snooze_Full_Data.csv");
            FrisbeeStatsAnalyzer analyzer = new FrisbeeStatsAnalyzer(playerStats);

## parseWithDefault method

This method simply transmits a String integer value into an actual integer, and takes a String value and int defaultvalue. This ensures that Integers are actually integers.

### Example Usage:

if (values[0].trim().equals("**Game 1**")) {
                    currentGame = Integer.parseInt(values[0].trim());
                    continue;
                }



