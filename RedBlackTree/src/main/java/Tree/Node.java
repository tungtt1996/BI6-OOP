package Tree;

/**
 * Created by Kim Anh Tao on 12-Nov-16.
 */
public class Node {
    int val;
    char color;
    Node parent; // root co bo la null
    Node leftChild; // variable type giong nhu int, char
    Node rightChild;
    boolean nullNode;// true false

    public Node(int val) {
        this.val = val;
        color = 'R'; //node tao moi thanh red
        parent = null;
        rightChild = new Node();
        leftChild = new Node();
        nullNode = false;
    }
    public Node(){ // null node
        val = 0;
        color = 'B';
        parent = null;
        rightChild = null;
        leftChild = null;
        nullNode = true;
    }
}


