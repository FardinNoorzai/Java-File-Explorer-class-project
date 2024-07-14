package gui;

public class Node {
    private String nodeName;
    private boolean isFolder;
    private boolean isPdf;
    boolean isDocx;
    boolean isSetting;
    boolean isTxt;

    public Node(String nodeName) {
        this.nodeName = nodeName;
    }
    

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }

    public boolean isPdf() {
        return isPdf;
    }

    public void setPdf(boolean isPdf) {
        this.isPdf = isPdf;
    }

    public boolean isDocx() {
        return isDocx;
    }

    public void setDocx(boolean isDocx) {
        this.isDocx = isDocx;
    }

    public boolean isSetting() {
        return isSetting;
    }

    public void setSetting(boolean isSetting) {
        this.isSetting = isSetting;
    }

    public boolean isTxt() {
        return isTxt;
    }

    public void setTxt(boolean isTxt) {
        this.isTxt = isTxt;
    }

    @Override
    public String toString() {
        return nodeName; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
}
