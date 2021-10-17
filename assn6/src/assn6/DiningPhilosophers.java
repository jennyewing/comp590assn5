package assn6;

public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];
        
        //Use generic Java objects to represent forks
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {
        	//Designates left and right fork for each philosopher as we go through the loop 
            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            //Designate any one philosopher to pick up the right fork first (i.e. third philosopher)
            //This step prevents deadlock by preventing the circular wait condition
            if (i == philosophers.length - 3) {
                philosophers[i] = new Philosopher(rightFork, leftFork); 
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }
            
            //Indicates which philosopher begins the new thread 
            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }
}

