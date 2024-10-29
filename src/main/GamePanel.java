package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen setting :
    final int originalTileSize = 16;
    final int scale = 3;

    int FPS = 60;

    public final int tileSize = originalTileSize*scale; // 48X48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = maxScreenCol * tileSize; // 48*16 = 768
    public final int screenHeight = maxScreenRow * tileSize; // 48*12 = 576

    // WORLDMAP parameters :
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    KeyHandler keyH = new KeyHandler();

    Thread gameThread; // this is the game clock, if this stops the game stops
    // to use thread you need to use 'implements Runnable' in the class

    public Player player = new Player(this, keyH);
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public SuperObject[] obj = new SuperObject[10];
    public Sound music = new Sound();
    public Sound se = new Sound();
    public UI ui = new UI(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight)); // setting the screen size
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // learn this
        this.addKeyListener(keyH);
        this.setFocusable(true); // with this the game panel is focused to receive any input
    }

    public void setUpGame() { // we will be calling this method before the game starts , i.e before starting the thread
        assetSetter.setObject();

        //PLAY MUSIC
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // now run method will be started
    }

    // this is imp for thread
    @Override
    public void run() {
        // this is automatically called when the thread starts

        double drawInterval = 1000000000/FPS; // (1 sec / FPS)
        double nextDrawTime = System.nanoTime() + drawInterval;
        // Logic : 1 sec --> 60 Frames
        // So : 1Frame = (1000000000 / 60) nanoseconds

        long initTime = System.nanoTime();
        int count = 0; // for displaying the current FPS
        // defining the gameloop :-
        while(gameThread != null) {

            // if we do not restrict this game loop, then it may run 1 million times per second
//            System.out.println(" game loop is running");

                // UPDATE the information such as character position
                update();
                // and draw the screen according to the update
                repaint();

                count++;
                if(System.nanoTime()  - initTime >= 1000000000) {
                    System.out.println("FPS : " + count);
                    count = 0;
                    initTime = System.nanoTime();
                }

            try {
                long remainingTime = (long) (nextDrawTime - System.nanoTime());
                remainingTime = remainingTime/1000000;  // we need it in milliseconds

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // boilerplate
        Graphics2D g2 = (Graphics2D)g; // converting g -> Graphics2D

        // TILE
        tileManager.draw(g2);

        // ASSET (Objects)
        assetSetter.drawObjects(g2);

        // PLAYER
        player.draw(g2);
        // player should be drawn later to overlap the tiles

        ui.draw(g2);
        // UI should be drawn at last
        g2.dispose(); // good practice to save system resources
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void stopSE(int i) {
        se.stop();
    }
}
