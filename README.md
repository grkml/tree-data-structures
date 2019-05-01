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
 
### Implementation
In practice, we would actually need more than just those 2 pointers to work with the tree efficiently. Here is a sample ```TreeNode``` class that get the job done:
#### Instance Variables
1. ```data <String>``` - Stark family member's name (ie "Ned Stark")
2. ```firstChild <TreeNode>``` - A pointer to a child of ```this```, if any
3. ```nextSibling <TreeNode>``` - A pointer to a sibling of ```this```, if any
4. ```previousNode <TreeNode> ``` - A pointer to previous Node along the chain (see Figure 2)
5. ```myRoot <TreeNode>``` - A pointer to the root node associated with ```this```
#### Code
```java
public class TreeNode {
    protected TreeNode firstChild, nextSibling, previousNode;
    protected String data;
    protected TreeNode myRoot;
    
    public TreeNode(String data, TreeNode nextSibling, TreeNode firstChild, TreeNode previousNode) {
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

    // Used by ```Tree``` later on to add create linked Nodes
    protected TreeNode( E data, TreeNode<E> nextSibling, TreeNode<E> firstChild, TreeNode<E> previousNode, TreeNode<E> root ) {
        this(data, nextSibling, firstChild, previousNode);
        myRoot = root;
    }
}
```
#### Data Stored
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
