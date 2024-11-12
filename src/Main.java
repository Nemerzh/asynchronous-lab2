import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        int[] numbers = ArrayUtils.fillArray(0, 100, 50);
        int countOfThreads = 5;

        int[][] splitArray = ArrayUtils.splitArray(numbers, countOfThreads);
        for (int i = 0; i < splitArray.length; i++) {
            System.out.println(Arrays.toString(splitArray[i]));
        }
        // Використовуємо CopyOnWriteArraySet для збереження результатів
        Set<Integer> results = new CopyOnWriteArraySet<>();
        List<Future<Set<Integer>>> futures = new ArrayList<>();

        // ExecutorService для керування потоками
        ExecutorService executor = Executors.newFixedThreadPool(countOfThreads);

        for (int i = 0; i < splitArray.length; i++) {
            int taskNumber = i + 1;
            int[] arrayForTask = splitArray[i];
            Callable<Set<Integer>> task = () -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task " + taskNumber + " running in thread " + threadName);
                Set<Integer> partialResults;
                partialResults = ArrayUtils.multiplyEvenOnOdd(arrayForTask);
                return partialResults;
            };
            futures.add(executor.submit(task));
        }

        for (Future<Set<Integer>> future : futures) {
            results.addAll(future.get());
        }

        // Перевірка статусу завдань
        for (Future<Set<Integer>> future : futures) {
            if (!future.isCancelled()) {
                while (!future.isDone()) {
                    System.out.println("Check out the calculation results...");
                    Thread.sleep(200);
                }
            }
            else {
                System.err.println("Canceling a calculation");
            }
        }

        // Завершуємо ExecutorService
        executor.shutdown();

        // Виводимо результати
        System.out.println("Попарні добутки: " + results);

        System.out.println("Execution Time: " + (System.currentTimeMillis() - start) + " ms");
    }
}
