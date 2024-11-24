# Current
- Bugged hero movement
    - Despite using the exact same function as the enemies, heroes are getting stuck in areas where they have possible movement options
      - There doesn't seem to be any directions in particular that they get stuck at

- Found unecessary calculation 
  - "  Hero hero = new Soldier(1, positionx * ScreenSettings.TILE_SIZE, positiony * ScreenSettings.TILE_SIZE);
    - This revealed that we're giving world position to divide it to re-multiply it in the constructor of both enemy and tile size

# Fixed
- Extremely high RAM usage that just keeps incrementing, memory leak somewhere
    - Sol
       - Enemies were made null but the reference to their image was not which kept them in memory
       - this.image = null dereferences it completely so the garbage collector gets rid of it
