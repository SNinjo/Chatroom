package MVC;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.SystemColor;

import javax.swing.JFrame;

public class View {

    private JFrame frame;
    private Container container;

    View(){
        frame = new JFrame();
        frame.setTitle("歷年成績");
        container = frame.getContentPane();
        container.setLayout(new BorderLayout(0, 0));
        container.setBackground(SystemColor.activeCaptionBorder);
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        frame.setVisible(true);
    }

}
