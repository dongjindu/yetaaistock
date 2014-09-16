/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yetaai.stock.gui;

import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author oefish
 */
public class MasterWindow extends JFrame{
    MasterWindow() {
        
    }
    public void makeUI() {
        String toolbartype = "Master";
        Container cp = this.getContentPane();
        JPanel jpanel = new ToolBarPanel(toolbartype);
        cp.add((JComponent) jpanel);
    }
}
