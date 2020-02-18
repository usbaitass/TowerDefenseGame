1. You can run the application by running 

:::  /src/com/app/towerDefense/guisystem/game.java


2. Github repository

::: https://github.com/amritanshmishra/xyz

3. Java document 

::: javadoc is in xyz-Build3 doc folder

NB: Classes that extend and implement abstract methods inherit their java doc through the use of {@inheritDoc} at the top of the class (class java doc);

4. Coding standards are in the design document

5. To run the tests, run the TestSuite.java

Team 5

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
