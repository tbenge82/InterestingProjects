
package stackpack;

/**
 *
 * @author awhitley
 * @modified by Thomas Benge
 * CSCI 202 Homework 5
 * 11/2/2012
 * 
 * This class is meant to be a Stack and is implemented by using an array to 
 * perform methods that a stack would use.
 */
public class StackArrayBased implements StackInterface {

    int maxSize = 50;
    private Object items[]; // used to store the items
    private int top; // the index of the "top" item, in the items array
    // The top item always has the largest index of all items in the stack.
    // An empty stack has top at -1.  A stack with one item has top at 0.

    public StackArrayBased() {
        items = new Object[maxSize]; // allocate memory to hold maxSize items
        top = -1; // that means the stack is empty
    }

    @Override
    public boolean isEmpty() {
        return top < 0;
    }

    @Override
    public void push(Object item) {
        if (top == maxSize - 1) { 
            Object moreItems[];
            moreItems = new Object[maxSize * 2];
            for (int i = 0; i < maxSize; i++) {  //Moves all items into a larger array, 
                moreItems[i] = items[i];         //then makes items refer to the new array
            }
            maxSize *= 2;
            items = moreItems;
        }
        items[++top] = item; // increment top, THEN use top to add the item 
    }

    @Override
    public Object peek() throws StackEmptyException {
        if (top < 0) { // stack is empty
            throw new StackEmptyException(
                    "StackEmptyException on peek. Stack is empty.");
        }
        return items[top];
    }

    @Override
    public Object pop() throws StackEmptyException {
        if (top < 0) { // stack is empty
            throw new StackEmptyException(
                    "StackEmptyException on pop. Stack is empty.");
        }
        return items[top--]; // use top, THEN decrement, then return
    }

    @Override
    public void popAll() {
        maxSize = 50;  //Resets maxSize since it is now capable of growing.
        items = new Object[maxSize]; // allocate fresh memory to hold 50 items
        top = -1; // that means the stack is empty
    }

    public void flip() {  //Flips the stack upside down.
        Object tempStack[];
        tempStack = new Object[maxSize];
        for (int i = 0; i < top + 1; i++) {
            tempStack[i] = items[(top - i)];
        }
        items = tempStack;
    }
    
    public void combine(StackArrayBased newStack){ //Sits the new stack on top of the stack that calls this function
       for(int i = 0; i <= newStack.top; i++){      
           push(newStack.items[i]);                 
       } //used a loop of pushes so I wouldn't have to worry about manually changing maxSize again.
       newStack.popAll();  //Empties the stack that gets pushed onto the stack calling this function.
    }
}
