import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProfilingTestApp {

    private static final int THREAD_COUNT = 4;
    private static final Lock lock = new ReentrantLock();
    private static final String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {
        System.out.println("Starting multithreaded calculations...");

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new Task(), "TaskThread-" + i);
            threads.add(thread);
            thread.start();
        }

        // Join threads to ensure all complete before program exits
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }
    }

    static class Task implements Runnable {
        private static final int MEMORY_ALLOCATION_SIZE = 1000;
        private static final long ITERATIONS = 10000;

        @Override
        public void run() {
            while (true) {
                performLockedCalculations();
                allocateMemory();
                performNetworkAccess();
                writeToFile();
            }
        }

        private void performLockedCalculations() {
            lock.lock();
            try {
                double result = 0;
                for (long i = 0; i < ITERATIONS; i++) {
                    result += Math.sin(i) * Math.cos(i);
                }
                if (result % 1000 == 0) {
                    System.out.println(Thread.currentThread().getName() + " - Locked Calc Result: " + result);
                }
            } finally {
                lock.unlock();
            }
        }

        private void allocateMemory() {
            List<Double> numbers = new ArrayList<>(MEMORY_ALLOCATION_SIZE);
            for (int i = 0; i < MEMORY_ALLOCATION_SIZE; i++) {
                numbers.add(Math.random());
            }
            System.out.println(Thread.currentThread().getName() + " - Memory allocation done.");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(Thread.currentThread().getName() + " - Sleep interrupted.");
            }
        }

        private void performNetworkAccess() {
            try {
                URL url = new URL("http://example.com"); // Simplified example; use a suitable test URL or mock server
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                System.out.println(Thread.currentThread().getName() + " - Network Response Code: " + responseCode);
                connection.disconnect();
            } catch (IOException e) {
                System.err.println(Thread.currentThread().getName() + " - Network error: " + e.getMessage());
            }
        }

        private void writeToFile() {
            lock.lock();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
                writer.write(Thread.currentThread().getName() + " - Writing to file.\n");
                System.out.println(Thread.currentThread().getName() + " - Disk write complete.");
            } catch (IOException e) {
                System.err.println(Thread.currentThread().getName() + " - File write error: " + e.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }
}
