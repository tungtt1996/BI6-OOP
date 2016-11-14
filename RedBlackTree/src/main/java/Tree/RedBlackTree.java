package Tree;

/**
 * Created by Kim Anh Tao on 12-Nov-16.
 */
public class RedBlackTree extends BineryTree{
    public void preOrder(Node node) {
        if(!node.nullNode) {
            System.out.print(node.val+""+node.color+"- ");
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
        else{
            System.out.print(" | ");
        }
    }

    public void addNode(Node newNode) {
        int val = newNode.val;
        if(root == null){
            newNode.color = 'B';
            root = newNode;
            newNode.parent = null;
            newNode.rightChild.parent = newNode;
            newNode.leftChild.parent = newNode;
        }
        else{
            Node currNode = root;
            while(true) {
                Node parent = currNode;
                if (val > currNode.val) {
                    currNode = currNode.rightChild;
                    if (currNode.nullNode){
                        parent.rightChild = newNode;
                        newNode.parent = parent;
                        newNode.rightChild.parent = newNode;
                        newNode.leftChild.parent = newNode;
                        checkAddRules(newNode);
                        return;
                    }
                } else {
                    currNode = currNode.leftChild;
                    if (currNode.nullNode){
                        parent.leftChild = newNode;
                        newNode.parent = parent;
                        newNode.rightChild.parent = newNode;
                        newNode.leftChild.parent = newNode;
                        checkAddRules(newNode);
                        return;
                    }
                }
            }
        }
        //end adding
    }
    //check BRBTree rules
    private void checkAddRules(Node newNode){
        if(newNode.parent.color == 'B'){
            return;
        }
        else {
            Node parent = newNode.parent;
            //find uncle
            Node uncle = brother(parent);
            //check uncle
            //black uncle -> rotate
            if(uncle.color == 'B'){
                //LL
                if(parent.parent.leftChild == parent && parent.leftChild == newNode){
                    rotateR(parent,true);
                }
                //RR
                else if(parent.parent.rightChild == parent && parent.rightChild == newNode){
                    rotateL(parent,true);
                }
                //LR
                else if(parent.parent.leftChild == parent && parent.rightChild == newNode){
                    rotateL(newNode,false);
                    rotateR(newNode,true);
                }
                //RL
                else{
                    rotateR(newNode,false);
                    rotateL(newNode,true);
                }
            }
            //red uncle -> recolor
            else{
                if(parent.parent != root){
                    parent.parent.color = 'R';
                }
                parent.color = uncle.color = 'B';
                if(parent.parent.color == 'R') {
                    checkAddRules(parent.parent);
                }
            }
        }
    }
    //delete BRNode
    public void deleteNode(int val){
        if (root == null) {
            System.out.println("This tree is empty");
            return;
        }
        Node deleteNode = findNode(val);
        deleteNode(deleteNode);
    }
    protected void deleteNode(Node deleteNode){
        if(deleteNode == null){
            return;
        }

        if (deleteNode.rightChild.nullNode || deleteNode.leftChild.nullNode) {
            deleteNodeOneChild(deleteNode);
        } else {
            Node replaceNode = findMin(deleteNode.rightChild);
            deleteNode.val = replaceNode.val;
            deleteNode(replaceNode);
        }
    }
    private void deleteNodeOneChild(Node deleteNode) {
        Node child = (!deleteNode.rightChild.nullNode) ? deleteNode.rightChild : deleteNode.leftChild;
        BRreplaceWithChild(deleteNode, child);
        //deleteNode is 'R' then stop here
        //double 'B' cases
        if(deleteNode.color == 'B') {
            if (child.color == 'B') {
                deleteCase1(child);
            }//child is 'R'
            else {
                child.color = 'B';
            }
            deleteNode = null;
        }
    }

    private void deleteCase1(Node doubleBlackNode){

        if(doubleBlackNode.parent == null){
            root = doubleBlackNode;
            return;
        }
        deleteCase2(doubleBlackNode);
    }
    private void deleteCase2(Node doubleBlackNode){
        Node brother = brother(doubleBlackNode);
        if(brother.color == 'R'){
            if(isLeftChild(brother)){
                rotateR(brother,true);
            }else {
                rotateL(brother,true);
            }
        }
        deleteCase3(doubleBlackNode);
    }
    private void deleteCase3(Node doubleBlackNode){
        Node brother = brother(doubleBlackNode);
        if(doubleBlackNode.parent.color == 'B' && brother.color == 'B'
                && brother.leftChild.color == 'B' && brother.rightChild.color == 'B'){
            brother.color = 'R';
            deleteCase1(doubleBlackNode.parent);
            return;
        }
        deleteCase4(doubleBlackNode);
    }
    private void deleteCase4(Node doubleBlackNode){
        Node brother = brother(doubleBlackNode);
        if(doubleBlackNode.parent.color == 'R' && brother.color == 'B'
                && brother.leftChild.color == 'B' && brother.rightChild.color == 'B'){
            doubleBlackNode.parent.color = 'B';
            brother.color = 'R';
            return;
        }
        deleteCase5(doubleBlackNode);
    }
    private void deleteCase5(Node doubleBlackNode){
        Node brother = brother(doubleBlackNode);
        if(brother.color == 'B'){
            if(isLeftChild(doubleBlackNode) && brother.rightChild.color == 'B' && brother.leftChild.color == 'R') {
                rotateR(brother.leftChild, true);
            }else if(!isLeftChild(doubleBlackNode) && brother.leftChild.color == 'B' && brother.rightChild.color == 'R'){
                rotateL(brother.rightChild, true);
            }
        }
        deleteCase6(doubleBlackNode);
    }
    private void deleteCase6(Node doubleBlackNode){
        Node brother = brother(doubleBlackNode);
        brother.color = brother.parent.color;
        brother.parent.color = 'B';
        if(isLeftChild(doubleBlackNode)){
            brother.rightChild.color = 'B';
            rotateL(brother,false);
        }else{
            brother.leftChild.color = 'B';
            rotateR(brother,false);
        }
        if(brother.parent == null){
            root = brother;
        }
    }
    //rotate
    private void rotateL(Node node,boolean changeColor){
        Node parent = node.parent;
        node.parent = parent.parent;
        if(node.parent != null){
            if(isLeftChild(parent)){
                parent.parent.leftChild = node;
            }else {
                parent.parent.rightChild = node;
            }
        }
        Node left = node.leftChild;
        node.leftChild = parent;
        if(root == parent) {
            root = node;
        }
        parent.parent = node;
        parent.rightChild = left;
        if(!left.nullNode){
            left.parent = parent;
        }
        if(changeColor){
            char temp = node.color;
            node.color = parent.color;
            parent.color = temp;
        }
    }
    private void rotateR(Node node, boolean changeColor){
        Node parent = node.parent;
        node.parent = parent.parent;
        if(node.parent != null){
            if(isLeftChild(parent)){
                parent.parent.leftChild = node;
            }else {
                parent.parent.rightChild = node;
            }
        }
        Node right = node.rightChild;
        node.rightChild = parent;
        if(root == parent) {
            root = node;
        }
        parent.parent = node;
        parent.leftChild = right;
        if(!right.nullNode){
            right.parent = parent;
        }
        if(changeColor){
            char temp = node.color;
            node.color = parent.color;
            parent.color = temp;
        }
    }
    //find brother
    private Node brother(Node node){
        if(isLeftChild(node)){
            return node.parent.rightChild;
        }
        else{
            return node.parent.leftChild;
        }
    }
    private void BRreplaceWithChild(Node deleteNode,Node child) {
        child.parent = deleteNode.parent;
        if (isLeftChild(deleteNode)) {
            deleteNode.parent.leftChild = child;
        } else {
            deleteNode.parent.rightChild = child;
        }
        deleteNode = null;
    }
}
