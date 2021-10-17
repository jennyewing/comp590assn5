package assn6;

public class Philosopher implements Runnable {
	
	private Object leftFork;
    private Object rightFork;

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }
    
    // Instructs a philosopher to perform an action
    // Suspends the invoking thread for a random amount of time
    // Meaning the execution order isn't enforced by time alone
    private void doAction(String action) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + action);
        Thread.sleep(((int) (Math.random() * 100)));
    }
    
    // Lock acquiring a fork so that no two philosophers can acquire the same one at the same time
    // Do this using synchronized keyword
    public void run() {
        try {
            while (true) {
                // Philosopher is thinking/just has a left fork
                doAction(System.nanoTime() + ": Think");
                synchronized (leftFork) {
                    doAction(System.nanoTime() + ": Pick up left fork");
                    synchronized (rightFork) {
                        // Philosopher is eating/has picked up a right fork
                        doAction(System.nanoTime() + ": Pick up right fork and feast"); 
                        doAction(System.nanoTime() + ": Put down right fork");
                    }
                    // Philosopher is back to thinking/has put down a left fork
                    doAction(System.nanoTime() + ": Put down left fork and think");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
   }
