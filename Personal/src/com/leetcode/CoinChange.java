package com.leetcode;

import java.util.Arrays;

public class CoinChange {
    //  Can be solved a bit like fibonacci, except with
    //  Faster Algorithm but consumes memory
    public int coinChange(int[] coins, int amount) {
        if (amount < 0) return -1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // acts like infinity
        dp[0] = 0;

        for (int a = 1; a <= amount; a++) {
            for (int c : coins) {
                if (a - c >= 0) {
                    dp[a] = Math.min(dp[a], dp[a - c] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    //  Slower algorithm but somewhat saves memory
    public int coinChange2(int[] coins, int amount) {
        int[] memo = new int[amount + 1];
        Arrays.fill(memo, Integer.MIN_VALUE); // -2 means "not computed"
        return dfs(coins, amount, memo);
    }

    private int dfs(int[] coins, int reminder, int[] memo) {
        if (reminder == 0) return 0;
        if (reminder < 0) return -1;

        if (memo[reminder] != Integer.MIN_VALUE) return memo[reminder];

        int best = Integer.MAX_VALUE;

        for (int c : coins) {
            int res = dfs(coins, reminder - c, memo);
            if (res >= 0 && res < best) {
                best = res + 1;
            }
        }

        memo[reminder] = (best == Integer.MAX_VALUE) ? -1 : best;
        return memo[reminder];
    }
}
