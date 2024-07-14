/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Noorzai
 */
public class PanelRight extends javax.swing.JPanel implements Observer {

    private Icon dir = new ImageIcon("folder.png");
    private Icon docx = new ImageIcon("docx.png");
    private Icon pdf = new ImageIcon("pdf.png");
    private Icon txt = new ImageIcon("txt.png");
    private Icon setting = new ImageIcon("setting.png");
    private Observable observable;
    DefaultTableModel dtm;

    public PanelRight(Observable observable) {
        this.observable = observable;
        observable.register(this);
        
        initComponents();
        dtm = (DefaultTableModel) tbl.getModel();
        tbl.getColumnModel().getColumn(0).setCellRenderer(new IconRenderer());
        tbl.getColumnModel().getColumn(0).setMaxWidth(40);
        TableCellRenderer renderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    ((JLabel) c).setHorizontalAlignment(JLabel.CENTER); // Center align text
                }
                return c;
            }
        };
        tbl.getColumnModel().getColumn(1).setCellRenderer(renderer);
        tbl.getColumnModel().getColumn(2).setCellRenderer(renderer);
        tbl.getColumnModel().getColumn(3).setCellRenderer(renderer);
        tbl.getColumnModel().getColumn(4).setCellRenderer(renderer);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Icon", "Path", "Name", "Last Date Modified", "Size"
            }
        ));
        tbl.setRowHeight(30);
        jScrollPane2.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(40);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(40);
            tbl.getColumnModel().getColumn(0).setMaxWidth(40);
            tbl.getColumnModel().getColumn(4).setMinWidth(80);
            tbl.getColumnModel().getColumn(4).setPreferredWidth(80);
            tbl.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void onNewUpdate(TreePath treePath) {
        File target = new File(getFilePathFromTreePath(treePath));
        if (target.isDirectory()) {
            dtm.setRowCount(0);
            File[] f = target.listFiles();
            for (File file : f) {
                String name = file.getName();
                String path = file.getAbsolutePath();
                double size = 0;
                Icon icon = null;
                if (!file.isDirectory()) {
                    size = (double) file.length() / (1024 * 1024);
                }
                if (file.getName().endsWith(".pdf")) {
                    icon = pdf;
                }
                if (file.getName().endsWith(".docx")) {
                    icon = docx;
                }
                if (name.toLowerCase().endsWith(".ini") || name.toLowerCase().endsWith(".bat") || name.toLowerCase().endsWith(".sys") || name.toLowerCase().endsWith(".bin") || name.toLowerCase().endsWith(".bin") || name.toLowerCase().endsWith(".msi") || name.toLowerCase().endsWith(".tmp")) {
                    icon = setting;
                }
                if (file.getName().endsWith(".txt")) {
                    icon = txt;
                }
                if ((!file.getName().contains("."))) {
                    icon = dir;
                }
                Date date = new Date(file.lastModified());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
                Object row[] = {icon, path, name,formatter.format(date),String.format("%.2f", size) + " MB"};
                dtm.addRow(row);
            }
        }
    }

    public String getFilePathFromTreePath(TreePath treePath) {
        Object[] nodes = treePath.getPath();
        String name = "";
        for (int i = 0; i < nodes.length; i++) {
            Object node = nodes[i];
            if (node instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
                Object userTreeNode = treeNode.getUserObject();
                if (userTreeNode instanceof Node) {
                    Node userNode = (Node) userTreeNode;
                    if (!userNode.getNodeName().equals("File System")) {
                        name += userNode.getNodeName() + "\\";
                    }
                }
            }
        }
        return name;
    }

    public class IconRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {

            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                setIcon(null);
            }
            setHorizontalAlignment(SwingConstants.CENTER);
            setText("");

            return this;
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl;
    // End of variables declaration//GEN-END:variables
}
