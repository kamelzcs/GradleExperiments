import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Node {
    int key;
    int value;
    Node previous;
    Node next;

    Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.previous = this;
        this.next = this;
    }

    void delete() {
        Node nodePrevious = this.previous;
        Node nodeNext = this.next;
        nodePrevious.next = nodeNext;
        nodeNext.previous = nodePrevious;

        this.previous = null;
        this.next = null;
    }
}

public class LRUCache {
    private static final Logger log = LogManager.getLogger(LRUCache.class);
    private Map<Integer, Node> map = new HashMap<>();
    private Node head = new Node(0, 0);
    private int capacity;
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        //check whether the node is present
        Node foundNode = map.get(key);
        if (foundNode == null){
            return -1;
        }
        else{
            moveNodeToEnd(foundNode);

            return foundNode.value;
        }

    }
    private void insertNodeToTheEnd(Node newNode){
        Node tail = head.previous;
        tail.next = newNode;
        newNode.previous = tail;
        newNode.next = head;
        head.previous = newNode;
    }
    private void moveNodeToEnd(final Node node){
        node.delete();
        insertNodeToTheEnd(node);
    }

    public void put(int key, int value) {
        //check whether the node is present
        Node foundNode = map.get(key);
        if (foundNode != null) {
            foundNode.value = value;
            moveNodeToEnd(foundNode);
        } else{
            //insert the new node
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertNodeToTheEnd(newNode);
            size++;

            if (size > capacity) {
                Node toDeleteNode = head.next;
                map.remove(toDeleteNode.key);
                toDeleteNode.delete();
                size--;
            }
            log.info("current size is {}, capacity is {}", size, capacity);

        }



    }
    public static void main(String[] args){
        LRUCache obj = new LRUCache(3);
        obj.put(1,1);
        obj.put(2,2);
        obj.put(3,3);
        obj.put(4,4);
        System.out.println(obj.get(4));
        System.out.println(obj.get(3));
        System.out.println(obj.get(2));
        System.out.println(obj.get(1));
        obj.put(5,5);
        //obj.put(4,1);
        System.out.println(obj.get(1));
        // obj.put(4,4);
        System.out.println(obj.get(2));
        System.out.println(obj.get(3));
        System.out.println(obj.get(4));
        System.out.println(obj.get(5));


    }
}
