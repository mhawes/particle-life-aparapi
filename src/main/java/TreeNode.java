import java.util.List;

public class TreeNode<T> {
  private TreeNode parent;
  public TreeNode getParent() {
    return parent;
  }
  public void setParent(TreeNode parent) {
    this.parent = parent;
  }
  private List<TreeNode> children;
  private List<T> values;
  private int depth;
}
