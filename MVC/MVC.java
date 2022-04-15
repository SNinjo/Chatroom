package MVC;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.SystemColor;

import javax.swing.JFrame;

// 單獨檔案的 MVC 格式
public class MVC {
    public static void main(String[] args) {
        // Debug
        new MVC();
    }

    public MVC(){
        new Controller(new Model(), new View());
    }

    private class Controller {
        private Model model;
        private View view;

        Controller(Model m, View v){
            this.model = m;
            this.view = v;

            // init Controller

        }
    }

    private class Model {
        Model(){}
    }

    private class View {
        private JFrame frame;
        private Container container;

        View(){
            frame = new JFrame();
            frame.setTitle("Title");
            container = frame.getContentPane();
            container.setLayout(new BorderLayout(0, 0));
            container.setBackground(SystemColor.activeCaptionBorder);
            frame.setBounds(100, 100, 500, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


            frame.setVisible(true);
        }
    }

}
