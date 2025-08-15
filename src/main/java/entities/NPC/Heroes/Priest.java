package entities.NPC.Heroes;
import entities.NPC.Heroes.skills.Fireball;
import entities.SpriteCoordinate;
import graphics.Sprite.ImgLoader;
import graphics.ScreenSettings;
import graphics.Sprite.SpriteType;
import world.World;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class Priest extends Hero {

    public Priest(int health,
                int x,
                int y,
                String name) {

        super( health, x, y, name);
        this.name = name;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 12;
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


    // Priest Female sprite coordinates
    private static final Map<SpriteType, SpriteCoordinate[]> SPRITE_ANIMATIONS = new EnumMap<>(SpriteType.class);

    static {
        SPRITE_ANIMATIONS.put(SpriteType.WALK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(5, 155, 16, 16),
                new SpriteCoordinate(20, 155, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(35, 155, 16, 16),
                new SpriteCoordinate(50, 155, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(65, 155, 15, 16),
                new SpriteCoordinate(79, 155, 14, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.WALK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(92, 155, 14, 16),
                new SpriteCoordinate(106, 155, 14, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_DOWN, new SpriteCoordinate[]{
                new SpriteCoordinate(5, 155, 16, 16),
                new SpriteCoordinate(20, 155, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_UP, new SpriteCoordinate[]{
                new SpriteCoordinate(35, 155, 16, 16),
                new SpriteCoordinate(50, 155, 16, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_LEFT, new SpriteCoordinate[]{
                new SpriteCoordinate(65, 155, 15, 16),
                new SpriteCoordinate(79, 155, 14, 16)
        });

        SPRITE_ANIMATIONS.put(SpriteType.ATTACK_RIGHT, new SpriteCoordinate[]{
                new SpriteCoordinate(92, 155, 14, 16),
                new SpriteCoordinate(106, 155, 14, 16)
        });


    }
    int ammo = 10;
    @Override
    public void behavior() {

        if(this.health <= 0){
            isDead = true;
            return;
        }
//TODO this behavior is common amongst all NPC.Hero, move up this snippet and share later not in the mood to clean

        if(World.INSTANCE.checkIfMonsterInRange(this, 5) && ammo > 0){
            //System.out.println("Shooting fireball type");
            new Fireball(tilePositionX,
                    tilePositionY,
                    this.currDirection,
                    20);

            ammo--;
        }

        else if(npcHasMoved()){

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
        return "Priest";
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