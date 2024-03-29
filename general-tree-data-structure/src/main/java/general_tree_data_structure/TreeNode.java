package general_tree_data_structure;

public class TreeNode<E> {
    // use protected access so the tree, in the same package,
    // or derived classes can access members  
    protected TreeNode<E> firstChild, nextSibling, previousNode;
    protected E data;
    protected TreeNode<E> myRoot;  // needed to test for certain error
    
    public TreeNode(E data, TreeNode<E> nextSibling, TreeNode<E> firstChild, TreeNode<E> previousNode) {
        this.firstChild = firstChild;
        this.nextSibling = nextSibling;
        this.previousNode = previousNode;
        this.data = data;
        myRoot = null;
    }
   
    public TreeNode() {
        this(null, null, null, null);
    }
   
    public E getData() { return data; }

    // for use only by Tree (default access)
    protected TreeNode( E data, TreeNode<E> nextSibling, TreeNode<E> firstChild, TreeNode<E> previousNode, TreeNode<E> root ) {
        this(data, nextSibling, firstChild, previousNode);
        myRoot = root;
    }
}