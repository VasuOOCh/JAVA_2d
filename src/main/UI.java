package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40;
    Font arial_80;
    BufferedImage keyImage;
    int messageCounter = 0;
    public boolean gameFinished = false;

    public boolean messageOn = false;
    public String message = "";
    public double playTime = 0;
    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public void showMessage(String m) {
        message = m;
        messageOn = true;
    }

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80 = new Font("Arial", Font.BOLD, 80);

        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void draw(Graphics2D g2) {

        if(gameFinished) {

            g2.setFont(arial_40); //setting the font
            // if we instantiate the font above, then it will instantiate it 60 times/sec, which is not efficient
            g2.setColor(Color.WHITE);

            String text;
            int textLength;

            text = "You found the treasure ! Hurray";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            int x = gp.screenWidth/2 - textLength/2;
            int y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text,x,y);

            text = "Time taken : " + decimalFormat.format(playTime);
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*4);
            g2.drawString(text,x,y);

            g2.setFont(arial_80);
            g2.setColor(Color.YELLOW);
            text = "Congratulations";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + (gp.tileSize*2);
            g2.drawString(text,x,y);

            gp.gameThread = null;// Stopping the game thread

        }else {

            g2.setFont(arial_40); //setting the font
            // if we instantiate the font above, then it will instantiate it 60 times/sec, which is not efficient
            g2.setColor(Color.WHITE);

            // TIME
            playTime += (double)1/60.0;
            g2.drawString("Time : " + decimalFormat.format(playTime),gp.tileSize*11, 50 );

            g2.drawImage(keyImage, 30,10,gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey,75,50);

            if(messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gp.tileSize/2,gp.tileSize*5);
                g2.setFont(arial_40);
                messageCounter++;
//            messageOn = false;
            }

            if(messageCounter >= 120) {
                messageOn = false;
                messageCounter = 0;
            }
        }


    }
}
