package Tree;

/**
 * Created by Kim Anh Tao on 12-Nov-16.
 */
public class BineryTree {
    Node root;
    //add new node
    public void addNode(Node newNode){ // Node la declared data type
        int val = newNode.val;
        if(root == null){ //khong co node nao thi new node la root
            root = newNode;
        }
        else{
            Node currNode = root; //trong pham vi be thi current node la root
            while(true) {
                Node parent = currNode; //chuyen currNode len chuc bo
                if (val > currNode.val) {
                    currNode = currNode.rightChild;
                    if (currNode.nullNode){ //nullnode cua node moi nhap la true hay false, o day se la false.khong khac gi viet la if (true) hoac if(false)
                        parent.rightChild = newNode;
                        newNode.parent = parent;
                        newNode.rightChild.parent = newNode;
                        newNode.leftChild.parent = newNode;
                        return;
                    }
                } else {
                    currNode = currNode.leftChild; //tuong tu cai tren
                    if (currNode.nullNode){
                        parent.leftChild = newNode;
                        newNode.parent = parent;
                        newNode.rightChild.parent = newNode;
                        newNode.leftChild.parent = newNode;
                        return;
                    }
                }
            }
        }
    }
    //find node
    public Node findNode(int val){
        if(root == null){
            System.out.println("This tree is empty!");
            return null;
        }
        else{
            Node currNode = root;
            while (true) { //chay vong lap vinh cuu
                if (val == currNode.val) {
                    return currNode;
                } else if (val > currNode.val) {
                    currNode = currNode.rightChild;
                } else {
                    currNode = currNode.leftChild;
                }
                if (currNode.nullNode) {//nullNode =true/false
                    return null;
                }
            }
        }
    }
    //delete node
    public void deleteNode(int val){//overloading
        if (root == null) {
            System.out.println("This tree is empty");
            return;
        }
        Node deleteNode = findNode(val);
        deleteNode(deleteNode);
    }
    protected void deleteNode(Node deleteNode) {//overloading

        if (deleteNode == null) {
            return;
            //delete node with no child
        } else if (isLeaf(deleteNode)) {
            if (isLeftChild(deleteNode)) {
                deleteNode.parent.leftChild = new Node(); // no val -> Null Node
                deleteNode.parent.leftChild.parent = deleteNode.parent;
            } else {
                deleteNode.parent.rightChild = new Node();
                deleteNode.parent.rightChild.parent = deleteNode.parent;
            }
            deleteNode=null;
            //delete node with 1 child
        } else if (deleteNode.leftChild.nullNode|| deleteNode.rightChild.nullNode) {
                Node replaceNode = (deleteNode.leftChild.nullNode)? deleteNode.rightChild : deleteNode.leftChild;
                replaceNode.parent = deleteNode.parent;
                if (isLeftChild(deleteNode)) {
                    replaceNode.parent.leftChild = replaceNode;
                } else {
                    replaceNode.parent.rightChild = replaceNode;
                }
            deleteNode = null;
            //delete node with 2 children
        } else {
            Node replaceNode = findMax(deleteNode.leftChild);
            deleteNode.val=replaceNode.val;
            deleteNode(replaceNode);
        }
    }
    //find max min
    public Node findMax(){
        if(root == null){
            return null;
        }
        Node currNode = root;
        while(!currNode.rightChild.nullNode){
            currNode = currNode.rightChild;
        }
        return currNode;
    }
    public Node findMin() {
        if (root == null) {
            return null;
        }
        Node currNode = root;
        while (!currNode.leftChild.nullNode) {
            currNode = currNode.leftChild;
        }
        return currNode;
    }

    //preOrder
    public void preOrder(Node node){
        if(!node.nullNode) {//khi ma la Node xin thi no chay va khi nullnode thi thoi khong chay
            System.out.print(node.val+"- ");
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
        else{
            System.out.print(" | ");
        }
    }
    //private method
    protected boolean isLeaf(Node node){ //isleaf o day se cho ra true hoac false, va su dung no nhu 1 cai cong tac
        return (node.leftChild.nullNode && node.rightChild.nullNode);// && nghia la and. return o day true
    }
    protected boolean isLeftChild(Node node){
        return (node.parent.leftChild == node);
    }
    //find Min Max for sub tree
    protected Node findMax(Node root){ //root la 1 node dc nhap vao
        if(root == null){
            return null;
        }
        Node currNode = root;
        while(!currNode.rightChild.nullNode){
            currNode = currNode.rightChild;
        }
        return currNode;
    }
    protected Node findMin(Node root){
        if(root == null){
            return null;
        }
        Node currNode = root;
        while(!currNode.leftChild.nullNode){
            currNode = currNode.leftChild;
        }
        return currNode;
    }
    public Node getRoot(){
        return root;
    }
}
