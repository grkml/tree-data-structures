package bst_hard_deletion;

public class Tree<E>
{
   private int mSize;
   TreeNode<E> mRoot;
   
   public Tree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size() { return mSize; }
   public void clear() { mSize = 0; mRoot = null; }
   
   // public TreeNode<E> addChild( TreeNode<E> treeNode,  E x )
   // {
   //    // ...
   // }
   
   // public TreeNode<E> find(E x) { return find(mRoot, x, 0); }
   
   // public TreeNode<E> find(TreeNode<E> root, E x, int level)
   // {
   //    // ...
   // }
}