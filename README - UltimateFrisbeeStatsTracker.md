
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

### `UltimateFrisbeeStatsTracker()`
Initializes all trackers, variables, structures
Input: None
Output: None

### `initializeTeamStats()`
Sets stat counters to 0
Input: None
Output: None

### `startGame()`
Starts to run the code; prompts for rosters, game details, and enters gameplay loop
Input: user input via scanner (examples as part of enterPlayersForGame(), editRoster(), and enterGameDetails())
Output: printed stats at end of game, csv export if user desires

### `enterPlayersForGame()`
Records the game roster from user
Input: string of players in game, given by user (ex. "Rook, Snooze, Prof Li")
Output: populates players, nameMapping, and playerStats

### `editRoster()`
Lets the user add or remove players before the game start
Input: add, remove, or done, based on user input (ex. "add", "remove", or "done")
Output: updated player list, nameMapping, and playerStats

### `enterGameDetails()`
Collects the rest of the game details, such as date, opponent, and offense/defense from user (ex. "05-14-2025", "Occidental", "Offense")
Input: user-inputted strings for those various details
Output: game settings initialized

### `playPoint()`
Manages the logic for each point, housing the ability to have the user tracking actions, make substitutions, edit actions, while also housing the storage and end of game scenarios; many methods and user interactions run through this method
Input: player actions and point result or subs by user input (examples in getLineForPoint, recordAction(String input))
Output: updates game state and stored point stats

### `getLineForPoint()`
Prompts for user to record the players who will play on the point and validates the entries against the aforegiven roster
Input: user input through scanner of players on line (ex."Rook, Snooze, Prof Li, Prof Birrell, Prof Osborne, Prof Chen, Prof Ye")
Output: updates currentLine


### `makeSubstitution()`
Allows user to replace a player on the line in the middle of a point.
Input: user-dictated player number to replace and sub name (ex. "1", then "Prof Ye")
Output: updates currentLine


### `recordAction(String input)`

Adds an action for a player during the point.
Input: user gives a 2-char string (assigned player number plus action code) (ex. "1c", "2f")
Output: updates action lists

### `getActionDescription(char action)`
Maps the give action code to it's full correlated value so that the user can read it back
Input: char representing action code (ex. "c")
Output: updates action lists

### `alterPointActions()`
Allows user to insert or delete actions from the point after the point
Input: user string of insert or delete (examples in insertAction() and deleteAction())
Output: updates action lists

### `insertAction()`
Inserts an action after the point if it was missed
Input: user inputs index where the action should go and the new action code (ex. "insert", then "1")
Output: updates action lists

### `deleteAction()`
Deletes an action after the point if it was add by accident
Input: user inputs which action should be deleted by index
Output: updates action lists (ex. "delete", then "1")

### `processPointActions()`
Updates team and player stats after point ends
Input: none
Output: updates teamStats, playerStats

### `updateTeamStats(String actionType)`
Add to team stat totals
Input: char of action code (ex. "c")
Output: updates teamStats

### `storePointStats()`
Saves stats from points to be used for exporting to CSV
Input: none
Output: updates pointPlayerStats

### `updatePointStats(int pointIndex)`
Updates player stats in a point after edits of insertion and deletion
Input: index in pointPlayerStats based on index of insertion or deletion of point (ex. "1")
Output: updates pointPlayerStats

### `displayHalftimeStats()`
Prints score and team stats at halftime
Input: none
Output: print for user to observe

### `displayFinalStats()`
Prints score and team stats at final time
Input: none
Output: print for user to observe

### `displayFullPlayerStats()`
Prints individual player stats
Input: none
Output: print for user to observe

### `displayPlayerRatings()`
Prints player ratings based on weighted calculation of recorded actions by player
Input: none
Output: print for user to observe

### `displayTeamStats()`
Prints team aggregated stats
Input: none
Output: print for user to observe

### `exportStatsToCSV()`
Exports game data to `Braineatersvs[Opponent]_[Date].csv`.
Input: none
Output: csv file

### `StatNode`
node representing one action performed by a player

### `DoublyLinkedList`
comprised of StatNodes for each player


