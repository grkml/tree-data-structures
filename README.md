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
Let's first try represent each node in a Java class called ```TreeNode```. To be able to sew together a ```Tree``` from ```TreeNodes```, lets focus on each node having the correct instance variables. Each ```TreeNode``` instance should have the following in it:

1. ```data <String>``` - Stark family member's name (ie "Ned Stark")
2. ```firstChild <TreeNode>``` - A pointer to a child of ```this```, if any
3. ```nextSibling <TreeNode>``` - A pointer to a sibling of ```this```, if any
4. ```previousNode <TreeNode> ``` - A pointer to previous Node along the chain (see Figure 2)
 
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

**Figure 1:** Efficiently representing House Stark with our ```TreeNode``` class
