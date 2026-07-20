package com.hackerrank;

import java.util.*;

public class DailyServerWork {
    public static int minDaysToIdleServers(int[] taskLoad, int[] dailyProcessing, int k) {
        int n = taskLoad.length;
        int[] days = new int[n];

        for (int i = 0; i < n; i++) {
            if (taskLoad[i] <= 0) {
                days[i] = 0;
            } else {
                days[i] = (taskLoad[i] + dailyProcessing[i] - 1) / dailyProcessing[i];
            }
        }

        Arrays.sort(days);
        return days[k - 1];
    }

    public static void main(String[] args) {
        int[] taskLoad = {4, 6};
        int[] dailyProcessing = {1, 5};
        int k = 1;

        System.out.println(minDaysToIdleServers(taskLoad, dailyProcessing, k)); // 2
    }
}