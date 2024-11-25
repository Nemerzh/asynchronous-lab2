import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class ArrayUtils {

    public static int[] fillArray(Integer lowLimit, Integer highLimit, Integer size) {
        int[] numbers = new int[size];

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(highLimit - lowLimit) + lowLimit;
        }

        return numbers;
    }

    public static int[][] splitArray(int[] numbers, int countOfThreads) {
        int size = numbers.length;
        int lengthParts = (int) Math.ceil((double) size / countOfThreads);
        int[][] partsForTask = new int[countOfThreads][];

        for (int i = 0; i < countOfThreads; i++) {
            int start = i * lengthParts;
            int end = Math.min(start + lengthParts, size);

            int[] subArray = new int[end - start];
            System.arraycopy(numbers, start, subArray, 0, end - start);
            partsForTask[i] = subArray;
        }

        return partsForTask;
    }

    public static Set<Integer> multiplyEvenOnOdd(int[] subArray) {
        Set<Integer> partialResults = new LinkedHashSet<>();

        for (int i = 0; i < subArray.length - 1; i += 2) {
            partialResults.add(subArray[i] * subArray[i + 1]);
        }

        // Якщо кількість елементів непарна, додаємо останній елемент як є
        if (subArray.length % 2 != 0) {
            partialResults.add(subArray[subArray.length - 1]);
        }

        return partialResults;
    }

}
