package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    private Icon dir = new ImageIcon("folder.png");
    private Icon docx = new ImageIcon("docx.png");
    private Icon pdf = new ImageIcon("pdf.png");
    private Icon txt = new ImageIcon("txt.png");
    private Icon setting = new ImageIcon("setting.png");

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,boolean selected, boolean expanded,boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();
        if (userObject instanceof Node) {
            Node nodeData = (Node) userObject;            
            if (nodeData.isDocx()) {
                setIcon(docx);
            } else if (nodeData.isFolder()) {
                setIcon(dir);
            } else if (nodeData.isPdf()) {
                setIcon(pdf);
            } else if (nodeData.isSetting()) {
                setIcon(setting);
            } else if (nodeData.isTxt()) {
                setIcon(txt);
            } else {
                setIcon(null);
            }
            
        }
        return this;
    }
}
