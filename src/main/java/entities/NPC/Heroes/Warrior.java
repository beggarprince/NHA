package entities.NPC.Heroes;

import entities.SpriteCoordinate;
import graphics.Sprite.ImgLoader;
import graphics.ScreenSettings;
import graphics.Sprite.SpriteType;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Warrior extends Hero{

    //TODO the rest can have static images, this can't. Figure out why
//  public static BufferedImage image =  ImgLoader.getImageResource("sprites/hero/knight_mvRight.png");

    //TODO i really need to be able to send stats if i want to. This could be the default, but i could use a diff const with more arguments
    public Warrior(int health,
                   int x,
                   int y,
                   String name){

        super( health, x, y, name);
        this.name = name;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 4;
        this.image = ImgLoader.getImageResource("sprites/hero/heroes.png");
        this.combatCooldown = 60;
        this.fxIndex = 1;
        this.pathfinding = new HeroPathfinder(this);
        spriteType = SpriteType.WALK_DOWN;
        pathfinding.logPath(false);
        lastTilePosX = tilePositionX;
        lastTilePosY = tilePositionY;
        this.hasMVP = false;
    }

    int lastTilePosX;
    int lastTilePosY;


    // Sprite animation coordinates using EnumMap
    //private static final Map<SpriteType, SpriteCoordinate[]> SPRITE_ANIMATIONS = new EnumMap<>(SpriteType.class);
// These are messed up i mixed 2 i didn't realize one was male the other female
//    static {
//        // Walk animations
//        SPRITE_ANIMATIONS.put(SpriteType.WALK_UP, new SpriteCoordinate[]{
//                new SpriteCoordinate(38, 8, 15, 16),
//                new SpriteCoordinate(38, 29, 15, 16),
//                new SpriteCoordinate(54, 8, 15, 16),
//                new SpriteCoordinate(54, 29, 15, 16)
//        });
//
//        SPRITE_ANIMATIONS.put(SpriteType.WALK_DOWN, new SpriteCoordinate[]{
//                new SpriteCoordinate(6, 8, 15, 16),
//                new SpriteCoordinate(6, 29, 15, 16),
//                new SpriteCoordinate(22, 8, 15, 16),
//                new SpriteCoordinate(22, 29, 15, 16)
//        });
//
//        SPRITE_ANIMATIONS.put(SpriteType.WALK_LEFT, new SpriteCoordinate[]{
//                new SpriteCoordinate(69, 8, 15, 16),
//                new SpriteCoordinate(69, 29, 15, 16),
//                new SpriteCoordinate(84, 8, 15, 16),
//                new SpriteCoordinate(82, 29, 15, 16)
//        });
//
//        SPRITE_ANIMATIONS.put(SpriteType.WALK_RIGHT, new SpriteCoordinate[]{
//                new SpriteCoordinate(98, 8, 15, 16),
//                new SpriteCoordinate(96, 29, 15, 16),
//                new SpriteCoordinate(112, 8, 15, 16),
//                new SpriteCoordinate(110, 29, 15, 16)
//        });
//
//
//        //TODO replace down animations
//        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_LEFT, new SpriteCoordinate[]{
//                new SpriteCoordinate(98, 8, 15, 16),
//                new SpriteCoordinate(96, 29, 15, 16),
//                new SpriteCoordinate(112, 8, 15, 16),
//                new SpriteCoordinate(110, 29, 15, 16)
//        });
//
//        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_RIGHT, new SpriteCoordinate[]{
//                new SpriteCoordinate(98, 8, 15, 16),
//                new SpriteCoordinate(96, 29, 15, 16),
//                new SpriteCoordinate(112, 8, 15, 16),
//                new SpriteCoordinate(110, 29, 15, 16)
//        });
//
//        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_UP, new SpriteCoordinate[]{
//                new SpriteCoordinate(98, 8, 15, 16),
//                new SpriteCoordinate(96, 29, 15, 16),
//                new SpriteCoordinate(112, 8, 15, 16),
//                new SpriteCoordinate(110, 29, 15, 16)
//        });
//
//        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_DOWN, new SpriteCoordinate[]{
//                new SpriteCoordinate(98, 8, 15, 16),
//                new SpriteCoordinate(96, 29, 15, 16),
//                new SpriteCoordinate(112, 8, 15, 16),
//                new SpriteCoordinate(110, 29, 15, 16)
//        });
//
//        // Death animation (placeholder coordinates - update with your actual values)
//        SPRITE_ANIMATIONS.put(SpriteType.DEATH, new SpriteCoordinate[]{
//
//        });
//    }

    // Warrior Male sprite coordinates
    private static final Map<SpriteType, SpriteCoordinate[]> SPRITE_ANIMATIONS = new EnumMap<>(SpriteType.class);

    //TODO change these to image.getSubImage and the map to be in hero and be Map<SpriteType, BufferedImage[]>
    static {
        SPRITE_ANIMATIONS.put(SpriteType.WALK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(6, 50, 16, 16),
                new SpriteCoordinate(23, 50, 16, 16),
                new SpriteCoordinate(6, 50, 16, 16),
                new SpriteCoordinate(23, 50, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(40, 50, 16, 16),
                new SpriteCoordinate(57, 50, 16, 16),
                new SpriteCoordinate(40, 50, 16, 16),
                new SpriteCoordinate(57, 50, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(73, 50, 16, 16),
                new SpriteCoordinate(88, 50, 16, 16),
                new SpriteCoordinate(73, 50, 16, 16),
                new SpriteCoordinate(88, 50, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(103, 50, 16, 16),
                new SpriteCoordinate(118, 50, 16, 16),
                new SpriteCoordinate(103, 50, 16, 16),
                new SpriteCoordinate(118, 50, 16, 16)
        });

        //TODO replace animations or add some attack effect
        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(73, 50, 16, 16),
                new SpriteCoordinate(88, 50, 16, 16),
                new SpriteCoordinate(73, 50, 16, 16),
                new SpriteCoordinate(88, 50, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(103, 50, 16, 16),
                new SpriteCoordinate(118, 50, 16, 16),
                new SpriteCoordinate(103, 50, 16, 16),
                new SpriteCoordinate(118, 50, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(40, 50, 16, 16),
                new SpriteCoordinate(57, 50, 16, 16),

                new SpriteCoordinate(40, 50, 16, 16),
                new SpriteCoordinate(57, 50, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(6, 50, 16, 16),
                new SpriteCoordinate(23, 50, 16, 16),
                new SpriteCoordinate(6, 50, 16, 16),
                new SpriteCoordinate(23, 50, 16, 16)
        });


    }


    @Override
    public void behavior() {
        //System.out.println(this.tilePositionX + ":" + this.tilePositionY);
        if(this.health <= 0){
            isDead = true;
            return;
        }

        if(npcHasMoved()){

            //This is how we know we are at a new tile
            if(detectNewTile()){
                pathfinding.logPath(hasMVP);

                if(pathfinding.currentlyBacktracking || hasMVP) {
                    currDirection = pathfinding.determinePath(hasMVP);
                }
            }
        }

        //This means we hit a wall
        else if(movementBlocked){
            this.currDirection = pathfinding.determinePath(hasMVP);
            movementBlocked = false;
        }

    }

    @Override
    public BufferedImage getImage(){
        return getSpriteFromCoordinate(SPRITE_ANIMATIONS.get(spriteType)[spriteFrame]);
    }

    @Override
    public void destroy() {
        image = null;
    }

    public String returnNpcType(){
        return "Warrior";
    }

    @Override
    protected void spriteHandler() {
        spriteHandlerFull(SPRITE_ANIMATIONS.get(spriteType).length);
    }

}