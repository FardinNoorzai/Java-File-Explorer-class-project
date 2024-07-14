
package gui;

import javax.swing.tree.TreePath;


public interface Observer {
    public void onNewUpdate(TreePath treePath);
    
}
