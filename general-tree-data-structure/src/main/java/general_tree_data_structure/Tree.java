package general_tree_data_structure;

public class Tree<E> {
    private int mSize;
    TreeNode<E> mRoot;
    
    public Tree() { clear(); }
    public boolean empty() { return (mSize == 0); }
    public int size() { return mSize; }
    public void clear() { mSize = 0; mRoot = null; }
    
    // Client passes in some data to find. call overloaded recursive version find()
    public TreeNode<E> find(E data) { return find(mRoot, data, 0); }
    
    // Overloaded find() for recursion
    public TreeNode<E> find(TreeNode<E> root, E data, int level) {
        TreeNode<E> stackResponse;

        // Basecase 1 - main tree or recursive subtree is empty
        // maybe we have looked all the way down to some leaf and still have not found our x
        if (mSize == 0 || root == null)
            return null;

        // Basecase 2 - Root of main tree or recursive subtree is node we're looking for
        if (root.data.equals(data))
            return root;

        // If past root of whatever subtree were on, recurse to sibling on the right, recursion will go go down its subtree
        if ( level > 0 && (stackResponse = find(root.nextSibling, data, level)) != null )
            return stackResponse;

        return find(root.firstChild, data, ++level);
    }
    
    public TreeNode<E> addChild( TreeNode<E> treeNode,  E data ) {
        
        // Empty Tree Case - Creates a root node
        if (mSize == 0) {
            if (treeNode != null) // silent error: treeNode can't be right
                return null; 
            mRoot = new TreeNode<E>(data, null, null, null);
            mRoot.myRoot = mRoot;
            mSize = 1;
            return mRoot;
        }
        
        if (treeNode == null)
            return null; // error inserting into non_null tree with a null parent
            
        if (treeNode.myRoot != mRoot)
            return null;  // silent error, node does not belong to this tree

        // push this node into the head of the sibling list; adjust prev pointers
        TreeNode<E> newNode = new TreeNode<E>(data, treeNode.firstChild, null, treeNode, mRoot);
        treeNode.firstChild = newNode;
        if (newNode.nextSibling != null)
            newNode.nextSibling.previousNode = newNode;
        ++mSize;
        return newNode;  
    }
}