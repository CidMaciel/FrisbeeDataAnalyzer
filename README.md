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


# FrisbeeStatsAnalyzer constructor

Tje FrisbeeStatsAnalyzer constructor takes one input of a map of type <String, StatList

# calculateTotalGames

the calculateTotalGames takes no parameters. It is used to find the total games that were played in the time of the data.
