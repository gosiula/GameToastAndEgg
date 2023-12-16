package org.example.TileMap;

import org.example.Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileMap implements Runnable{
    private double x;
    private double y;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private double tween;
    private int[][] map;
    private final int tileSize;
    private int numberOfRows;
    private int numberOfColumns;
    private int numTilesAcross;
    private Tile[][] tiles;
    private int rowOffset;
    private int colOffset;
    private final int numRowsToDraw;
    private final int numColsToDraw;

    // Nowe zmienne do obsługi wielowątkowości
    private Thread tileMapLogicThread;
    private Thread tileMapGraphicsThread;
    private Thread tileMapMainThread;
    private volatile boolean running = true;

    @Override
    public void run() {
        // Logika wątku TileMap
        while (running) {
            tileMapLogic();
            tileMapGraphics();
            tileMapMain();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;

        startThreads();
    }

    private void startThreads() {
        tileMapLogicThread = new Thread(this::tileMapLogic);
        tileMapGraphicsThread = new Thread(this::tileMapGraphics);
        tileMapMainThread = new Thread(this::tileMapMain);

        tileMapLogicThread.start();
        tileMapGraphicsThread.start();
        tileMapMainThread.start();
    }

    // Metoda zatrzymująca wątek TileMap
    public void stopThreads() {
        running = false;
        try {
            tileMapLogicThread.join();
            tileMapGraphicsThread.join();
            tileMapMainThread.join();
            GamePanel.tileMapThread.interrupt(); // Przerwanie wątku TileMap
            GamePanel.tileMapThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loadTiles(String s) {
        try {
            BufferedImage tileset = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(s)));
            numTilesAcross = tileset.getWidth() / tileSize;

            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;
            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public int getTileSize() {
        return tileSize;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public int getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return tiles[r][c].getTileType();
    }

    public void setTween(double d) {
        tween = d;
    }

    public void setPosition(double x, double y) {
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    private void fixBounds() {
        if (x < xMin) x = xMin;
        if (y < yMin) y = yMin;
        if (x > xMax) x = xMax;
        if (y > yMax) y = yMax;
    }

    // Metoda zwracająca wysokość mapy
    public int getHeight() {
        return numberOfColumns * tileSize;  // Przyjmujemy, że tileSize to wysokość jednego kafelka
    }

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

    // Dodatkowe metody do obsługi logiki, grafiki i wątka głównego dla TileMap
    private void tileMapLogic() {
        while (running) {
            // Aktualizacja logiki TileMap
            // (możesz dodać swoją logikę)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tileMapGraphics() {
        while (running) {
            // Aktualizacja grafiki TileMap
            // (możesz dodać swoją logikę renderowania)
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tileMapMain() {
        while (running) {
            // Dodatkowa logika dla wątka głównego TileMap
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
