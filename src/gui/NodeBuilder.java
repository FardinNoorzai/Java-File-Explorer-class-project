package gui;


public class NodeBuilder {
    private String nodeName;
    private boolean isFolder = false;
    private boolean isPdf = false;
    private boolean isDocx = false;
    private boolean isSetting = false;
    private boolean isTxt = false;

    public NodeBuilder(String nodeName) {
        this.nodeName = nodeName;
    }

    public NodeBuilder folder(boolean isFolder) {
        this.isFolder = isFolder;
        return this;
    }

    public NodeBuilder pdf(boolean isPdf) {
        this.isPdf = isPdf;
        return this;
    }

    public NodeBuilder docx(boolean isDocx) {
        this.isDocx = isDocx;
        return this;
    }

    public NodeBuilder setting(boolean isSetting) {
        this.isSetting = isSetting;
        return this;
    }

    public NodeBuilder txt(boolean isTxt) {
        this.isTxt = isTxt;
        return this;
    }

    public Node build() {
        Node node = new Node(nodeName);
        node.setFolder(isFolder);
        node.setPdf(isPdf);
        node.setDocx(isDocx);
        node.setSetting(isSetting);
        node.setTxt(isTxt);
        return node;
    }
}
