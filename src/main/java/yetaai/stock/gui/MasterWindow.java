/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yetaai.stock.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout.Constraints;

/**
 *
 * @author oefish
 */
public class MasterWindow extends JFrame{
    public MasterWindow() {
        makeUI();
    }

    public void makeUI() {
        String toolbartype = "Master";
        JPanel jp = new ToolBarPanel(toolbartype);
        Container cp = this.getContentPane();
        LayoutManager layout = new BorderLayout();
        cp.setLayout(layout);
        cp.add((JComponent) jp, BorderLayout.NORTH);
    }
}
