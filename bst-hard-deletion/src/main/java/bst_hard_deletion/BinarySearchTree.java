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
   
   // public E findMax() { ... }
}
