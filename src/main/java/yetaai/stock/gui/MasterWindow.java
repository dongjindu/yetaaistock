/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yetaai.stock.gui;

import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author oefish
 */
public class MasterWindow extends JFrame{
    MasterWindow() {
        
    }
    public void makeUI() {
        Container cp = this.getContentPane();
        addMasterButton();
    }
}
