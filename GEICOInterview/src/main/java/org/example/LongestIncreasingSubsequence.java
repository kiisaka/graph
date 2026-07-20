package org.example;

public class LongestIncreasingSubsequence {

    public class LongestIncreasingSubsequenceSolution {
        public int lengthOfLIS(int[] nums) {
            // tails[length - 1] stores the smallest possible tail value
            // for an increasing subsequence of length 'length'.
            int[] tails = new int[nums.length];

            // Number of valid entries in tails.
            int size = 0;

            for (int num : nums) {
                // Find the first position in tails where tails[pos] >= num.
                int left = 0;
                int right = size;

                while (left < right) {
                    int mid = left + (right - left) / 2;

                    if (tails[mid] < num) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }

                // Replace tails[left] with num.
                // This keeps tails as small as possible.
                tails[left] = num;

                // If num extends the largest subsequence found so far,
                // increase the size.
                if (left == size) {
                    size++;
                }
            }

            return size;
        }
    }
}