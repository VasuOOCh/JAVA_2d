package entity;

import main.GamePanel;
import main.KeyHandler;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public int hasKey = 0;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(8,16,32, 32);
        solidAreaDefaultX = solidArea.x;
        getSolidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = 23*gp.tileSize;
        worldY = 23*gp.tileSize;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/walking/boy_right_2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() { // this update method will be called 60 times per second

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // we added this additional if statement to stop the spriteCounter even when we are not
            // pressing any key


            if(keyH.upPressed) {
                direction = "up";
//                worldY -= speed;
            }else if(keyH.downPressed) {
                direction = "down";
//                worldY += speed;
            }else if(keyH.leftPressed) {
                direction = "left";
//                worldX -= speed;
            }else if(keyH.rightPressed) {
                direction = "right";
//                worldX += speed;
            }

            // Checking the tile collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // Checking the object collision
            int objIndex = gp.collisionChecker.checkObject(this,true);
            pickUpObject(objIndex);

            // If collision is false player can move
            if(!collisionOn) {
                switch (direction) {
                    case "up" :
                        worldY -= speed;
                        break;
                    case "down" :
                        worldY += speed;
                        break;
                    case "left" :
                        worldX -= speed;
                        break;
                    case "right" :
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }else {
                    spriteNum = 1;
                }

                spriteCounter = 0;
            }
        }


    }

    public void pickUpObject(int i) {
        if(i != 999) {
            String objName = gp.obj[i].name;

            switch (objName) {
                case "Key" :
                    hasKey++;
                    gp.obj[i] = null;
                    gp.playSE(1);
                    gp.ui.showMessage("Key found ! Get inside the door");
                    break;
                case "Door" :
                    if(hasKey > 0) {
                        gp.obj[i] = null;
                        gp.playSE(3);
                        gp.ui.showMessage("Knock knock !");
                        hasKey--;
                    }else {
                        gp.ui.showMessage("Get a key noobie.");
                    }
                    break;
                case "Chest" :
                    gp.ui.showMessage("Hurray !");
                    gp.ui.gameFinished = true;
                    gp.music.stop();
                    gp.playSE(4);
                    break;
                case "Boots" :
                    speed += 2;
                    gp.obj[i] = null;
                    gp.playSE(2);
                    gp.ui.showMessage("Shiny shoes :)");
            }

        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x,y,tileSize,tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up" :
                if(spriteNum == 1) {
                    image = up1;
                }else {
                    image = up2;
                }
                break;
            case "down" :
                if(spriteNum == 1) {
                    image = down1;
                }else {
                    image = down2;
                }
                break;
            case "left" :
                if(spriteNum == 1) {
                    image = left1;
                }else {
                    image = left2;
                }
                break;
            case "right" :
                if(spriteNum == 1) {
                    image = right1;
                }else {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image,screenX,screenY, gp.tileSize, gp.tileSize, null);
    }
}

