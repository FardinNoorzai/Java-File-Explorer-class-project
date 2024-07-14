package gui;

import javax.swing.tree.TreePath;


public interface Observable {
    public void register(Observer newObserver);
    public void notifyAllObservers(TreePath treePath);
    
}
