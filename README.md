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

## Designing the Data Structure
### Strategy
In order to store this data structure in memory, we have to come up with an efficient design to do so. Lets first try represent each node in a Java class called ```TreeNode```. To be able to sew together a tree, we'll have each node host 3 sets of data:

1. ```name``` - The family member's name
2. ```firstChild``` - A pointer to the first child's TreeNode Object, if any
3. ```nextSibling``` - A pointer to a sibling's TreeNode Object, if any
 ### Implementation
```java
class TreeNode {
    String name; // ie "Ned Stark"
    TreeNode firstChild // pointer to a child
    TreeNode nextSibling; // pointer to a sibling
    
    public TreeNode() { // some constructor Logic }
}
```
### Data Stored
| ```<TreeNode> this``` | ```this.firstChild``` | ```this.nextSibling``` |
| :--: | :--: | :--: |
| ```rickardStark``` | ```nedStark``` | ```null``` |
| ```nedStark``` | ```robbStark``` | ```brandonStark``` |
| ```brandonStark``` | ```null``` | ```benjenStark``` |
| ```benjenStark``` | ```null``` | ```lyannaStark``` |
| ```lyannaStark``` | ```jonSnow``` | ```null``` |
| ```robbStark``` | ```null``` | ```sansaStark``` |
| ```sansaStark``` | ```null``` | ```aryaStark``` |
| ```aryaStark``` | ```null``` | ```branStark``` |
| ```branStark``` | ```null``` | ```rickonStark``` |
| ```rickonStark``` | ```null``` | ```null``` |
### Logical Representation
<img width="65%" src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/starkFamilyTreeImplementation.jpg">
