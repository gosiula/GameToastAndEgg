package org.example.Entity;
import org.example.TileMap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import static org.example.Music.Music.*;

// CREATING A PLAYER AND DEFINING THE WORKING OF MOVEMENT, ATTACKS, ANIMATION
// AND THE INTERACTION WITH OTHER OBJECTS
public class Player extends MapObject implements Runnable{

    // player parameters
    private int health;
    private final int maxHealth;
    private int fire;
    private final int maxFire;
    private boolean flinching;
    private long flinchTimer;
    public boolean dead;

    // fireball parameters
    private boolean firing;
    private final int fireCost;
    private final int fireballDamage;
    private final ArrayList<Fireball> fireballs;

    // punching
    private boolean punching;
    private final int punchDamage;
    private final int punchRange;

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
    private static final int PUNCHING = 6;

    //private volatile boolean running = true;

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
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -4.8;
        stopJumpSpeed = 0.3;

        facingRight = true;

        // player health and fireballs
        health = maxHealth = 5;
        fire = maxFire = 500;
        fireCost = 200;
        fireballDamage = 5;
        fireballs = new ArrayList<>();

        // punching
        punchDamage = 8;
        punchRange = 40;

        dead = false;

        points = 0;

        // loading sprites
        try {
            BufferedImage spriteSheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(
                            "/Player/henio_the_bread.png"
                    ))
            );

            // getting the desired sub image
            sprites = new ArrayList<>();
            for(int i = 0; i < 7; i++) {
                int[] numFrames = {9, 2, 1, 1, 4, 3, 5};
                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                for(int j = 0; j < numFrames[i]; j++) {
                    if(i != PUNCHING) {
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
    }


    // running the thread in GamePanel
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            update();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // getting the parameters of the player
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getFire() { return fire; }
    public int getMaxFire() { return maxFire; }

    public void setFiring() {
        firing = true;
    }
    public void setPunching() {
        punching = true;
    }
    public void setGliding(boolean b) {
        gliding = b;
    }

    // the player being hit and flinching
    public void hit(int damage) {
        if(flinching || dead) return;
        health -= damage;
        ouchSound();
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
            // punch attack
            if (punching) {
                if (facingRight) {
                    if (e.getx() > x && e.getx() < x + punchRange && e.gety() > y - (float)height / 2 && e.gety() < y + (float)height / 2) {
                        e.hit(punchDamage);
                    }
                } else {
                    if (e.getx() < x && e.getx() > x - punchRange && e.gety() > y - (float)height / 2 && e.gety() < y + (float)height / 2) {
                        e.hit(punchDamage);
                    }
                }
            }

            // fireballs
            for (Fireball fireball : fireballs) {
                if (fireball.intersects(e)) {
                    e.hit(fireballDamage);
                    fireball.setHit();
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
        if ((currentAction == PUNCHING || currentAction == FIREBALL) &&
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
            dead = true;
            health = 5;
        }

        if(dead) return;

        // updating position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xTemporary, yTemporary);

        // checking if the attack has stopped
        if(currentAction == PUNCHING) {
            if(animation.hasPlayedOnce()) punching = false;
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
                Fireball fb = new Fireball(tileMap, facingRight);
                fb.setPosition(x, y);
                fireballs.add(fb);
            }
        }

        // update fireballs
        for(int i = 0; i < fireballs.size(); i++) {
            fireballs.get(i).update();
            if(fireballs.get(i).shouldRemove()) {
                fireballs.remove(i);
                i--;
            }
        }

        // check if the player is done flinching
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 1000) {
                flinching = false;
            }
        }

        // setting animation for different states of the player
        if(punching) {
            if(currentAction != PUNCHING) {
                punchingSound();
                currentAction = PUNCHING;
                animation.setFrames(sprites.get(PUNCHING));
                animation.setDelay(50);
                width = 60;
            }
        }
        else if(firing) {
            if(currentAction != FIREBALL) {
                fireballSound();
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
        if(currentAction != PUNCHING && currentAction != FIREBALL) {
            if(right) facingRight = true;
            if(left) facingRight = false;
        }
    }

    // checking if the player is dead
    public boolean isDead() {
        if(dead) { health = 5; }
        return dead;
    }

    // checking if the player is alive
    public void isAlive(boolean alive) {
        dead = alive;
        if(!alive) health = 5;
    }

    // getting the height of the player
    public double getHeight() {
        return y;
    }

    // getting the points of the player
    public int getPoints() {
        return points;
    }

    // adding the points for the player
    public void addPoints(int amount) {
        points += amount;
    }

    // drawing the player
    public void draw(Graphics2D g) {
        // setting map position
        setMapPosition();

        // drawing fireballs
        for (Fireball fireball : fireballs) {
            fireball.draw(g);
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
}
