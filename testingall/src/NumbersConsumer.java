
import javax.swing.*;
import java.util.concurrent.*;

public class NumbersConsumer implements Runnable {
    private BlockingQueue<Integer> queue;
    private final int poisonPill;

    public NumbersConsumer(BlockingQueue<Integer> queue, int poisonPill) {
        this.queue = queue;
        this.poisonPill = poisonPill;
    }
    public void run() {
        try {
            while (true) {
                Integer number = queue.take();
                if (number.equals(poisonPill)) {
                    System.out.println("===================\n" + Thread.currentThread().getName() + "has completed!\n===================");
                    //System.out.println();
                    return;
                }
                //long counter = System.currentTimeMillis();
                //int fibme = fib(number%40);
                System.out.println(Thread.currentThread().getName() + " result: " + number + " \n Fib equivalent = " /*+ fibme*/);
                //counter = System.currentTimeMillis() - counter;

                //System.out.printf("        time required: %d%n", counter);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int fib(int n){
        if(n==0 || n==1){
            return n;
        }else{
            if(n < 0){
                return 0;
            }
        }
        return fib(n-1) + fib(n-2);
    }
}