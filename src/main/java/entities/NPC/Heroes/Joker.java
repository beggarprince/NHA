package entities.NPC.Heroes;
import entities.SpriteCoordinate;
import graphics.Sprite.ImgLoader;
import graphics.ScreenSettings;
import graphics.Sprite.SpriteType;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Joker extends Hero {

    public Joker(int health,
                  int x,
                  int y,
                  String name) {

        super(health, x, y, name);
        this.name = name;
        this.health = 96;
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
        spriteType = SpriteType.WALK_DOWN;
    }

    int lastTilePosX;
    int lastTilePosY;


    // Joker Male sprite coordinates
    private static final Map<SpriteType, SpriteCoordinate[]> SPRITE_ANIMATIONS = new EnumMap<>(SpriteType.class);
    static {
        SPRITE_ANIMATIONS.put(SpriteType.WALK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(5, 260, 16, 16),
                new SpriteCoordinate(20, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(35, 260, 16, 16),
                new SpriteCoordinate(50, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(65, 260, 16, 16),
                new SpriteCoordinate(80, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(95, 260, 16, 16),
                new SpriteCoordinate(110, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(5, 260, 16, 16),
                new SpriteCoordinate(20, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(35, 260, 16, 16),
                new SpriteCoordinate(50, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(65, 260, 16, 16),
                new SpriteCoordinate(80, 260, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(95, 260, 16, 16),
                new SpriteCoordinate(110, 260, 16, 16)
        });
    }

    @Override
    public void behavior() {

        if(this.health <= 0){
            isDead = true;
            return;
        }
//TODO this behavior is common amongst all NPC.Hero, move up this snippet and share later not in the mood to clean
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
        return "Joker";
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