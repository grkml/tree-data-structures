# Binary Search Trees
## Understanding General Tree Data Structures First!

Lets take a look at one of my favorite implementations of the tree data structure and identify some terminology along the way:

<img width="65%" src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/starkFamilyTree.jpg">

**Figure 1:** A tree data structure modeling one of Great Houses of Westeros

### Nodes
Trees are organized into nodes that hold data along various branches. Nodes are related through ```parent```, ```child```, and ```sibling``` relationships.
### Roots
Every tree has a ```root``` node at the highest level (ie: Rickard Stark). Every node is also the ```root``` of a ```subtree```. We can see that Ned Stark is the ```root``` node of the ```subtree``` containing him, Robb, Sansa, Arya, Bran, and Rickon.
### Depth
Depth refers to the level at which a node sits relative to the ```root```.  When referring to the entire tree, a ```root``` node, like Rickard Stark has a depth of 0, while Jon Snow and Arya Stark have a depth of 2. However, depth is also relative to a ```subtree```. If we were only considering Ned Stark's subtree, Arya would have a depth of 1.
### Height
The height of any node is measured through the deepest ```child``` node it has. So the height of Benjen Stark is 0, while the height of Rickard Stark is 2.

## Designing a General Tree Data Structure
### Strategy
To represent a tree, it would be useful to abstract away the "spider web" feel of the actual tree into something more manageable. Imagine that each node has 2 reference pointers ```firstChild``` and ```nextSibling```. Then, we can represent the tree in a chained hierarchy structure focussed on node depth. It would look something like this:

<img width="65%" src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/starkFamilyTreeImplementation.jpg">

**Figure 2:** Efficiently representing House Stark with our ```TreeNode``` class
 
### Implementing a ```TreeNode```
In practice, we would actually need more than just those 2 pointers to work with the tree efficiently. Here is an outline of our ```TreeNode``` class:
#### Instance Variables
1. ```data <String>``` - Stark family member's name (ie "Ned Stark")
2. ```firstChild <TreeNode>``` - A pointer to a child of ```this```, if any
3. ```nextSibling <TreeNode>``` - A pointer to a sibling of ```this```, if any
4. ```previousNode <TreeNode> ``` - A pointer to previous Node along the chain (see Figure 2)
5. ```myRoot <TreeNode>``` - A pointer to the root node associated with ```this```
#### ```TreeNode``` class structure
```java
public class TreeNode {
    protected TreeNode firstChild, nextSibling, previousNode;
    protected String data;
    protected TreeNode myRoot;
    
    public TreeNode() { 
      // Constructor Logic...
    }
    
    public E getData() { 
      return data; // Accessor
    }
}
```
#### Data that Could be Stored in a ```Tree```
| ```<TreeNode> this``` | ```this.firstChild``` | ```this.nextSibling``` | ```this.previousNode``` |
| :--: | :--: | :--: | :--: |
| ```rickardStark``` | ```nedStark``` | ```null``` | ```null``` |
| ```nedStark``` | ```robbStark``` | ```brandonStark``` | ```rickardStark``` |
| ```brandonStark``` | ```null``` | ```benjenStark``` | ```nedStark``` |
| ```benjenStark``` | ```null``` | ```lyannaStark``` | ```brandonStark``` |
| ```lyannaStark``` | ```jonSnow``` | ```null``` | ```benjenStark``` |
| ```robbStark``` | ```null``` | ```sansaStark``` | ```nedStark``` |
| ```sansaStark``` | ```null``` | ```aryaStark``` | ```robbStark``` |
| ```aryaStark``` | ```null``` | ```branStark``` | ```sansaStark``` |
| ```branStark``` | ```null``` | ```rickonStark``` | ```aryaStark``` |
| ```rickonStark``` | ```null``` | ```null``` | ```branStark``` |
| ```jonSnow``` | ```null``` | ```null``` | ```lyannaStark``` |

## Recursion, Recursion, Recursion
Let's take a mini tour of a barebones ```Tree``` class linking together ```TreeNodes```. We'll focus on the ```find()``` method for now and assume an object of this class called ```starkFamilyTree``` is already setup.

```java
public class Tree {
    private int mSize;
    TreeNode mRoot;

    public Tree() { 
        // Constrcutor Logic...
    }
    
    // Client side find() function
    public TreeNode find(String data) { 
        return find(mRoot, data, 0); // Runs private recursive version of find()
    }
    
    // Recursive logic - overloaded find()
    private TreeNode find(TreeNode root, String data, int level) {
        TreeNode<E> returnValue;
        
        if (mSize == 0 || root == null) // Basecase 1 (nothing to search)
            return null;
        
        if (root.data.equals(data)) // Basecase 2 (found!)
            return root;

        if (level > 0) {
            returnValue = find(root.nextSibling, data, level); // Recursive Call
            if (returnValue != null)
                return returnValue;
        }

        return find(root.firstChild, data, ++level); // Recursive Call
    }
}
```
