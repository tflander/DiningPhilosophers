The dining philosophers problem illustrates non-composability of low-level synchronization primitives like semaphores. It is a modification of a problem posed by Edsger Dijkstra.
Five philosophers spend their time thinking and eating spaghetti. They eat at a round table with five individual seats. Philosopher's need two forks for serving the spaghetti, his own fork and his neighbor's fork. When a philosopher cannot grab both forks he sits and thinks.

Instructions:

  - Run DiningPhilosophersApp.java from the todd.java packsage
  - Observe that at least one of the rules in the following section are broken.
  - Fix the code so all of the rules pass
  - Bonus: Create a unit test suite.

Rules:

  - A fork can only be used by one philosopher at a time.
  - The philosopher keeps both forks until he is done eating (he may want seconds).
  - The lunch room is open for four seconds.  All philosophers must be done eating and thinking before the lunch room closes.
  - It takes a philosopher a full second to eat (minus time to grab forks).
  - It takes a small (non-zero) amount of time to grab each fork.
  - The algorithm should work equally well if philosophers grab forks from either their left neighbor or their right neighbor.
  - The philosopher must think for a total of two seconds, but may eat in-between thoughts.
  - Philosophers cannot think while eating.
  