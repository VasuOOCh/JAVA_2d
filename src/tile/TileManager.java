package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        getTileImage();
        mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
        loadMap("/maps/world01.txt");
    }
    public void loadMap(String filePath) {
        try{
            InputStream is = getClass().getResourceAsStream(filePath); // importing the textFile
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); // to read the text file

            int col = 0;
            int row = 0;
            while(row < gp.maxWorldRow) {
//                System.out.println("Row is " + row);
                String line = br.readLine();

                String[] numbers = line.split(" ");
//                System.out.println(Arrays.toString(numbers));

                while(col < gp.maxWorldCol) {
//                    System.out.println("Col is "+ col);
                    mapTileNum[row][col] = Integer.parseInt(numbers[col]);
                    col++;
                }
                col = 0;
                row++;
            }
            br.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/old/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/old/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/old/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/old/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/old/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/old/sand.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // positions of that tile according to the row and col number
            int worldX = worldCol* gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // deciding where to draw the mapTileNum[worldRow][worldCol] :
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // rendering only the required tiles only (as seen on the screen) |**| Optimization
            if((screenX < (gp.screenWidth + gp.tileSize) && screenX > -1*gp.tileSize) && (screenY < (gp.screenHeight + gp.tileSize) && screenY > -1*gp.tileSize)) {
                g2.drawImage(tile[mapTileNum[worldRow][worldCol]].image, screenX,screenY,gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
