package gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class PanelLeft extends javax.swing.JPanel implements TreeWillExpandListener, Observable, ActionListener {

    DefaultTreeModel defaultTreeModel;
    List<Observer> observers;

    public PanelLeft() {
        observers = new ArrayList<>();
        initComponents();
        initTree();
        jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jtree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                notifyAllObservers(e.getPath());
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtree = new javax.swing.JTree();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(jtree);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jtree;
    // End of variables declaration//GEN-END:variables

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        if (event.getPath().toString().equals("[File System]")) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getChildAt(i);
                loadDirectoryContents(node, node.toString());
            }
        } else {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
            String currentPath = getFilePathFromTreePath(event.getPath());
            for (int i = 0; i < parent.getChildCount(); i++) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getChildAt(i);
                Node userNode = (Node) node.getUserObject();
                if (userNode.isFolder()) {
                    loadDirectoryContents(node, currentPath + userNode.getNodeName());
                }
            }
        }

    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }

    public void initTree() {
        File[] roots = File.listRoots();
        Node NodeRoot = new NodeBuilder("File System").folder(true).build();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(NodeRoot);
        defaultTreeModel = new DefaultTreeModel(root);
        for (File file : roots) {
            Node node = new NodeBuilder(file.getAbsolutePath()).folder(true).build();
            DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(node);
            root.add(defaultMutableTreeNode);
        }

        jtree.setModel(defaultTreeModel);
        jtree.setCellRenderer(new CustomTreeCellRenderer());
        jtree.addTreeWillExpandListener(this);
        collapseAllNodes(jtree, root);

    }

    private void collapseAllNodes(JTree tree, DefaultMutableTreeNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            collapseAllNodes(tree, childNode);
        }
        tree.collapsePath(new TreePath(node.getPath()));
    }

    private void loadDirectoryContents(DefaultMutableTreeNode parentNode, String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            Node node = createNode(file.getName());
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(node);
            defaultTreeModel.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
        }
    }

    public String getFilePathFromTreePath(TreePath treePath) {
        StringBuilder filePath = new StringBuilder();

        Object[] nodes = treePath.getPath();
        for (int i = 0; i < nodes.length; i++) {
            Object node = nodes[i];
            if (node instanceof DefaultMutableTreeNode) {
                Object userObject = ((DefaultMutableTreeNode) node).getUserObject();
                if (userObject instanceof Node) {
                    String nodeName = ((Node) userObject).getNodeName();
                    if (!nodeName.equalsIgnoreCase("File System")) {
                        filePath.append(nodeName + "\\");
                    }

                }
            }
        }

        return filePath.toString();
    }

    @Override
    public void register(Observer newObserver) {
        observers.add(newObserver);
    }

    @Override
    public void notifyAllObservers(TreePath treePath) {
        for (Observer observer : observers) {
            observer.onNewUpdate(treePath);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = getFilePathFromTreePath(jtree.getSelectionPath());
        path = path.substring(0, path.length() - 1);
        File file = new File(path);
        if (e.getActionCommand().equals("delete")) {
            int response = JOptionPane.showConfirmDialog(this.getParent(), "Are you Sure Wanna Delete?", "?", JOptionPane.WARNING_MESSAGE);
            System.out.println(response);
            if (response == 0) {

                if (file.exists()) {
                    file.delete();
                    JOptionPane.showMessageDialog(this.getParent(), "File was deleted!", "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    Object[] objectArr = jtree.getSelectionPath().getPath();

                    Object node = objectArr[objectArr.length - 1];
                    if (node instanceof DefaultMutableTreeNode) {
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
                        defaultTreeModel.removeNodeFromParent(treeNode);
                    }
                } else {
                    JOptionPane.showMessageDialog(this.getParent(), "File not was deleted!", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        if (e.getActionCommand().equals("open")) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(file);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this.getParent(), "Your device does not support this feature", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getActionCommand().equals("rename")) {
            String name = JOptionPane.showInputDialog(this.getParent(), "Enter the new Name", "New Name", JOptionPane.INFORMATION_MESSAGE);
            String[] pathArr = path.split("\\\\");
            String newPath = "";
            for (int i = 0; i < pathArr.length - 1; i++) {
                newPath = newPath + pathArr[i] + "\\";
            }
            file.renameTo(new File(newPath + "\\" + name));
            Object[] objectArr = jtree.getSelectionPath().getPath();
            Object test = objectArr[objectArr.length - 1];
            if (test instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) test;
                treeNode.setUserObject(createNode(name));
                defaultTreeModel.nodeChanged(treeNode);
                JOptionPane.showMessageDialog(this.getParent(), "File name was changed", "Renamed", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (e.getActionCommand().equals("new")) {
            String name = JOptionPane.showInputDialog(this.getParent(), "Enter the new Name", "New Name", JOptionPane.INFORMATION_MESSAGE);
            String[] pathArr = path.split("\\\\");
            String newPath = "";
            for (int i = 0; i < pathArr.length; i++) {
                newPath = newPath + pathArr[i] + "\\";
            }
            File newFile = new File(newPath + name);
            try {
                if (newFile.createNewFile()) {
                    JOptionPane.showMessageDialog(this.getParent(), "File name was Created", "Created", JOptionPane.INFORMATION_MESSAGE);
                    TreePath treepath = jtree.getSelectionPath();
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treepath.getLastPathComponent();
                    DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(createNode(name));
                    defaultTreeModel.insertNodeInto(newNode, parent, parent.getChildCount());
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    public Node createNode(String name) {
        NodeBuilder builder = new NodeBuilder(name);
        if (name.endsWith(".pdf")) {
            builder.pdf(true);
        }
        if (name.endsWith(".docx")) {
            builder.docx(true);
        }
        if (name.toLowerCase().endsWith(".ini") || name.toLowerCase().endsWith(".bat") || name.toLowerCase().endsWith(".sys") || name.toLowerCase().endsWith(".bin") || name.toLowerCase().endsWith(".bin") || name.toLowerCase().endsWith(".msi") || name.toLowerCase().endsWith(".tmp")) {
            builder.setting(true);
        }
        if (name.endsWith(".txt")) {
            builder.txt(true);
        }
        if (!(name.contains("."))) {
            builder.folder(true);
        }
        return builder.build();
    }
}
