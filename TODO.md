# Gameplay Fixes
- **Player moves too fast**
- **Player camera moves too often**
  - Needs a deadzone in the center that will allow the player small movements before the entire camera moves.

# New Features
## Heroes
- Need to be implemented.
- Need their pathfinding algorithm.

## Runes
- Special enemy summons when meeting certain criteria.

## Dungeon IQ
- Will give enemies special attacks as the entire dungeon's IQ increases.

## Enemy Buffs
- Will give enemies buffs across the entire roster.

## Evolution
- Will upgrade enemies into a more powerful variant.
- Same as current version in all aspects except stats.

## Adaptation
- Depending on criteria, enemies will sidegrade and become a new type of enemy (subclass of itself).
  - **Examples:**
    - Slime → Slime on roller skates (to avoid predation).
    - Slime → Fat Slime (to store higher food reserves to prevent starvation).

## Water
- Blocks need to be added to handle spawning of water.
- Water needs to flow downward to mimic gravity.
- Water should contain a value like nutrients/mana, allowing stacking and movement when space is limited as the player digs.
- **Water Creatures** need to be implemented.

## Slime
- Needs to reproduce.
- Needs to walk slower.

## Audio
- Needs to exist.

## Sprite System
- Need sprites.
- Need a sprite handler.

## Limit Controls and Reactions
- Prevent pressing a button for 1/60th of a second from causing multiple instances of said button action.
- Accidental culling of enemies could occur otherwise.

## Camera
- Current camera movement makes it very hard to view the entire map.
- Implement a system where the player moves by X/Y values until far enough from the offset that the camera moves.
  - Allows precise movements without triggering camera shifts.

# Refactors
## GameCanvas
- Should only be responsible for painting, not deciding what gets painted.
- Move logic outside of it.

## Level
- Minor refactoring required now that it is a singleton class.

## Enemy
- Contains useful movement code that should be moved outside for non-enemy NPCs to utilize.

## Camera
- Needs to be refactored into a singleton class.

## kbInput
- Should be a singleton class.

## Remove Coordinate Class
- Coordinate class has proven to be a massive headache and should be removed.

# Architecture
- Create a state within the engine to interrupt the game loop with menus.
