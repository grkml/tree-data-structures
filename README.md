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
        
        if (mSize == 0 || root == null) // Base case 1 (nothing to search)
            return null;
        
        if (root.data.equals(data)) // Base case 2 (found!)
            return root;

        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive Call
            if (stackResponse != null)
                return stackResponse; // Base case 3 (forward result it along the recusrive chain!)
        }

        return find(root.firstChild, data, ++level); // Recursive Call
    }
}
```
# Recursive Steps & Stack Frames
Lets assume we want to find "Arya Stark" in the tree. To do this we traverse the tree through recursion in a number of steps. Recursive steps can be thought of as repetative runs of the same function, with each repetition having a different state. The state we refer to is called a stack frame that encapsulates local variables. Recrusion generates a stack of stack frames by calling the function over and over again. Stack frames keep piling up until we hit a basecase. Once the basecase is hit, the stack starts to undo itself, returning values to the frames that have been waiting in the stack.

This is a recursive process that has many steps. You can follow along and note what the stack is returning at each step marked with "**"

## Global - calls Function
This frame runs the following global code statement:
```java
TreeNode result = starkFamilyTree.find("Arya Stark"); // Call into Function
```

<table>
  <tr>
    <th>Stack Frame</th>
    <th>Global</th>
  </tr>
  <tr>
    <th>global variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>result</td>
    <td>starkFamilyTree.find("Arya Stark")</td>
    <td>awaiting return from Function</td> 
  </tr>
</table>

## Function - calls Node 0
This frame runs the following code statement from 

`public TreeNode find(String data)`
```java
        return find(mRoot, data, 0); // Recursive Call to Node 0 (rickardStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Function</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>mRoot</td>
    <td>rickardStark</td>
  </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>find(rickardStark, "Arya Stark", 0)</td>
     <td>awaiting return from Node 0 & will eventually return to Global</td> 
    </tr>
</table>

## Node 0 - calls Node 1
Because local variable `level == 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive call to Node 1 (nedStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 0 (rickardStark)</th>
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
     <td>awaiting return from Node 1 (nedStark) & will eventually return to Function</td> 
    </tr>
</table>

## Node 1 - calls Node 2
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to Node 2 (brandonStark)
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 1 (nedStark)</th>
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
     <td>awaiting return from Node 2 (brandonStark)</td> 
  </tr>
  <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 0 (rickardStark)</td> 
   </tr>
</table>

## Node 2 - calls Node 3
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to Node 3 (benjenStark)
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 2 (brandonStark)</th>
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
     <td>awaiting return from Node 3 (benjenStark)</td> 
   </tr>
  <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 1 (nedStark)</td> 
   </tr>
</table>

## Node 3 - calls Node 4
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to Node 4 (lyannaStark)
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 3 (benjenStark)</th>
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
     <td>awaiting return from Node 4 (lyannaStark)</td> 
   </tr>
  <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 2 (brandonStark)</td> 
   </tr>
</table>

## Node 4 - calls lyannaStark.nextSibling
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to lyannaStark.nextSibling
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 4 (lyannaStark)</th>
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
     <td>awaiting return from lyannaStark.nextSibling</td> 
    </tr>
   <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 3 (benjenStark)</td> 
   </tr>
</table>

## lyannaStark.nextSibling - returns `null` to Node 4
Because local variable `root == null`, this frame hits the following base case from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // Recursive return to Node 4 (lyannaStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>lyannaStark.nextSibling</th>
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
     <td>returning to Node 4 (lyannaStark)</td> 
   </tr>
</table>


## Node 4 - calls Node 5
Node 4 now has received `stackResponse == null` from lyannaStark.nextSibling, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive call to Node 5 (jonSnow)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 4 (lyannaStark)</th>
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
     <td>received from lyannaStark.nextSibling</td> 
    </tr>
   <tr>
     <td>return value</td>
     <td>find(jonSnow, "Arya Stark", 2)</td>
     <td>awaiting response from Node 5 (jonSnow) & will eventually return to Node 3 (benjenStark)</td> 
   </tr>
</table>

## Node 5 - calls jonSnow.nextSibling
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to jonSnow.nextSibling
            if (stackResponse != null)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 5 (jonSnow)</th>
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
     <td>awaiting return from jonSnow.nextSibling</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 4 (lyannaStark)</td> 
   </tr>
</table>

## jonSnow.nextSibling - returns `null` to Node 5
Because local variable `root == null`, this frame hits the following base case from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // Recursive return to Node 5 (jonSnow)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>jonSnow.nextSibling</th>
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
     <td>returning to Node 5 (jonSnow)</td> 
    </tr>
</table>

## Node 5 - calls jonSnow.firstChild
Frame 8 now has received `stackResponse == null` from jonSnow.nextSibling, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive call to jonSnow.firstChild
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 5 (jonSnow)</th>
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
     <td>null</td>
     <td>received from jonSnow.nextSibling</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>find(null, "Arya Stark", 3)</td>
     <td>awating response from jonSnow.firstChild & will eventually return to Node 4 (lyannaStark)</td> 
   </tr>
</table>

## jonSnow.firstChild - returns `null` to Node 5
Because local variable `root == null`, this frame hits the following base case from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // Recursive return to Node 5 (jonSnow)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>jonSnow.firstChild</th>
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
    <td>3</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>null</td>
     <td>returning to Node 5 (jonSnow)</td> 
    </tr>
</table>

## Node 5 - returns `null` to Node 4
Node 5 now has received `null` from jonSnow.firstChild, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive return to Node 4 (lyannaStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 5 (jonSnow)</th>
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
     <td>null</td>
     <td>received from jonSnow.nextSibling</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>null</td>
     <td>received from jonSnow.firstChild & returning to Node 4 (lyannaStark)</td> 
   </tr>
</table>

## Node 4 - returns `null` to Node 3
Node 4 now has received `null` from 8, so it finishes the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive return to Node 3 (benjenStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 4 (lyannaStark)</th>
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
     <td>received from lyannaStark.nextSibling</td> 
    </tr>
   <tr>
     <td>return value</td>
     <td>null</td>
     <td>received from Node 5 (jonSnow) & returning to Node 3 (benjenStark)</td> 
   </tr>
</table>


## Node 3 - calls benjenStark.firstChild
Node 3 now has received `stackResponse == null` from Node 4, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive call to benjenStark.firstChild
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 3 (benjenStark)</th>
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
     <td>null</td>
     <td>received from Node 4 (lyannaStark)</td> 
   </tr>
  <tr>
     <td>return value</td>
     <td>find(null, "Arya Stark", 2)</td>
     <td>awating response from benjenStark.firstChild & will eventually return to Node 2 (brandonStark)</td> 
   </tr>
</table>

## benjenStark.firstChild returns `null` to Node 3
Because local variable `root == null`, this frame hits the following base case from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // Recursive return to Node 3 (benjenStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>benjenStark.firstChild</th>
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
     <td>returning value to Node 3 (benjenStark)</td> 
    </tr>
</table>

## Node 3 - returns value to Node 2
Node 3 now has received `null` from benjenStark.firstChild, so it finishes the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive return to Node 2 (brandonStark)
```

<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 3 (benjenStark)</th>
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
     <td>null</td>
     <td>received from Node 4 (lyannaStark)</td> 
   </tr>
  <tr>
     <td>return value</td>
     <td>null</td>
     <td>received from benjenStark.firstChild & returning to Node 2 (brandonStark)</td> 
   </tr>
</table>

## Node 2 - calls brandonStark.firstChild
Frame 4 now has received `stackResponse == null` from 5, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive call to brandonStark.firstChild
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 2 (brandonStark)</th>
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
     <td>null</td>
     <td>received from Node 3 (benjenStark)</td> 
   </tr>
  <tr>
     <td>return value</td>
     <td>find(null, "Arya Stark, 2)</td>
     <td>awating response from brandonStark.firstChild & will eventually return to Node 1 (nedStark)</td> 
   </tr>
</table>

## brandonStark.firstChild - returns `null` to Node 2
Because local variable `root == null`, this frame hits the following base case from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (mSize == 0 || root == null)
            return null; // Recursive return to Node 2 (brandonStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>brandonStark.firstChild</th>
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
     <td>returning value to Node 2 (brandonStark)</td> 
    </tr>
</table>

## Node 2 - returns `null` to Node 1
Node 2 now has received `null` from brandonStark.firstChild, so it finishes the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive return to Node 1 (nedStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 2 (brandonStark)</th>
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
     <td>null</td>
     <td>received from Node 3 (benjenStark)</td> 
   </tr>
  <tr>
     <td>return value</td>
     <td>null</td>
     <td>received from brandonStark.firstChild & returning to Node 1 (nedStark)</td> 
   </tr>
</table>

## Node 1 - calls Node 6
Node 1 now has received `stackResponse == null` from Node 2, so it runs the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive call to Node 6 (robbStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 1 (nedStark)</th>
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
     <td>null</td>
     <td>received from Node 2 (brandonStark)</td> 
  </tr>
  <tr>
     <td>return value</td>
     <td>find(robbStark, "Arya Stark", 2)</td>
     <td>awating response from Node 6 (robbStark) & will eventually return to Node 0 (rickardStark)</td> 
   </tr>
</table>

## Node 6 - calls Node 7
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to Node 7 (sansaStark)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 6 (robbStark)</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/robbStark.jpg"></td>
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
     <td>find(sansaStark, "Arya Stark", 2)</td>
     <td>awaiting return from Node 7 (sansaStark)</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 1 (nedStark)</td> 
   </tr>
</table>

## Node 7 - calls Node 8
Because local variable `level > 0`, This frame hits the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level); // Recursive call to Node 8 (aryaStark)
                return stackResponse;
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 7 (sansaStark)</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/sansaStark.jpg"></td>
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
     <td>find(aryaStark, "Arya Stark", 2)</td>
     <td>awaiting return from Node 8 (aryaStark)</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>N/A</td>
     <td>will eventually return to Node 6 (robbStark)</td> 
   </tr>
</table>

## Node 8 - returns `aryaStark` to Node 7
Because local variable `root.data == "Arya Stark"`, This frame hits the base case and finds Arya!

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (root.data.equals(data)) // base case
            return root; // recursive return to Node 7 (sansaStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 7 (aryaStark)</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/aryaStark.jpg"></td>
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
     <td>aryaStark</td>
     <td>returning to Node 7 (sansaStark)</td> 
   </tr>
</table>

## Node 7 - returns `aryaStark` to Node 6
Node 7 (sansaStark) receives `stackReponse == aryaStark` from Node 8 (aryaStark), so it finishes the stack by returning stackResponse to Node 6 in this code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level);
            if (stackResponse != null)
                return stackResponse; // Recursive return to Node 6 (robbStark)
        }
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 7 (sansaStark)</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/sansaStark.jpg"></td>
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
     <td>aryaStark</td>
     <td>received from Node 8 (aryaStark)</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>aryaStark</td>
     <td>returning to Node 6 (robbStark)</td> 
   </tr>
</table>

## Node 6 - returns `aryaStark` to Node 1
Node 6 (robbStark) receives `stackReponse == aryaStark` from Node 7 (sansaStark), so it finishes the stack by returning stackResponse to Node 1 in this code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        if (level > 0) {
            stackResponse = find(root.nextSibling, data, level);
            if (stackResponse != null)
                return stackResponse; // Recursive return to Node 1 (nedStark)
        }
```

<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 6 (robbStark)</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>root</td>
    <td><img src="https://github.com/gurkamalpsc/binary-search-trees/blob/master/img/robbStark.jpg"></td>
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
     <td>aryaStark</td>
     <td>received from Node 7 (sansaStark)</td> 
   </tr>
    <tr>
     <td>return value</td>
     <td>aryaStark</td>
     <td>returning to Node 1 (nedStark)</td> 
   </tr>
</table>

## Node 1 - returns `aryaStark` to Node 0
Node 1(nedStark) now has received `aryaStark` from Node 6 (robbStark), so it finishes the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive return to Node 0 (rickardStark)
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 1 (nedStark)</th>
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
     <td>null</td>
     <td>received from Node 2 (brandonStark)</td> 
  </tr>
  <tr>
     <td>return value</td>
     <td>aryaStark</td>
     <td>received from Node 6 (robbStark) & returning to Node 0 (rickardStark)</td> 
   </tr>
</table>

## Node 0 - returns `aryaStark` to Function
Node 0 (rickardStark) receives `aryaStark` from Node 1 (nedStark) Because local variable `level == 0`, This frame finishes the following code block from 

`private TreeNode find(TreeNode root, String data, int level)`
```java
        return find(root.firstChild, data, ++level); // Recursive return to Function
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Node 0 (rickardStark)</th>
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
     <td>aryaStark</td>
     <td>received from Node 1 (nedStark) & returning to Function</td> 
    </tr>
</table>

## Function - returns `aryaStark` to Global
This frame receives `aryaStark` from Node 0 (rickardStark) and finishes the following code block from

`public TreeNode find(String data)`
```java
        return find(mRoot, data, 0); // Recursive return to Global
```
<table>
  <tr>
    <th>Stack Frame</th>
    <th>Function</th>
  </tr>
  <tr>
    <th>local variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>mRoot</td>
    <td>rickardStark</td>
  </tr>
  <tr>
    <td>data</td>
    <td>"Arya Stark"</td>
  </tr>
   <tr>
     <td>return value</td>
     <td>aryaStark</td>
     <td>received from Node 0 & returning to Global</td> 
    </tr>
</table>

## Global - returns `aryaStark` to `result`
This frame receives `aryaStark` from Function and finishes the following global code statement:
```java
TreeNode result = starkFamilyTree.find("Arya Stark"); // end of recusrsion
```

<table>
  <tr>
    <th>Stack Frame</th>
    <th>Global</th>
  </tr>
  <tr>
    <th>global variable</th>
    <th>value</th>
  </tr>
  <tr>
    <td>result</td>
    <td>aryaStark</td>
    <td>received from Function</td> 
  </tr>
</table>
