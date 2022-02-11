/**
 * This program provides an implementation of the Deque interface
 * and demonstrates it.
 *
 * For a double ended queue where we can only add nodes to either the front or back of the queue, and where
 * we don't need index based access of our collection, a doubly linked list is a data structure we can take
 * full advantage of.
 *
 * The drawbacks of a linked list won't come into play because we don't need to access an arbitrary node in our
 * collection, we are only adding/removing and peeking from the front and back of the queue. We also use a simple
 * counter incrementer to return the size of the collection or use it to tell us if our queue is empty.
 *
 * Completion time, 25 hours
 * 
 * @author Luis A. Gonzalez, Acuna
 * @version 1.1
 */

import java.util.NoSuchElementException;
//implement Iterable<Item> will have to included in the method signature
    
//TODO: implement.

public class GonzalezDeque<Item> implements Deque<Item>{

    /**
     * Program entry point for deque. 
     * @param args command line arguments
     */

    //INTERNAL NODE CLASS
    public class DoublyLinkedListNode<Item>{

        //THE DATA THAT WILL COME ALONG FOR THE RIDE, A REFERENCE TO AN OBJECT
        private Item element;

        private DoublyLinkedListNode<Item> next;
        private DoublyLinkedListNode<Item> previous;

        //CONSTRUCTOR; THE ASSIGNMENT REQUIRES A PARAMETER-LESS CONSTRUCTOR
        public DoublyLinkedListNode(){
            this.element = null;
            this.next = null;
            this.previous = null;
        }


        //ACCESSOR AND MUTATOR METHODS
        public DoublyLinkedListNode<Item> getNext(){
            return next;
        }

        public void setNext(DoublyLinkedListNode<Item> node){
            next = node;
        }

        public DoublyLinkedListNode<Item> getPrevious(){
            return previous;
        }

        public void setPrevious(DoublyLinkedListNode<Item> node){
            previous = node;
        }

        public Item getElement(){
            return element;
        }

        public void setElement(Item elem){
            element = elem;
        }

    }

    //THE FRONT NODE WILL HAVE TO BE UPDATED EVERY TIME WE ADD TO THE FRONT OF THE QUEUE
    private DoublyLinkedListNode <Item> front;

    //THE LAST NODE WILL HAVE TO BE UPDATED EVERY TIME WE ADD TO THE BACK OF THE QUEUE
    private DoublyLinkedListNode <Item> back;

    //COUNTER TO KEEP TRACK OF THE SIZE OF OUR QUEUE
    private int n;


    @Override
    public void enqueueFront(Item element){
        /*
        Adds one element to the front of this deque.
        parameter is the element to be added to the front of the deque
        How do we add this generic object to our queue as a node that stores
        the data it contains?
        allocate the node
        put in the data
        */

        DoublyLinkedListNode<Item> newNode = new DoublyLinkedListNode<Item>();

        //for troubleshooting,
        //making sure the data we pass to the Nodes come along for the ride
        newNode.setElement(element);
        //System.out.println(newNode.getElement());

        //theres a chance that the queue might be empty when we call it
        //if it is, make this newly instantiated node the front node
        if(front == null){
            newNode.previous = null;
            front = newNode;
            newNode.next = null;
            //back = newNode;
            n++;
            return;
        } else if (front != null){
            /*
            IF THE QUEUE IS NOT EMPTY...
            THE NEWLY INSTANTIATED NODE BECOMES THE FRONT NODE OF THE QUEUE
            make its next field point to what's currently at the front...
            and it's previous field NULL (nothing could be ahead of the 'front' of the dequeue)
            */
            newNode.next = front;
            newNode.previous = null;
            /*
            The node that used to be the front node will no longer be the front node
            this former front node has a "previous" member data...
            ... a reference to a node
            it will no longer point to null because it is not the front any more
            it will point to the node we just added with this method call
            */

            front.previous = newNode; // the current front of dequeue now has the newest node ahead in front of it
            /*
            the next link of the previous front node is already referencing
            the correct next node
            our queue does not need to add nodes in between nodes,
            only the front and back of the queue
            resets the front of the queue to point to this newly added node
            */

            front = newNode;

            //we just added to the queue, so increase the counter by one
            n++;
        }
    }

   @Override
   public void enqueueBack(Item element){
        /*
       Adds one element to the back of this deque.
       parameter is the element to be added to the back of the deque
       BUT WE WILL HAVE TO UPDATE WHICH NODE'S "NEXT" DATA MEMBER POINTS TO NULL
       WHEN WE REMOVE A NODE FROM THE BACK OF THE LIST
       WE WILL HAVE TO UPDATE THE REFERENCE STORED IN THE VARIABLE BACK AS WELL
       allocate the node
       PASS in the data
       */

       DoublyLinkedListNode<Item> newNode = new DoublyLinkedListNode<Item>();
       // memory allocation for our new node

       //the data we pass to the Nodes must come along for the ride
       newNode.setElement(element);
       //for troubleshooting purposes to make sure our data is preserved
       //System.out.println(newNode.getElement());

       //allocating a node reference, but not instantiating a node
       //to preserve data we need for later
       DoublyLinkedListNode<Item> tempNode = front;

       /*
       this node we just created will be the last in the queue
       THE LAST NODE IN THE QUEUE WILL ALWAYS POINT TO NULL
       WITH ITS "NEXT" DATA MEMBER
       THIS IS HOW WE WILL KNOW WHERE THE END IS
       */
       newNode.next = null;

       //theres a chance that the queue might be empty when we call this method that adds a node to the list
       //if it is, make this newly instantiated node the front node and the back node
       if(front == null){
           newNode.previous = null; // nothing could possibly be ahead of the front node
           front = newNode; // taking care of what we came here to do
           n++; // book keeping 101
           return;
       }

       /*
       if our list is not empty we are guaranteed at least a 'front' node in our implementation (n >= 1)
       we need a way to traverse the dequeue to get our reference and use it to get this new node to the end of the dequeue
       we start our traversal at the front of the queue using the "tempNode" reference to point to the front node
       */

//       // basically one case where we are not guaranteed a 'back' node reference, when n = 1, and we can use generally...
//       while(tempNode.next != null){
//           tempNode = tempNode.next;
//       }

       // all other cases we can just tell our tempNode to point to the 'back' node attribute of the ADT
       if (back != null) {
           tempNode = back;
       } else {
           // basically one case where we are not guaranteed a 'back' node reference, when n = 1, and we can use generally...
           while(tempNode.next != null){
               tempNode = tempNode.next;
           }
       }

       /*
       if we got to this point in the code, our tempNode's "next" reference
       now points to NULL, its the last node
       whatever node is at the end of the queue, its "next" reference
       must refer to this new node we added
       */
       tempNode.next = newNode;

       /*
       our new node is now the end of the queue,
       its "next" reference points to null
       our "tempNode" is the second to last node in the queue
       our new nodes "previous" reference needs to point to the
       second to last node, which in this case is the
       same reference that tempNode is storing
       */
       newNode.previous = tempNode;

       // complete the update for the ADT after the linked list is taken care of
       back = newNode;

       //we just added a node to the queue, so increment the counter
       n++;

    }


    @Override
    public Item dequeueFront() throws NoSuchElementException{
        //Removes and returns the element at the front of this deque.
        //Throws an exception if the deque is empty.
        if(isEmpty())
            throw new NoSuchElementException();

        //a reference to the node we are removing
        DoublyLinkedListNode<Item> preservedData = front;

        front = preservedData.next;
        front.previous = null;
        //System.out.println("You have removed: " + preservedData.getElement() + "from the front of the queue");
        n--;
        return preservedData.getElement();
    }

   @Override
    public Item dequeueBack() throws NoSuchElementException{
        //Removes and returns the element at the back of this deque.
        //Throw an exception if the deque is empty.
       if(isEmpty())
           throw new NoSuchElementException();

       //a reference to the node we are eventually removing
       DoublyLinkedListNode<Item> tempNode = front;

       DoublyLinkedListNode<Item> tempNodeNewBack;

       /*
       if our list is not empty we need a way to traverse it and get this
       reference to point to the node at the end of the queue
       we start our traversal at the front of the queue, we used our
       "tempNode" reference to point to the front node
       */
       while(tempNode.next != null){
           tempNode = tempNode.next; // moving right along the list using the links
       }

       //this is a spot where having a "back" reference would have been great
       tempNodeNewBack = tempNode.previous;
       tempNodeNewBack.next = null;
       tempNodeNewBack = tempNodeNewBack.previous;
       n--;
        // Return the element that was removed from the back of the deque.
        return tempNode.getElement();
    }

   @Override
    public Item first() throws NoSuchElementException{
        //Returns, without removing, the element at the front of this deque.
       //similar to a peek() function for the front element/node of the queue

        //Should throw an exception if the deque is empty.
       if(isEmpty())
           throw new NoSuchElementException();

        //Return the first element in the deque
       return front.getElement();
    }

    @Override
    public Item last() throws NoSuchElementException{
        //Returns, without removing, the element at the back of this deque.
        //similar to a peek() function for the back element/node of the queue

        //Should throw an exception if the deque is empty.
        if(isEmpty())
            throw new NoSuchElementException();

        //a reference to the node we are eventually removing
        DoublyLinkedListNode<Item> tempNode = front;

        /*
        if our list is not empty we need a way to traverse it and get this
        reference to point to the node at the end of the queue
        we start our traversal at the front of the queue, we used our
        "tempNode" reference to point to the front node
        */
        while(tempNode.next != null){
            tempNode = tempNode.next; // moving right along the list using the links
        }

        // Return the element at the back of the deque.
        return tempNode.getElement();
    }

    @Override
    public boolean isEmpty(){
        //Returns true if this deque is empty and false otherwise.
        return n == 0;
    }

    @Override
    public int size(){
        //Returns the number of elements in this deque.
        return n;
    }


    @Override
    public String toString(){
        /*
        Returns a string representation of this deque.
        The back element occurs first, and each element is separated by a space (meaning front of queue to the rightmost)
        This is where iteration will come in to the frey
        If the deque is empty, returns "empty".
        */

        if(isEmpty()){
            System.out.println("empty");
        }

        //a reference to the node we are eventually removing
        DoublyLinkedListNode<Item> tempNode = front;

        String result;

        /*
        if our list is not empty we need a way to traverse it and get this
        reference to point to the node at the end of the queue
        we start our traversal at the front of the queue, we used our
        "tempNode" reference to point to the front node
        */
        while(tempNode.next != null){
            tempNode = tempNode.next; // moving right along the list using the links
        }

        result = (String) tempNode.getElement().toString() + " ";

        while(tempNode.previous != null){
            //move it back the other way
            tempNode = tempNode.previous;
            //and add it to the result
            result = result + (String) tempNode.getElement().toString() + " ";
        }

        return result;
    }

    public static void main(String[] args) {
        GonzalezDeque<Integer> deque = new GonzalezDeque<>();
        GonzalezDeque<String> dequeString = new GonzalezDeque<>(); // you can resuse this ADT for String items as well!

        //standard queue behavior
        deque.enqueueBack(3);
        deque.enqueueBack(7);
        deque.enqueueBack(4);
        deque.dequeueFront();        
        deque.enqueueBack(9);
        deque.enqueueBack(8);
        deque.dequeueFront();
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());

        //Using the linkedlist methods
        deque.

        //deque features
        /*this is a chaotic line with people cutting in front of others and people in the back of the line
        * being allowed inside. Like a real life queue at a major even where things like this happen.*/
        System.out.println(deque.dequeueFront());        
        deque.enqueueFront(1);
        deque.enqueueFront(11);
        deque.enqueueFront(3);
        deque.enqueueFront(5);
        System.out.println(deque.dequeueBack());
        System.out.println(deque.dequeueBack());        
        System.out.println(deque.last());                
        deque.dequeueFront();
        deque.dequeueFront();        
        System.out.println(deque.first());        
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());

        //standard stack behaviors
        //add to the call stack
        dequeString.enqueueFront("method call 1");
        dequeString.enqueueFront("method call 2");
        dequeString.enqueueFront("method call 3");
        System.out.println(dequeString.first());
        dequeString.enqueueFront("method call 4");
        dequeString.enqueueFront("method call 5");
        System.out.println(dequeString.first());

        //pop off the call stack
        dequeString.dequeueFront();
        dequeString.dequeueFront();
        dequeString.dequeueFront();
        System.out.println(dequeString.first());

        dequeString.enqueueFront("method call 6");

        dequeString.enqueueFront("method call 7");
        dequeString.enqueueFront("method call 8");

    /*    //ACCESSOR AND MUTATOR METHODS
        public DoublyLinkedListNode<Item> getNext(){
            return next;
        }

        public void setNext(DoublyLinkedListNode<Item> node){
            next = node;
        }

        public DoublyLinkedListNode<Item> getPrevious(){
            return previous;
        }

        public void setPrevious(DoublyLinkedListNode<Item> node){
            previous = node;
        }

        public Item getElement(){
            return element;
        }

        public void setElement(Item elem){
            element = elem;
        }*/


    }
}

/**
 12345678901234567890123456789012345678901234567890123456789012345678901234567890
 */