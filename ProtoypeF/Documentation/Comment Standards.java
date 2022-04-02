//package

//imports

//every class and function should be commented as written

/**
 * Standards for Comments and code conventions
 *
 * @author Conner McKevitt
 * @version 1.0
 * @see if extends or implements a class see that class
 */
public class CommentCode{

    //number of times to say hi
    public int timeHi;

    /**
     * Constructor for CommentCode class
     * @param num int times to say hi
     */
    public CommentCode(int timeHi){
        this.timeHi = timeHi;
    }

    //String for use in sayHi for ...
    public String hi = "hello world";

    /**
     * Prints string hi to the terminal timeHi amount of times.
     * If sayBye is true will say bye at the end
     * @param sayBye boolean if true says bye
     * @return boolean true if finished saying hi
     */
    public boolean sayHi(boolean sayBye){

        //loop for printing hi
        for(int i = 0; i < timeHi; i++){
            System.out.println(hi);
        }
        //check if print bye
        //NOTE: use == for readability
        if(sayBye == true){
            System.out.println("bye");
        }
    }
}