package com.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public List<List<Integer>> permute(final int[] nums) {
        final List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, 0, res);
        return res;
    }

    private void backtrack(final int[] nums, final int start, final List<List<Integer>> res) {
        if (start == nums.length) {
            final List<Integer> perm = new ArrayList<>();
            for (int n : nums) perm.add(n);
            res.add(perm);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            backtrack(nums, start + 1, res);
            swap(nums, start, i); // backtrack
        }
    }

    private void swap(final int[] nums, final int i, final int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
