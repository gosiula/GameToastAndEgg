package org.example.Entity;
import java.awt.Rectangle;
import org.example.TileMap.TileMap;
import org.example.TileMap.Tile;

// THE GENERAL FUNCTIONS OF THE MAP OBJECTS
public abstract class MapObject {
    // tile parameters
    protected TileMap tileMap;
    protected int tileSize;
    protected double xMap;
    protected double yMap;

    // position and vector
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;

    // dimensions
    protected int width;
    protected int height;

    // collision box
    protected int cWidth;
    protected int cHeight;

    // collision
    protected int currentRow;
    protected int currentColumn;
    protected double xDestination;
    protected double yDestination;
    protected double xTemporary;
    protected double yTemporary;
    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    // animation
    protected Animation animation;
    protected int currentAction;
    protected boolean facingRight;

    // movement destination
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean jumping;
    protected boolean falling;

    // movement attributes
    protected double moveSpeed;
    protected double maxSpeed;
    protected double stopSpeed;
    protected double fallSpeed;
    protected double maxFallSpeed;
    protected double jumpStart;
    protected double stopJumpSpeed;

    // MapObject constructor
    public MapObject(TileMap tm) {
        tileMap = tm;
        tileSize = tm.getTileSize();
    }

    // checking the intersection
    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    // getting the rectangle (to later be able to check the collisions)
    public Rectangle getRectangle() {
        return new Rectangle(
                (int)x - cWidth,
                (int)y - cHeight,
                cWidth,
                cHeight
        );
    }

    // calculate collision corners
    public void calculateCorners(double x, double y) {

        int leftTile = (int)(x - cWidth / 2) / tileSize;
        int rightTile = (int)(x + cWidth / 2 - 1) / tileSize;
        int topTile = (int)(y - cHeight / 2) / tileSize;
        int bottomTile = (int)(y + cHeight / 2 - 1) / tileSize;

        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;

    }

    // checking the tile map collisions
    public void checkTileMapCollision() {
        currentColumn = (int)x / tileSize;
        currentRow = (int)y / tileSize;

        xDestination = x + dx;
        yDestination = y + dy;

        xTemporary = x;
        yTemporary = y;

        calculateCorners(x, yDestination);

        if(dy < 0) {
            if(topLeft || topRight) {
                dy = 0;
                yTemporary = currentRow * tileSize + cHeight / (float)2;
            }
            else {
                yTemporary += dy;
            }
        }
        if(dy > 0) {
            if(bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                yTemporary = (currentRow + 1) * tileSize - cHeight / (float)2;
            }
            else {
                yTemporary += dy;
            }
        }

        calculateCorners(xDestination, y);

        if(dx < 0) {
            if(topLeft || bottomLeft) {
                dx = 0;
                xTemporary = currentColumn * tileSize + cWidth / (float)2;
            }
            else {
                xTemporary += dx;
            }
        }
        if(dx > 0) {
            if(topRight || bottomRight) {
                dx = 0;
                xTemporary = (currentColumn + 1) * tileSize - cWidth / (float)2;
            }
            else {
                xTemporary += dx;
            }
        }

        if(!falling) {
            calculateCorners(x, yDestination + 1);
            if(!bottomLeft && !bottomRight) {
                falling = true;
            }
        }
    }

    // getting location x
    public int getx() { return (int)x; }

    // getting location y
    public int gety() { return (int)y; }

    // setting the position
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // setting map position
    public void setMapPosition() {
        xMap = tileMap.getx();
        yMap = tileMap.gety();
    }

    // setting in different directions
    public void setLeft(boolean b) { left = b; }
    public void setRight(boolean b) { right = b; }
    public void setUp(boolean b) { up = b; }
    public void setJumping(boolean b) { jumping = b; }

    // drawing the object
    public void draw(java.awt.Graphics2D g) {
        if(facingRight) {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xMap - width / 2),
                    (int)(y + yMap - height / 2),
                    null
            );
        }
        else {
            g.drawImage(
                    animation.getImage(),
                    (int)(x + xMap - width / 2 + width),
                    (int)(y + yMap - height / 2),
                    -width,
                    height,
                    null
            );
        }
    }
}
