import Tree.Node;
import Tree.BineryTree;
import Tree.RedBlackTree;

/**
 * Created by Kim Anh Tao on 12-Nov-16.
 */
public class Main {
    public static void main(String[] args){
        RedBlackTree tree = new RedBlackTree();
        //BineryTree tree = new BineryTree();
        int[] array = {10,1,3,100,49,14,15,20,85,67,51,16};
        //int[] array = {1,2,3,4,5,6,7,8,9,10};
        //int[] array = {10,-30,30,50};
        for(int i = 0; i<array.length; i++){
            tree.addNode(new Node(array[i]));
        }
        System.out.println("");
        tree.preOrder(tree.getRoot());

        tree.deleteNode(49);
        System.out.println("");
        tree.preOrder(tree.getRoot());
    }
}
