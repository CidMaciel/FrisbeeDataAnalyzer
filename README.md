# FrisbeeDataAnalyzer
Welcome to FrisbeeDataAnalyzer, a data analyzer for ultimate frisbee teams! With this program, you can input live stats as well as read from a csv of stats to recieve a large amont of information such as the most impactful players, player plus-minus, overall team trends, and even suggesting new lines.


## Usage

There are several methods within the FrisbeeDataAnalyzer project. I will begin by going over GameStats, the class in which we store the stats for each individual player

# GameStats class

The GameStats class is a part of the map used in FrisbeeStatsAnalyzer, and holds the stats for each player. I will go over each method and what they do.

## GameStats constructor

The constructor for GameStats takes 10 inputs: player name, game number, point in game, started on offense, uncompleted throws, completed throws, catches, drops, forced ds, and if the line won. This constructs an object that holds all the information for each player and their stats.

### Example Usage:

As an example of input, one of the lines of stats of a player that could be read and inputted into the constructor is "Rook, 1, 3, yes, 0, 2, 1, 0, 2, true". This data would then be recorded in this GameStats object that corresponds to Rook.

## calculatePlusMinus

calculatePlusMinus takes no inputs. It is called within each player, and calculates the plus minus of a player by adding their completed throws, catches, and forced ds, and subtracts their drops and uncompleted throws. This method will return an Integer value that is uqual to the player's plus minus.

### Example Usage:

If you were to call this method on Rook's object that I mentioned in the example for the constructor, the method would return (2 + 1 + 2 - 0 - 0 = 5). This would be the resulting plus-minus for Rook.

## Getters

I thought I would include the getters here, as there are a lot. The getter functions all return the values they are associated with, such as a player's catches or forced ds.

# StatList class

The StatList class is a custom class that I created to store data for each player. It uses an ArrayList, and each node of the list is a GameStats stat. This class is meant to create a list of the stats for each player. I created my own linked list structure.

### Example Usage:

StatList playerStats = new StatList();

## StatNode inner class

the inner class StatNode represents the nodes within the list and contains a constructor that makes a new StatNode that is then added to the StatList.

## getSize method

The getSize method returns the total number of games in the list. It returns an int, the number of stored games within the list.

## addGame method

the addGame method takes one input, being a GameStats stat. It creates a new StatNode within the StatList and adds the game.

### Example Usage:

playerStats.addGame(new GameStats("Rook", 1, 3, true, 0, 5, 3, 1, 2, true));

## averageStat method

This method takes one input, which is the average stat you want to find. It then searches through the StatList for each stat that matches, and adds them to the double value that is returned. It also throws an error if an unsupported stat name is inputted

### Example Usage:

double avgCatches = playerStats.averageStat("catches");

## getGamesInPeriod

This method takes two inputs, a start int, and an end int. These represent the first and last game in the period you want to search. It returns all games within that period.

### Example Usage:

List<GameStats> earlyGames = playerStats.getGamesInPeriod(1, 3);

## calculateTotalPlusMinus

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

# FrisbeeStatsAnalyzer constructor

Tje FrisbeeStatsAnalyzer constructor takes one input of a map of type <String, StatList

# calculateTotalGames

the calculateTotalGames takes no parameters. It is used to find the total games that were played in the time of the data.
