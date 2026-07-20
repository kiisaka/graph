package org.example;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        // impossible is a sentinel value larger than any valid answer.
        // If coin 1 exists, the worst valid answer is amount coins.
        // So amount + 1 safely represents "not reachable."
        int impossible = amount + 1;

        // minCoinsForAmount[a] means:
        // the minimum number of coins needed to make amount a.
        int[] minCoinsForAmount = new int[amount + 1];

        // Initially, every positive amount is unreachable.
        Arrays.fill(minCoinsForAmount, impossible);

        // Amount 0 requires zero coins.
        minCoinsForAmount[0] = 0;

        // Build answers from smaller amounts up to the target amount.
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            // Try using each coin as the last coin in the combination.
            for (int coin : coins) {
                // If the coin is not larger than the current amount,
                // then we can try to build currentAmount from currentAmount - coin.
                if (coin <= currentAmount) {
                    minCoinsForAmount[currentAmount] = Math.min(
                            minCoinsForAmount[currentAmount],
                            minCoinsForAmount[currentAmount - coin] + 1
                    );
                }
            }
        }

        // If the target amount is still marked impossible, no combination exists.
        if (minCoinsForAmount[amount] == impossible) {
            return -1;
        }

        return minCoinsForAmount[amount];
    }

    public int coinChangeBfs(int[] coins, int amount) {
        // Amount 0 requires zero coins.
        if (amount == 0) {
            return 0;
        }

        // visited[a] means amount 'a' has already been reached.
        // The first time BFS reaches an amount, it uses the fewest coins.
        boolean[] visited = new boolean[amount + 1];

        // Queue stores amounts reachable with the current number of coins.
        Queue<Integer> queue = new ArrayDeque<>();

        // Start from amount 0.
        queue.offer(0);
        visited[0] = true;

        // coinsUsed is the BFS depth.
        // Each BFS layer represents using one more coin.
        int coinsUsed = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            // Process all amounts reachable using exactly coinsUsed coins.
            for (int i = 0; i < levelSize; i++) {
                int currentAmount = queue.poll();

                // Try adding each coin.
                for (int coin : coins) {
                    int nextAmount = currentAmount + coin;

                    // Skip amounts larger than the target.
                    if (nextAmount > amount) {
                        continue;
                    }

                    // If we reach the target, this is the minimum number of coins
                    // because BFS explores by increasing coin count.
                    if (nextAmount == amount) {
                        return coinsUsed + 1;
                    }

                    // Visit this amount only once.
                    if (!visited[nextAmount]) {
                        visited[nextAmount] = true;
                        queue.offer(nextAmount);
                    }
                }
            }

            // Move to the next BFS layer.
            coinsUsed++;
        }

        // Target amount is not reachable using the given coins.
        return -1;
    }
}