
# Ultimate Frisbee Stats Tracker

**Author:** Emmett Levine and Cid Maciel

Welcome to the Ultimate Frisbee Stats Tracker! With this program, you can record individual player stats and team stats rapidly and live as the game is played, and then export these results to a CSV file that can be read by the Frisbee Stats Analyzer.


## Usage

The point of this program is to have a simple and consistent way for a user to input stats for a frisbee game. User will be prompted with several questions before the game starts about roster and game details. Then, point by point, the user can input player-action codes. When the user inputs the "line" of players for the point, each player is assigned a number, 1-7. As the point is played out, the user will input the player number plus their action. The action codes are 'c': completed catch, 'd': dropped catch, 't': completed throw, 'u': unsuccessful throw, 'f': forced turnover, 'a': assist, and 's': score. For example, if the first person on the line scores, the user would input '1s'. Then, as the point would be over and the user's team had scored, the user can input 'point won'. They then continue until the game is over, which they indicate by saying 'game over'. After the game and at half time, the player and team stats are presented. Player ratings are also presented, which are computed with the equation: rating = c + t + f - d - u + 3(a) + 3(s).


Here is an example of a one point game that a user might enter

```
Welcome to Ultimate Frisbee Stats Tracker! Good luck in the game!

Enter the players for this game, separated by comma:
Rook, Snooze, Jimmy, Prof Li, Emmett, Cid, Pres Starr

Confirm Roster:
Rook
Snooze
Jimmy
Prof Li
Emmett
Cid
Pres Starr

Enter 'add' to add players, 'remove' to delete players, or 'done' to continue:
done

What is today's date? Please enter in M/D/Y form, ex. 09/20/2004:
05/14/2025

Enter the name of the opposing team (no spaces or non-alphabet characters):
AntiDOEAdministration

Game to 13 or 15?
13

Starting on offense or defense?
offense

Braineaters vs AntiDOEAdministration
Game to 13
Starting on offense

----- New Point -----
Current Score: Braineaters 0 - 0 AntiDOEAdministration
We are on offense
Enter the players on this line, separated by comma:
Rook, Snooze, Jimmy, Prof Li, Emmett, Cid, Pres Starr

Current Line:
1. Rook
2. Snooze
3. Jimmy
4. Prof Li
5. Emmett
6. Cid
7. Pres Starr

Enter player-action code, 'point won', 'point lost', or 'sub'):
1t
1. Rook Completed Throw

Enter player-action code, 'point won', 'point lost', or 'sub'):
2c
2. Snooze Completed Catch

Enter player-action code, 'point won', 'point lost', or 'sub'):
2a
3. Snooze Assist

Enter player-action code, 'point won', 'point lost', or 'sub'):
4s
4. Prof Li Score

Enter player-action code, 'point won', 'point lost', or 'sub'):
point won

Enter 1 if ready for next point; enter 2 if need to alter this point; enter 'game over' if the game is over.
game over

----- Final Game Stats -----
Team Stats:
Completed Catches: 2
Dropped Catches: 0
Completed Throws: 2
Unsuccessful Throws: 0
Forced Turnovers: 0
Assists: 1
Scores: 1
Throw Completion Rate: 100.0%
Catch Completion Rate: 100.0%

----- Full Player Stats -----

Rook:
  Completed Catches:     0
  Dropped Catches:       0
  Completed Throws:      1
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               0
  Scores:                0

Snooze:
  Completed Catches:     1
  Dropped Catches:       0
  Completed Throws:      1
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               1
  Scores:                0

Jimmy:
  Completed Catches:     0
  Dropped Catches:       0
  Completed Throws:      0
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               0
  Scores:                0

Prof Li:
  Completed Catches:     1
  Dropped Catches:       0
  Completed Throws:      0
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               0
  Scores:                1

Emmett:
  Completed Catches:     0
  Dropped Catches:       0
  Completed Throws:      0
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               0
  Scores:                0

Cid:
  Completed Catches:     0
  Dropped Catches:       0
  Completed Throws:      0
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               0
  Scores:                0

Pres Starr:
  Completed Catches:     0
  Dropped Catches:       0
  Completed Throws:      0
  Unsuccessful Throws:   0
  Forced D's:            0
  Assists:               0
  Scores:                0

Player Ratings:
Rook rating: 1
Snooze rating: 4
Jimmy rating: 0
Prof Li rating: 3
Emmett rating: 0
Cid rating: 0
Pres Starr rating: 0

Do you want to export stats to CSV? 'Yes' to export, 'no' to terminate program
no

Sounds good, thank you for using the Ulitmate Frisbee Stats Tracker!
```

There are several other options within this program, such as adding and removing players before the roster is set and the game starts or making a sub, shown below.

Adding and removing players:

```
Confirm Roster:
Rook
Snooze
Jimmy
Prof Li
Emmett
Cid
Pres Starr

Enter 'add' to add players, 'remove' to delete players, or 'done' to continue:
add
Enter additional player names, separated by commas:
Prof Chen
Added: Prof Chen

Confirm Roster:
Rook
Snooze
Jimmy
Prof Li
Emmett
Cid
Pres Starr
Prof Chen

Enter 'add' to add players, 'remove' to delete players, or 'done' to continue:
remove
Enter name of player to remove:
Jimmy
Removed: Jimmy

Confirm Roster:
Rook
Snooze
Prof Li
Emmett
Cid
Pres Starr
Prof Chen

Enter 'add' to add players, 'remove' to delete players, or 'done' to continue:
done
```

Making a sub mid point:

```
----- New Point -----
Current Score: Braineaters 0 - 0 13
We are on defense
Enter the players on this line, separated by comma:
 Prof Li, Emmett, Cid, Pres Starr

Current Line:
1. Prof Li
2. Emmett
3. Cid
4. Pres Starr

Enter player-action code, 'point won', 'point lost', or 'sub'):
sub
Enter player number to substitute (1-4):
3
Enter name of substitute player:
Rook
Substituting Cid with Rook

Updated Line:
1. Prof Li
2. Emmett
3. Rook
4. Pres Starr

Enter player-action code, 'point won', 'point lost', or 'sub'):

```

I hope that this brief overview paints a picture of what you can do with this program. There are more updates coming that will help fix bugs, but it does operate these key components as long as the user does their best to stay within the operation constraints.

## API

### Constructor

```
UltimateFrisbeeStatsTracker tracker = new UltimateFrisbeeStatsTracker();
```
Creates a new instance of the game tracker.

### `startGame()`

```
tracker.startGame();
```
Starts the interactive terminal session. Prompts for rosters, game details, and enters gameplay loop.

### `recordAction(String input)`

Adds an action for a player during the point.

### `makeSubstitution()`

Substitutes a player on the field.

### `exportStatsToCSV()`

Exports game data to `Braineatersvs[Opponent]_[Date].csv`.


### `displayPlayerRatings()`

Shows a calculated performance score per player.