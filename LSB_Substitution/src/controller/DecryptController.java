/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 *
 * @author nauq3
 */
public class DecryptController {

    private javax.swing.JFileChooser fileChooser;
    private view.DecryptView frame;
    private java.awt.image.BufferedImage currentImg;
    private String imgExtension;

    public DecryptController() {
        fileChooser = new  javax.swing.JFileChooser();
        frame = new view.DecryptView();
        
        frame.getBtnBroswe().addActionListener((ActionEvent ae) -> {
            openFile();
        });
        
        frame.getBtnShowMessage().addActionListener((ActionEvent ae) -> {
            String str = decrypt(currentImg);
            frame.getTxtSecretText().setText(str);
        });
        
        frame.setVisible(true);
    }

    
    
    
    /**
     *
     * @param bufImg
     * @return
     */
    private String decrypt(java.awt.image.BufferedImage bufImg) {
        String bits = "";
        
        String bit = "";
        
        a:
        for (int i = 0; i < bufImg.getWidth(); ++i) {
            for (int j = 0; j < bufImg.getHeight(); ++j) {
                bit += utility.Biswise.getLastBit(bufImg.getRGB(i, j));
                if (bit.length() == 8) {
                    if (Integer.parseInt(bit) != 0) {
                        bits += bit;
                    } else {
                        break a;
                    }
                    bit = "";
                }
                
            }
        }
        
        String[] strArr = bits.split("(?<=\\G........)");
        
        String result = "";
        for (String s : strArr) {
            int value = Integer.parseInt(s, 2);
            
            if (value == 0) {
                break;
            } else {
                result += (char) value;
                System.out.println("" + value);
            }
        }
        
        return result;
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
                frame.getTxtFilePath().setText(currentFile.getAbsolutePath());
                utility.ViewHelper.pushImgIntoLabel(currentImg, frame.getLblImage());
                
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(frame, "File is not image");
            }
        }
    }
}
