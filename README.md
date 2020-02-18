# Tower Defense Game
Programming practices course.
In this project at least 4 Design Patterns were used.
For detailed information view the Documentation folder.

Guide to run the project:

1. You can run the application by running 

:::  /src/com/app/towerDefense/guisystem/game.java

How to play the game?
When the game starts:
	in the upper left corner,
	user can choose whether to create new map, edit saved map,
	load the presious game.
To create map:
	left click is to place a path
	1st right click is to set Entry point
	2nd right click is to set Exit point

To build tower:
	in the right bottom corner, there is a tower shop panel,
	where user can choose the tower, buy it and place it on the map, only if user has enough currency for it.

	user can sell the tower for half of the total spendings on the tower.

	user can upgrade the tower during the waves and in between waves.

	user can not sell and buy tower during the waves, only between waves.

Critters:
	critters difficulty depends on the wave level, the higher the level, more critters appear with higher health points and higher speed.

Game over:
	Each critter substract one hit point from player hit points when passing into the exit.

	when player's hit points reach zero, game over.

	if player clears 50 levels, he wins the game.
