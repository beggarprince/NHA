# Current
- Bugged hero movement
    - Despite using the exact same function as the enemies, heroes are getting stuck in areas where they have possible movement options
      - There doesn't seem to be any directions in particular that they get stuck at


- Found unecessary calculation 
  - "  Hero hero = new Soldier(1, positionx * ScreenSettings.TILE_SIZE, positiony * ScreenSettings.TILE_SIZE);
    - This revealed that we're giving world position to divide it to re-multiply it in the constructor of both enemy and tile size

# Fixed

- Bugged enemy movement
    - Upon hitting a wall they move twice
        - If/else doesn't prevent it, did not happen until Move was moved to npc out of monster
    - Sol
      - Enemies update world position when they are 1 pixel in the square making it appear that they skipped one
      - Used movementCycle to fix collisions and only update the world position when screenPosition % tilesize == 0
      - This makes it so they are fully inside the cell before moving out of it
      - Movement cycle already increments within move(), it was just being utilized for eating. Now it can signal new tile location

- Extremely high RAM usage that just keeps incrementing, memory leak somewhere
    - Sol
       - Enemies were made null but the reference to their image was not which kept them in memory
       - this.image = null dereferences it completely so the garbage collector gets rid of it
