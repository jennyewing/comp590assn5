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
    // essentially logging fn so we can see system time and the action executed
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
                doAction((double) System.nanoTime() / 1_000_000_000 + ": Think");
                synchronized (leftFork) { // in other class switching order of left/right fork objects changes the order of pick up to prevent deadlock
                    doAction((double) System.nanoTime() / 1_000_000_000 + ": Pick up left fork");
                    synchronized (rightFork) {
                        // Philosopher can eat now with both forks
                        doAction((double) System.nanoTime() / 1_000_000_000 + ": Pick up right fork and feast"); 
                        doAction((double) System.nanoTime() / 1_000_000_000 + ": Put down right fork");
                    }
                    // Philosopher is back to thinking/has put down a left fork for another to pick up
                    doAction((double) System.nanoTime() / 1_000_000_000 + ": Put down left fork and think");
                }
            }
        } catch (InterruptedException e) { 
            Thread.currentThread().interrupt();
            return;
        }
    }
}
