package general_tree_data_structure;

public class Tree<E>
{
   private int mSize;
   TreeNode<E> mRoot;
   
   public Tree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size() { return mSize; }
   public void clear() { mSize = 0; mRoot = null; }
   
   // Client passes in some data to find. call overloaded recursive version find()
   public TreeNode<E> find(E data) { 
      return find(mRoot, data, 0); 
   }
   
   // Overloaded find() for recursion
   public TreeNode<E> find(TreeNode<E> root, E data, int level) {
      TreeNode<E> returnValue;
      
      // Basecase 1 - main tree or recursive subtree is empty
      // maybe we have looked all the way down to some leaf and still have not found our x
      if (mSize == 0 || root == null)
         return null;
         
      // Basecase 2 - Root of main tree or recursive subtree is node we're looking for
      if (root.data.equals(data))
         return root;
         
      // If past root of whatever subtree were on, recurse to sibling on the right, recursion will go go down its subtree
      if ( level > 0 && (returnValue = find(root.nextSibling, data, level)) != null )
         return returnValue;
         
      return find(root.firstChild, data, ++level);
   }
   
   // public TreeNode<E> addChild( TreeNode<E> treeNode,  E x )
   // {
   //    // ...
   // }
   
}