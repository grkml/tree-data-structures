# Binary Search Trees
# Understanding General Tree Data Structures First!

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

# Recursion
<img width="50%" src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/recursiveMeme.jpg">

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
        TreeNode stackResponse;
        
        if (mSize == 0 || root == null) // Basecase 1 (nothing to search)
            return null;
        
        if (root.data.equals(data)) // Basecase 2 (found!)
            return root;

        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive Call
            if (returnValue != null)
                return stackResponse;
        }

        return find(root.firstChild, data, ++level); // Recursive Call
    }
}
```
# Recursive Steps & Stack Frames
Lets assume we want to find "Arya Stark" in the tree. To do this we traverse the tree through recursion in a number of steps. Recursive steps can be thought of as repetative runs of the same function, with each repetition having a different state. The state we refer to is called a stack frame that encapsulates local variables. Recrusion generates a stack of stack frames by calling the function over and over again. Stack frames keep piling up until we hit a basecase. Once the basecase is hit, the stack starts to undo itself, returning values to the frames that have been waiting in the stack.

This is a recursive process that has many steps. You can follow along and note what the stack is returning at each step marked with "**"

## Frame 0a (Global) calls 1a
This frame runs the following global code statement:
```java
TreeNode result = starkFamilyTree.find("Arya Stark"); // Call into Frame 1a
```

<table>
  <tr>
    <th colspan="2">Frame 0a (Global)</th>
  </tr>
  <tr>
    <th>global variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>result</td>
    <td>starkFamilyTree.find("Arya Stark")</td>
    <td>awaiting return from 1a</td> 
  </tr>
</table>

## Frame 1a calls 2a
This frame runs the following code statement from 

`public TreeNode find(String data)`
```java
        return find(mRoot, data, 0); // <-------- Call into Frame 2a
```
<table>
  <tr>
    <th colspan="2">Frame 1a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>mRoot</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/rickardStark.jpg"></td>
  </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>find(rickardStark, "Arya Stark", 0)</td>
     <td>awaiting return from 2a</td> 
    </tr>
</table>

## Frame 2a calls 3a
Because local variable `level == 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // <-------- Recursive call into Frame 3a
```
<table>
  <tr>
    <th colspan="2">Frame 2a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/rickardStark.jpg"></td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>0</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>find(nedStark, "Arya Stark", 1)</td>
     <td>awaiting return from 3a</td> 
    </tr>
</table>

## Frame 3a calls 4a
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // <-------- Recursive call into Frame 4a
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th colspan="2">Frame 3a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/nedStark.jpg"></td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>1</td>
  </tr>
   <tr>
     <td>stackResponse</td>
     <td>find(brandonStark, "Arya Stark", 1)</td>
     <td>awaiting return from 4a</td> 
    </tr>
</table>

## Frame 4a calls 5a
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // <-------- Recursive call into Frame 5a
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th colspan="2">Frame 4a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/brandonStark.jpg"></td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>1</td>
  </tr>
   <tr>
     <td>stackResponse</td>
     <td>find(benjenStark, "Arya Stark", 1)</td>
     <td>awaiting return from 5a</td> 
    </tr>
</table>

## Frame 5a calls 6a
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // <-------- Recursive call into Frame 6a
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th colspan="2">Frame 5a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/benjenStark.jpg"></td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>1</td>
  </tr>
   <tr>
     <td>stackResponse</td>
     <td>find(lyannaStark, "Arya Stark", 1)</td>
     <td>awaiting return from 6a</td> 
    </tr>
</table>

## Frame 6a calls 7a
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // <-------- Recursive call into Frame 7a
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th colspan="2">Frame 6a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/lyannaStark.jpg"></td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>1</td>
  </tr>
   <tr>
     <td>stackResponse</td>
     <td>find(null, "Arya Stark", 1)</td>
     <td>awaiting return from 7a</td> 
    </tr>
</table>

## Frame 7a returns value to 6a
Because local variable `root == null`, this frame hits the following basecase from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // <-------- Recursive return value into Frame 6a
```
<table>
  <tr>
    <th colspan="2">Frame 7a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td>null</td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>1</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>null</td>
     <td>returning value to 6a</td> 
    </tr>
</table>


## Frame 6a - receives return value from 7a & calls 7b
Frame 6a now has received `stackResponse == null` from 7a, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // <-------- Recursive call into Frame 7b
```
<table>
  <tr>
    <th colspan="2">Frame 6a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/lyannaStark.jpg"></td>
  </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>1</td>
  </tr>
   <tr>
    <td>stackResponse</td>
    <td>null</td>
    <td>recieved from 7a</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>find(jonSnow, "Arya Stark", 2)</td>
     <td>awaiting return from 7b</td> 
    </tr>
</table>

## Frame 7b calls 8a
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // <-------- Recursive call into Frame 8a
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th colspan="2">Frame 7b</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/jonSnow.jpg"></td>
  </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>2</td>
  </tr>
   <tr>
     <td>stackResonse</td>
     <td>find(null, "Arya Stark", 2)</td>
     <td>awaiting return from 7b</td> 
    </tr>
</table>

## Frame 8a returns value to 7b
Because local variable `root == null`, this frame hits the following basecase from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // <-------- Recursive return value into Frame 7b
```
<table>
  <tr>
    <th colspan="2">Frame 8a</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td>null</td>
   </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>2</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>null</td>
     <td>returning value to 7b</td> 
    </tr>
</table>

## Frame 7b - receives return value from 8a & calls 8b
Frame 7b now has received `stackResponse == null` from 7a, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // <-------- Recursive call into Frame 7b
```
<table>
  <tr>
    <th colspan="2">Frame 7b</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/jonSnow.jpg"></td>
  </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
  <tr>
    <td>level</td>
    <td>2</td>
  </tr>
   <tr>
    <td>stackResponse</td>
    <td>null</td>
    <td>recieved from 8a</td> 
  </tr>
   <tr>
     <td>return value</td>
     <td>find(null, "Arya Stark", 3)</td>
     <td>awaiting return from 8a</td> 
    </tr>
</table>
