package org.example.TileMap;
import java.awt.image.BufferedImage;

// HANDLING THE IMAGE AND TYPE OF A SINGLE TILE
public class Tile {
    // image
    private final BufferedImage image;

    // type of the tile
    private final int tileType;

    // tile types - normal and blocked
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    // Tile constructor
    public Tile(BufferedImage image, int type) {
        this.image = image;
        this.tileType = type;
    }

    // getting the image
    public BufferedImage getImage() { return image; }

    // getting the tile type
    public int getTileType() { return tileType; }

}

