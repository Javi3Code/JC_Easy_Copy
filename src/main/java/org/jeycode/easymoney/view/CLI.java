package main.java.org.jeycode.easymoney.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CLI extends JFrame
{

      private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit()
                                                          .getScreenSize();

      public CLI()
      {
            setSize((int)SCREEN_SIZE.getWidth() >> 3,(int)SCREEN_SIZE.getHeight() >> 4);
            setAlwaysOnTop(true);
            setUndecorated(true);
            setAutoRequestFocus(true);
            Container backPanel = getContentPane();
            JTextArea commandLine = new JTextArea("Holaaa");
            commandLine.setFont(new Font("Arial",Font.BOLD,14));
            commandLine.setForeground(Color.LIGHT_GRAY);
            commandLine.setBackground(Color.decode("#190b02"));
            backPanel.add(commandLine);
            setLocationRelativeTo(null);
            setVisible(true);
      }

      private static final long serialVersionUID = 3097315771562710818L;

}
