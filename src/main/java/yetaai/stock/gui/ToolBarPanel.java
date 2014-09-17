/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yetaai.stock.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import javax.swing.Box;
import org.apache.log4j.Level;

/**
 *
 * @author HIS20166
 */
public class ToolBarPanel extends JPanel implements ActionListener {

    public ToolBarPanel(String toolbartypename) {
//     Consider reflection optimization   sun.reflect.inflationThreshold = 2;
        JToolBar toolbar = new JToolBar("Yetaai Stock toolbar");
        //toolbar.setLayout(null);
        toolbar.setRollover(true);
        addButons(toolbar);
        setPreferredSize(new Dimension(450, 130));
        add(toolbar, BorderLayout.PAGE_START);
        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addButons(JToolBar toolbar) {
        
        Dimension d = new Dimension(100, 30);
        JButton button2 = new JButton("Minimize");
        button2.setActionCommand("Minimize");
        button2.addActionListener((ActionListener)this);
        button2.setMaximumSize(d);button2.setMinimumSize(d);
        toolbar.add(button2);

        JButton button1 = new JButton("Exit");
        button1.setActionCommand("Exit by ESC");
        button1.addActionListener((ActionListener)this);
        button1.setMaximumSize(d); button1.setMinimumSize(d);
        toolbar.add(button1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        org.apache.log4j.Logger.getLogger(this.getClass().getName()).log(Level.ERROR, "Command String:" + e.getActionCommand());
        
    }

}
