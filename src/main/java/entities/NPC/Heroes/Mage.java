package entities.NPC.Heroes;

import entities.SpriteCoordinate;
import graphics.Sprite.ImgLoader;
import graphics.ScreenSettings;
import graphics.Sprite.SpriteType;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Mage extends Hero {

    public Mage(int health,
                int x,
                int y,
                String name) {

        super(health, x, y, name);
        this.name = name;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY = y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 4;
        this.image = ImgLoader.getImageResource("sprites/hero/heroes.png");
        this.combatCooldown = 60;
        this.fxIndex = 1;
        this.pathfinding = new HeroPathfinder(this);
        pathfinding.logPath(false);
        lastTilePosX = tilePositionX;
        lastTilePosY = tilePositionY;
        this.hasMVP = false;
    }

    int lastTilePosX;
    int lastTilePosY;


    // Mage Male sprite coordinates
    private static final Map<SpriteType, SpriteCoordinate[]> SPRITE_ANIMATIONS = new EnumMap<>(SpriteType.class);

    static {
        SPRITE_ANIMATIONS.put(SpriteType.WALK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(6, 176, 16, 16),
                new SpriteCoordinate(22, 176, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(38, 176, 16, 16),
                new SpriteCoordinate(54, 176, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(70, 176, 16, 16),
                new SpriteCoordinate(86, 176, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(103, 176, 16, 16),
                new SpriteCoordinate(119, 176, 16, 16)
        });

        // Attack animations using the same coordinates as walk animations
        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(6, 176, 16, 16),
                new SpriteCoordinate(22, 176, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(38, 176, 16, 16),
                new SpriteCoordinate(54, 176, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(70, 176, 16, 16),
                new SpriteCoordinate(86, 176, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(103, 176, 16, 16),
                new SpriteCoordinate(119, 176, 16, 16)
        });



    }

    @Override
    public void behavior() {

        if(this.health <= 0){
            isDead = true;
            return;
        }

        if(npcHasMoved()){

            if(detectNewTile()){
                pathfinding.logPath(hasMVP);

                if(pathfinding.currentlyBacktracking || hasMVP) {
                    currDirection = pathfinding.determinePath(hasMVP);
                }
            }
        }

        else if(movementBlocked){
            this.currDirection = pathfinding.determinePath(hasMVP);
            movementBlocked = false;
        }

    }

    @Override
    public void destroy() {
        image = null;
    }

    public String returnNpcType(){
        return "Mage";
    }

    @Override
    protected void spriteHandler() {
        spriteHandlerFull(SPRITE_ANIMATIONS.get(spriteType).length);
    }

    @Override
    public BufferedImage getImage(){
        return getSpriteFromCoordinate(SPRITE_ANIMATIONS.get(spriteType)[spriteFrame]);
    }
}