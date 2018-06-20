import java.util.concurrent.*;


public class NumbersProducer implements Runnable {
    private BlockingQueue<Integer> numbersQueue;
    private final int poisonPill;
    private final int poisonPillPerProducer;

    public NumbersProducer(BlockingQueue<Integer> numbersQueue, int poisonPill, int poisonPillPerProducer) {
        this.numbersQueue = numbersQueue;
        this.poisonPill = poisonPill;
        this.poisonPillPerProducer = poisonPillPerProducer;
    }
    public void run() {
        try {
            generateNumbers();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void generateNumbers() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            String threadName = current.toString();
            numbersQueue.put(current.nextInt(100));
            System.out.println(threadName + " was given a number");

        }
        System.out.println("===================\nfirst for loop is done\n===================");
        for (int j = 0; j < poisonPillPerProducer; j++) {
            numbersQueue.put(poisonPill);
            System.out.println("added a poison pill");
        }
        System.out.println("===================\nsecond for loop is done\n===================");
    }
}