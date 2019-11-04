/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author nauq3
 */
public class ViewHelper {
    
    /**
     * 
     * @param bufImg
     * @param label 
     */
    public static void pushImgIntoLabel(java.awt.image.BufferedImage bufImg, javax.swing.JLabel label) {
        double ws = ((double)label.getWidth()) / bufImg.getWidth();
        double hs = ((double)label.getHeight()) / bufImg.getHeight();
        
        double newScale = Math.min(hs, ws);
        
        int newWidth = (int)(bufImg.getWidth() * newScale);
        int newHeigth = (int)(bufImg.getHeight()* newScale);
        
        java.awt.Image img = bufImg.getScaledInstance(
                newWidth,
                newHeigth,
                java.awt.Image.SCALE_REPLICATE
        );
        label.setIcon(new javax.swing.ImageIcon(img));
    }

}
