package org.example;

public class TaskScheduler {
    public int leastInterval(char[] tasks, int cooldown) {
        int[] frequencies = new int[26];

        // Count each task type.
        for (char task : tasks) {
            frequencies[task - 'A']++;
        }

        int maxFrequency = 0;
        int numTasksWithMaxFrequency = 0;

        // Find the highest frequency and how many tasks have that frequency.
        for (int frequency : frequencies) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                numTasksWithMaxFrequency = 1;
            } else if (frequency == maxFrequency) {
                numTasksWithMaxFrequency++;
            }
        }

        // Build a frame around the most frequent task.
        // Example: A _ _ A _ _ A
        // There are maxFrequency - 1 full gaps, each of size cooldown + 1.
        int frameLength = (maxFrequency - 1) * (cooldown + 1) + numTasksWithMaxFrequency;

        // If there are enough other tasks to fill idle slots,
        // the answer is just tasks.length.
        return Math.max(tasks.length, frameLength);
    }
}
