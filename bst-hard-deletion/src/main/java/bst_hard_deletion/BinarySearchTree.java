package bst_hard_deletion;

import java.util.*;

public class BinarySearchTree<E extends Comparable< ? super E > >
   implements Cloneable
{
   protected int mSize;
   protected BinaryTreeNode<E> mRoot;
   
   public BinarySearchTree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size() { return mSize; }
   public void clear() { mSize = 0; mRoot = null; }
   
   public E findMin()
   {
      if (mRoot == null)
         throw new NoSuchElementException();
      return findMin(mRoot).data;
   }
   protected BinaryTreeNode<E> findMin( BinaryTreeNode<E> root ) 
   {
      if (root == null)
         return null;
      if (root.lftChild == null)
         return root;
      return findMin(root.lftChild);
   }
   
   public E findMax()
   {
      if (mRoot == null)
         throw new NoSuchElementException();
      return findMax(mRoot).data;
   }
   protected BinaryTreeNode<E> findMax( BinaryTreeNode<E> root ) 
   {
      if (root == null)
         return null;
      if (root.rtChild == null)
         return root;
      return findMin(root.lftChild);
   }
   
   public boolean insert( E x )
   {
      int oldSize = mSize;
      mRoot = insert(mRoot, x); // Start recursion @ mRoot and follow through
      return (mSize != oldSize); // Tests Whether Insersion Successful
   }
   protected BinaryTreeNode<E> insert( BinaryTreeNode<E> root, E x )
   {
      int compareResult;
      
      if (root == null) // This condition finds a spot to hang the node
      {
         mSize++;
         return new BinaryTreeNode<E>(x, null, null); // null lftChild & rtChild
      }
      compareResult = x.compareTo(root.data); 
      if ( compareResult < 0 ) // less than
         root.lftChild = insert(root.lftChild, x);
      else if ( compareResult > 0 ) // greater than
         root.rtChild = insert(root.rtChild, x);

      return root;
   }
}
