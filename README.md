# L3-Java-multiplayer-game
Small multiplayer java fighting game with an MVC implementation, a swing GUI and a very simple AI

The game includes several items such as weapons (guns, bombs, mines), regeneration points, a shield system.
Players can move across the map, the goal is to be the last survivor.

This project implements several design patterns, such as Proxy (to hide some objects between players) or singleton to manage the creation of objects and resources
Terrain generation is procedural and configurable, the number of players and AI is selectable via a menu or the config file.

The objective was mainly to focus on the implementation via an MVC model, the GUI and the gameplay of the game can be greatly improved.

A player view (some items are only visible by a player), all player view and another with a player using a shield:

<p float="left">
<img src="/images/P1.PNG" width="300">
<img src="/images/allp.PNG" width="300">
 <img src="/images/shield.PNG" width="300">
</p>
