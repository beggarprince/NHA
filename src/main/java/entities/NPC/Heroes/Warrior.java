package entities.NPC.Heroes;

import entities.SpriteCoordinate;
import graphics.Sprite.ImgLoader;
import graphics.ScreenSettings;
import graphics.Sprite.SpriteType;
import graphics.SpriteSettings;

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
        this.health = 96;
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
        this.spriteType = SpriteType.WALK_RIGHT;
        pathfinding.logPath(false);
        lastTilePosX = tilePositionX;
        lastTilePosY = tilePositionY;
        this.hasMVP = false;
    }

    int lastTilePosX;
    int lastTilePosY;



    // Walking animation sprite coordinates
//

    private final static int[] spriteArrayCoordinateCount = {
            4, //left
            4, // Right
            4, //Up
            4 //Down
    };


    // Sprite animation coordinates using EnumMap
    private static final Map<SpriteType, SpriteCoordinate[]> SPRITE_ANIMATIONS = new EnumMap<>(SpriteType.class);

    static {
        // Walk animations
        SPRITE_ANIMATIONS.put(SpriteType.WALK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(38, 8, 15, 16),
                new SpriteCoordinate(38, 29, 15, 16),
                new SpriteCoordinate(54, 8, 15, 16),
                new SpriteCoordinate(54, 29, 15, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(6, 8, 15, 16),
                new SpriteCoordinate(6, 29, 15, 16),
                new SpriteCoordinate(22, 8, 15, 16),
                new SpriteCoordinate(22, 29, 15, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(69, 8, 15, 16),
                new SpriteCoordinate(69, 29, 15, 16),
                new SpriteCoordinate(84, 8, 15, 16),
                new SpriteCoordinate(82, 29, 15, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(98, 8, 15, 16),
                new SpriteCoordinate(96, 29, 15, 16),
                new SpriteCoordinate(112, 8, 15, 16),
                new SpriteCoordinate(110, 29, 15, 16)
        });


        //TODO replace down animations
        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(98, 8, 15, 16),
                new SpriteCoordinate(96, 29, 15, 16),
                new SpriteCoordinate(112, 8, 15, 16),
                new SpriteCoordinate(110, 29, 15, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(98, 8, 15, 16),
                new SpriteCoordinate(96, 29, 15, 16),
                new SpriteCoordinate(112, 8, 15, 16),
                new SpriteCoordinate(110, 29, 15, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(98, 8, 15, 16),
                new SpriteCoordinate(96, 29, 15, 16),
                new SpriteCoordinate(112, 8, 15, 16),
                new SpriteCoordinate(110, 29, 15, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(98, 8, 15, 16),
                new SpriteCoordinate(96, 29, 15, 16),
                new SpriteCoordinate(112, 8, 15, 16),
                new SpriteCoordinate(110, 29, 15, 16)
        });

        // Death animation (placeholder coordinates - update with your actual values)
        SPRITE_ANIMATIONS.put(SpriteType.DEATH, new SpriteCoordinate[]{

        });
    }


    @Override
    public void behavior() {

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

    //What the fuck am i overriding
    @Override
    public BufferedImage getImage(){
        return returnSprite();
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
        spriteHandlerNew();
    }

    private void spriteHandlerNew(){

        SpriteType currentSpriteArray = determineSpriteArrayFull();
        // We don't need this since we have the advanced sprite array
        //spriteArrayIndex = simpleSpriteToArray(currentSpriteArray);

        if(spriteType != currentSpriteArray){
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
            spriteType = currentSpriteArray;
        }
        spriteFrameTimeCounter++;

        if(spriteFrameTimeCounter == (spriteArrayCoordinateCount[spriteArrayIndex] -1) * SpriteSettings.ANIMATION_LENGTH){
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
        }

        if(spriteFrameTimeCounter % SpriteSettings.ANIMATION_LENGTH == 0){
            spriteFrame++;
        }

    };

    private BufferedImage returnSprite(){

        System.out.println("Trying to access " + spriteType + " at frame " + spriteFrame);
        SpriteCoordinate temp = SPRITE_ANIMATIONS.get(spriteType)[spriteFrame];
        //SpriteCoordinate temp =  new SpriteCoordinate(38, 8, 15, 16);
                BufferedImage subImage = image.getSubimage(temp.col, temp.row, temp.width, temp.height);

        return subImage;
    }
}