
import gui.FrameMain;
import gui.PanelBottom;
import gui.PanelLeft;
import gui.PanelMain;
import gui.PanelRight;
import java.awt.Toolkit;
import javax.swing.JFrame;
import java.awt.Dimension;


public class Main {
    public static void main(String[] args) {
        PanelLeft panelLeft = new PanelLeft();
        PanelRight panelRight = new PanelRight(panelLeft);        
        PanelBottom panelBottom = new PanelBottom(panelLeft);
        PanelMain panelMain = new PanelMain(panelBottom, panelLeft, panelRight);
        FrameMain frameMain = new FrameMain(panelMain);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frameMain.setSize((int)dimension.getWidth(),(int)dimension.getHeight()-30);
        frameMain.setVisible(true);
    }
}
