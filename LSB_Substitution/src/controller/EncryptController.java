/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nauq3
 */
public class EncryptController {

    private javax.swing.JFileChooser fileChooser;
    private view.EncryptView frame;
    private java.awt.image.BufferedImage currentImg;
    private java.awt.image.BufferedImage encyptedImg;
    private String imgExtension;
    
    /**
     *
     */
    public EncryptController() {
        fileChooser = new javax.swing.JFileChooser();
        frame = new view.EncryptView();

        frame.getBtnBrowse().addActionListener((ActionEvent ae) -> {
            openFile();
        });
        
        frame.getBtnHideMessage().addActionListener((ActionEvent ae) -> {
            encyptedImg = encrypt(frame.getTxtSecretText().getText());
            if (encyptedImg == null) {
                javax.swing.JOptionPane.showMessageDialog(frame, "Message is too big for this img");
            } else {
                utility.ViewHelper.pushImgIntoLabel(encyptedImg, frame.getLblStegoImg());
            }
        });
        
        frame.getBtnSaveImage().addActionListener((ActionEvent ae) -> {
            saveFile(encyptedImg);
        });

        frame.setVisible(true);
    }

    /**
     *
     */
    private void openFile() {
        int result = fileChooser.showDialog(frame, "Choose image file to open");

        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File currentFile = fileChooser.getSelectedFile();
            
            try {
                currentImg = javax.imageio.ImageIO.read(currentFile);
                frame.getTxtImageFile().setText(currentFile.getAbsolutePath());
                utility.ViewHelper.pushImgIntoLabel(currentImg, frame.getLblOriginalImg());
                imgExtension = getFileExtension(currentFile);
                
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(frame, "File is not image");
            }
        }
    }
    
    /**
     * 
     * @param bufImg 
     */
    private void saveFile(java.awt.image.BufferedImage bufImg) {
        int result = fileChooser.showSaveDialog(frame);
        
        if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File currentFile = fileChooser.getSelectedFile();
            
            try {
                boolean s = javax.imageio.ImageIO.write(bufImg, imgExtension, currentFile);
                System.out.println("" + s);
            } catch (IOException ex) {
                Logger.getLogger(EncryptController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * 
     * @param message
     * @return 
     */
    private java.awt.image.BufferedImage encrypt(String message) {
        java.awt.image.BufferedImage newImg = copyImage(currentImg);
                
        int width = newImg.getWidth();
        int height = newImg.getHeight();

        int count = 0;
        String bits = utility.Biswise.toBitString(message);
        
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                
                //  Set new RGB
                if (count < bits.length()) {
                    int oldRGB = newImg.getRGB(i, j);
                    
                    System.out.println("" + bits.charAt(count));
                    
                    int newRGB = utility.Biswise.setLastBit(oldRGB, Integer.parseInt(bits.charAt(count) + ""));
                    newImg.setRGB(i, j, newRGB);
                    ++count;
                
                // Set null char
                } else if (count < bits.length() + 8) {
                    Integer oldRGB = newImg.getRGB(i, j);
                    newImg.setRGB(i, j, utility.Biswise.setLastBit(oldRGB, 0));
                    ++count;
                
                // Return img
                } else {
                    return newImg;
                }
            }
        }
        
        //  Mess too big
        return null;
    }

    /**
     * 
     * @return 
     */
    private java.awt.image.BufferedImage copyImage(java.awt.image.BufferedImage oldImg) {
        java.awt.image.BufferedImage newImg = new java.awt.image.BufferedImage(
                oldImg.getWidth(), 
                oldImg.getHeight(), 
                oldImg.getType()
        );
        java.awt.Graphics g = newImg.getGraphics();
        g.drawImage(oldImg, 0, 0, null);
        g.dispose();
        return newImg;
    }
    
    /**
     * 
     * @param file
     * @return 
     */
    private String getFileExtension(java.io.File file) {
        String fileName = file.getName();
        
        int i = fileName.lastIndexOf('.');
        
        if (i > 0) {
            return fileName.substring(i+1);
        } else {
            return null;
        }
    }
    
}
