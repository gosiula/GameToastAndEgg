package org.example.TileMap;
import org.example.Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

// READING, DRAWING AND UPDATING THE TILEMAP
public class TileMap implements Runnable{
    // position x and y of the tile
    private double x;
    private double y;

    // bounds of the tile
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    // smooth transitioning factor
    private double tween;

    // map of the tiles
    private int[][] map;
    private final int tileSize;
    private int numberOfRows;
    private int numberOfColumns;
    private int numTilesAcross;
    private Tile[][] tiles;

    // drawing the tiles
    private int rowOffset;
    private int colOffset;
    private final int numRowsToDraw;
    private final int numColsToDraw;

    // running the thread in GamePanel
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // TileMap constructor
    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }

    // loading tiles
    public void loadTiles(String s) {
        try {
            // tileset info - image, number of tiles
            BufferedImage tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(s)));
            numTilesAcross = tileset.getWidth() / tileSize;

            // 2D array for different tile types
            tiles = new Tile[2][numTilesAcross];

            // sub image of the image
            BufferedImage subImage;
            for (int col = 0; col < numTilesAcross; col++) {
                // extract sub images for normal and blocked tiles
                subImage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // loading map from a text file
    public void loadMap(String s) {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            assert in != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numberOfColumns = Integer.parseInt(br.readLine());
            numberOfRows = Integer.parseInt(br.readLine());
            map = new int[numberOfRows][numberOfColumns];
            int width = numberOfColumns * tileSize;
            int height = numberOfRows * tileSize;

            xMin = GamePanel.WIDTH - width;
            xMax = 0;

            yMin = GamePanel.HEIGHT - height;
            yMax = 0;

            // regular expression for whitespace
            String delimiters = "\\s+";

            for (int row = 0; row < numberOfRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for (int column = 0; column < numberOfColumns; column++) {
                    map[row][column] = Integer.parseInt(tokens[column]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // getting x
    public double getx() {
        return x;
    }

    // getting y
    public double gety() {
        return y;
    }

    // getting the type of the tile
    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getTileType();
    }

    // getting the type of specific tile
    public void setTween(double d) {
        tween = d;
    }

    // setting position of the tilemap
    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    // ensuring the tilemap stays within the specified bounds
    private void fixBounds() {
        if (x < xMin) x = xMin;
        if (y < yMin) y = yMin;
        if (x > xMax) x = xMax;
        if (y > yMax) y = yMax;
    }

    // drawing the tilemap
    public void draw(Graphics2D g) {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numberOfRows) break;

            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numberOfColumns) break;
                if (map[row][col] == 0) continue;

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(
                        tiles[r][c].getImage(),
                        (int) x + col * tileSize,
                        (int) y + row * tileSize,
                        null
                );
            }
        }
    }
}
