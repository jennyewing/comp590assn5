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
    // Essentially logging fn so we can see system time and the action executed
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
		String t = ((double) System.nanoTime() / 1_000_000_000).toString();
                doAction(t + ": Philosophizing");
                synchronized (leftFork) { // In other class switching order of left/right fork objects changes the order of pick up to prevent deadlock
		    t = ((double) System.nanoTime() / 1_000_000_000).toString();
                    doAction(t + ": Pick up left fork");
                    synchronized (rightFork) {
                        // Philosopher can eat now with both forks
			t = ((double) System.nanoTime() / 1_000_000_000).toString();
                        doAction(t + ": Pick up right fork and feast"); 
		        t = ((double) System.nanoTime() / 1_000_000_000).toString();
                        doAction(t + ": Put down right fork");
                    }
                    // Philosopher is back to thinking/has put down a left fork for another to pick up
		    t = ((double) System.nanoTime() / 1_000_000_000).toString();
                    doAction(t + ": Put down left fork and philosophize some more");
                }
            }
        } catch (InterruptedException e) { 
            Thread.currentThread().interrupt();
            return;
        }
    }
}
