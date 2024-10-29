package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed,downPressed,leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
//            System.out.println("forward");
            upPressed = true;
        }else if(code == KeyEvent.VK_S) {
//            System.out.println("Backward");
            downPressed = true;
        }else if(code == KeyEvent.VK_A) {
//            System.out.println("Left");
            leftPressed = true;
        }else if(code == KeyEvent.VK_D) {
//            System.out.println("Right");
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }else if(code == KeyEvent.VK_S) {
            downPressed = false;
        }else if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }else if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}