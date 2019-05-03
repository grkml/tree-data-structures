package bst_hard_deletion;

package cs_1c;

public class TreeNode<E extends Comparable< ? super E > >
{
   // use protected access so the tree, in the same package, 
   // or derived classes can access members 
   protected TreeNode<E> lftChild, rtChild;
   protected E data;
   protected TreeNode<E> myRoot; // needed to test for certain error

   public TreeNode( E d, TreeNode<E> lft, TreeNode<E> rt )
   {
      lftChild = lft; 
      rtChild = rt;
      data = d;
   }
   
   // a few less important methods omitted for now
}