# Binary Search Trees
This repository implements the binary search tree data structure in Java. It also aims to provide a brief background on the tree data structure and its various uses for educational purposes.
## Tree Data Structure Terminology

Lets take a look at one of my favorite implementations of the tree data structure and identify some terminology along the way:

<img width="65%" src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/starkFamilyTree.jpg">

### Nodes
Trees are organized into nodes that hold data along various branches. Nodes are related through ```parent```, ```child```, and ```sibling``` relationships.
### Roots
Every tree has a ```root``` node at the highest level (ie: Rickard Stark). Every node is also the ```root``` of a ```subtree```. We can see that Ned Stark is the ```root``` node of the ```subtree``` containing him, Robb, Sansa, Arya, Bran, and Rickon.
### Depth
Depth refers to the level at which a node sits relative to the ```root```.  When referring to the entire tree, a ```root``` node, like Rickard Stark has a depth of 0, while Jon Snow and Arya Stark have a depth of 2. However, depth is also relative to a ```subtree```. If we were only considering Ned Stark's subtree, Arya would have a depth of 1.
### Height
The height of any node is measured through the deepest ```child``` node it has. So the height of Benjen Stark is 0, while the height of Rickard Stark is 2.

## Designing a Data Structure
### Strategy
In order to store this data structure in memory, we have to come up with an efficient design to do so. Lets first try represent each node in a Java class called ```TreeNode```. To be able to sew together a tree, we'll have each node host 4 instance variables:

1. <String>```name``` - The family member's name
2. <TreeNode>```firstChild``` - A pointer to the first child's TreeNode Object, if any
3. <TreeNode>```nextSibling``` - A pointer to a sibling's TreeNode Object, if any
4. <TreeNode>```previousNode``` - A pointer to the TreeNode Object that chained right before this one
 
 ### Implementation
```java
public class TreeNode {
   protected TreeNode firstChild, nextSibling, previousNode;
   protected String Name;

   public TreeNode( String data, TreeNode nextSibling, TreeNode firstChild, TreeNode previousNode ) {
      this.firstChild = firstChild; 
      this.nextSibling = nextSibling;
      this.previousNode = previousNode;
      this.name = name;
   }
   
   public TreeNode() { this(null, null, null, null); }
}
```
### Data Stored
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
### Logical Representation
<img width="65%" src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/starkFamilyTreeImplementation.jpg">
