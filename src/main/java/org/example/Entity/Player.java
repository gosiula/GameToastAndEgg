package org.example.Entity;

import org.example.Main.GamePanel;
import org.example.TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

import static org.example.Main.GamePanel.HEIGHT;

// CREATING A PLAYER AND DEFINING THE WORKING OF MOVEMENT, ATTACKS, ANIMATION
// AND THE INTERACTION WITH OTHER OBJECTS
public class Player extends MapObject {

    // player parameters
    private int health;
    private final int maxHealth;
    private int fire;
    private final int maxFire;
    private boolean flinching;
    private long flinchTimer;
    private boolean dead;

    // fireball parameters
    private boolean firing;
    private final int fireCost;
    private final int fireBallDamage;
    private final ArrayList<FireBall> fireBalls;

    // scratching
    private boolean scratching;
    private final int scratchDamage;
    private final int scratchRange;

    // gliding
    private boolean gliding;

    // sub images - animations of the player
    private ArrayList<BufferedImage[]> sprites;

    // animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;

    // Threads
    private Thread playerLogicThread;
    private Thread playerGraphicsThread;
    private Thread playerMainThread;
    private volatile boolean running = true;

    private int points;


    // Player constructor
    public Player(TileMap tm) {
        super(tm);

        // player size
        width = 30;
        height = 30;
        cWidth = 20;
        cHeight = 20;

        // player speed
        moveSpeed = 0.2;
        maxSpeed = 0.9;
        stopSpeed = 0.4;
        fallSpeed = 0.1;
        maxFallSpeed = 2.7;
        jumpStart = -3.8;
        stopJumpSpeed = 0.28;

        facingRight = true;

        // player health and fireballs
        health = maxHealth = 5;
        fire = maxFire = 1000;
        fireCost = 200;
        fireBallDamage = 5;
        fireBalls = new ArrayList<>();

        // scratching
        scratchDamage = 8;
        scratchRange = 40;

        dead = false;

        points = 0; // Zmieniono dostęp do protected

        // loading sprites
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Player/player_sprites.gif"
                    ))
            );

            // getting the desired sub image
            sprites = new ArrayList<>();
            for(int i = 0; i < 7; i++) {
                int[] numFrames = {2, 8, 1, 2, 4, 2, 5};
                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                for(int j = 0; j < numFrames[i]; j++) {
                    if(i != SCRATCHING) {
                        bi[j] = spriteSheet.getSubimage(
                                j * width,
                                i * height,
                                width,
                                height
                        );
                    }
                    else {
                        bi[j] = spriteSheet.getSubimage(
                                j * width * 2,
                                i * height,
                                width * 2,
                                height
                        );
                    }
                }
                sprites.add(bi);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        // initialization of Animation object
        animation = new Animation();

        currentAction = IDLE;

        // creating animation frames
        animation.setFrames(sprites.get(IDLE));

        // setting the delay
        animation.setDelay(400);

        // Separate thread for logic
        playerLogicThread = new Thread(this::playerLogic);
        playerLogicThread.start();

        // Separate thread for graphics
        playerGraphicsThread = new Thread(this::playerGraphics);
        playerGraphicsThread.start();

        // Separate main thread (for any additional main logic)
        playerMainThread = new Thread(this::playerMain);
        playerMainThread.start();
    }

    // getting the parameters of the player
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getFire() { return fire; }
    public int getMaxFire() { return maxFire; }

    public void setFiring() {
        firing = true;
    }
    public void setScratching() {
        scratching = true;
    }
    public void setGliding(boolean b) {
        gliding = b;
    }

    // the player being hit and flinching
    public void hit(int damage) {
        if(flinching || dead) return;
        health -= damage;
        if (health <= 0) {
            health = 0;
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    // method for checking player's attack
    public void checkAttack(ArrayList<Enemy> enemies) {
        // loop through enemies
        for (Enemy e : enemies) {
            // scratch attack
            if (scratching) {
                if (facingRight) {
                    if (e.getx() > x && e.getx() < x + scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                } else {
                    if (e.getx() < x && e.getx() > x - scratchRange && e.gety() > y - height / 2 && e.gety() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                }
            }

            // fireballs
            for (FireBall fireBall : fireBalls) {
                if (fireBall.intersects(e)) {
                    e.hit(fireBallDamage);
                    fireBall.setHit();
                    break;
                }
            }

            // check for enemy collision
            if (intersects(e)) {
                hit(e.getDamage());
            }
        }
    }



    private void getNextPosition() {
        // movement of the player
        if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
        else if(right) {
            dx += moveSpeed;
            if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
        else {
            if(dx > 0) {
                dx -= stopSpeed;
                if(dx < 0) {
                    dx = 0;
                }
            }
            else if(dx < 0) {
                dx += stopSpeed;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }

        // the player cannot move while attacking, except in air
        if ((currentAction == SCRATCHING || currentAction == FIREBALL) &&
                !(jumping || falling)) {
            dx = 0;
        }

        // jumping
        if (jumping && !falling) {
            dy = jumpStart;
            falling = true;
        }

        // falling
        if (falling) {
            if (dy > 0 && gliding) {
                dy += fallSpeed * 0.05;
            } else {
                dy += fallSpeed;
            }

            if (dy > 0) {
                jumping = false;
            }
            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
            }

            if (dy > maxFallSpeed) {
                dy = maxFallSpeed;
            }
        }
    }

    // updating the player
    public void update() {
        if (y >= 270) {
            health = 0;
            dead = true;
        }

        if(dead) return;

        // updating position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        // checking if the attack has stopped
        if(currentAction == SCRATCHING) {
            if(animation.hasPlayedOnce()) scratching = false;
        }
        if(currentAction == FIREBALL) {
            if(animation.hasPlayedOnce()) firing = false;
        }

        // fireball attack
        fire += 1;
        if(fire > maxFire) fire = maxFire;
        if(firing && currentAction != FIREBALL) {
            if(fire > fireCost) {
                fire -= fireCost;
                FireBall fb = new FireBall(tileMap, facingRight);
                fb.setPosition(x, y);
                fireBalls.add(fb);
            }
        }

        // update fireballs
        for(int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).update();
            if(fireBalls.get(i).shouldRemove()) {
                fireBalls.remove(i);
                i--;
            }
        }

        // check if the player is done flinching
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 1000) {
                flinching = false;
            }
        }

        // setting animation for different states of the player
        if(scratching) {
            if(currentAction != SCRATCHING) {
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 60;
            }
        }
        else if(firing) {
            if(currentAction != FIREBALL) {
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 30;
            }
        } else if (gliding && jumping && left) {
            currentAction = GLIDING;
            animation.setFrames(sprites.get(GLIDING));
            animation.setDelay(100);
            width = 30;
        } else if(dy > 0) {
            if(gliding) {
                if(currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            }
            else if(currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        }
        else if(dy < 0) {
            if(currentAction != JUMPING) {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);
                width = 30;
            }
        }
        else if(left || right) {
            if(currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 30;
            }
        }
        else {
            if(currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }

        animation.update();

        // setting direction
        if(currentAction != SCRATCHING && currentAction != FIREBALL) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public double getHeight() {
        return y;
    }

    // Metoda pobierająca punkty
    public int getPoints() {
        return points;
    }

    // Metoda dodająca punkty
    public void addPoints(int amount) {
        points += amount;
    }

    // drawing the player
    public void draw(Graphics2D g) {
        if (dead) {
            // Dodaj tutaj kod rysowania stanu martwego gracza lub ekranu końca gry
            return;
        }

        // setting map position
        setMapPosition();

        // drawing fireballs
        for (FireBall fireBall : fireBalls) {
            fireBall.draw(g);
        }

        // drawing the player
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        super.draw(g);
    }

    public void stop() {
        running = false;
        try {
            playerLogicThread.join();
            playerGraphicsThread.join();
            playerMainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playerLogic() {
        while (running) {
            update();
            // Dodatkowa logika dla wątka logiki gracza
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void playerGraphics() {
        while (running) {
            // Aktualizacja grafiki gracza
            // (możesz dodać swoją logikę renderowania)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void playerMain() {
        while (running) {
            // Dodatkowa logika dla wątka głównego gracza
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
