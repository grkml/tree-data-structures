package bst_hard_deletion;

public class BinaryTreeNode<E extends Comparable< ? super E > >
{
   // use public access so the tree or other classes can access members 
   public BinaryTreeNode<E> lftChild, rtChild;
   public E data;
   public BinaryTreeNode<E> myRoot;  // needed to test for certain error

   
   public BinaryTreeNode( E d, BinaryTreeNode<E> lft, BinaryTreeNode<E> rt )
   {
      lftChild = lft; 
      rtChild = rt;
      data = d;
   }
   
   public BinaryTreeNode()
   {
      this(null, null, null);
   }
}
