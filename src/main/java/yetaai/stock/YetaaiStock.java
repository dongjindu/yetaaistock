/*
 * Copyright (c) 2014, Yetaai
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package yetaai.stock;

import java.awt.Container;
import java.awt.EventQueue;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import org.apache.log4j.Level;

import org.apache.pivot.wtk.DesktopApplicationContext;
import org.openqa.selenium.android.library.Logger;
import yetaai.stock.gui.MasterWindow;

import yetaai.stock.gui.PivotMain;
import yetaai.stock.tools.YetaaiApplication;

/**
 *
 * @author Yetaai
 */
public class YetaaiStock {

    public static HashMap<String, String> props = YetaaiApplication.getProps();

    public static void main(String[] arguments) {
        try {

            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            org.apache.log4j.Logger.getLogger(YetaaiStock.class.getName()).log(Level.ERROR, YetaaiStock.class.getName() + ": Exception in Main() set look and feel");
        }
        try {
            /* Turn off metal's use of bold fonts */
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    start();
                }
            });
        } catch (Exception e) {
            org.apache.log4j.Logger.getLogger(YetaaiStock.class.getName()).log(Level.ERROR, YetaaiStock.class.getName() + ": Exception in Main() starting swing thread");
        }
    }

    public static void start() {
        JFrame jf;
        jf = new MasterWindow();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setTitle("Yetaai Stock");
//        frame.setBounds(10, 10, 630, 470); //Generally used for JComponents position and size when no layout is used.we can set layout manager to null.
        ((MasterWindow) jf).makeUI();
        jf.setSize(640, 620);
        //frame.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.pack();
        jf.setVisible(true);
        jf.toFront();

    }
}
