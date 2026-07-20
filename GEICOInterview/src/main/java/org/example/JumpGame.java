package org.example;

public class JumpGame {
    public int jump(int[] nums) {
        // If there is only one position, no jump is needed.
        if (nums.length <= 1) {
            return 0;
        }

        int jumps = 0;

        // currentJumpEnd is the farthest index reachable using the current number of jumps.
        int currentJumpEnd = 0;

        // farthestReachable is the farthest index reachable from positions seen so far.
        int farthestReachable = 0;

        // We stop at nums.length - 2 because once we are at or beyond the last index,
        // no more jumps are needed.
        for (int i = 0; i < nums.length - 1; i++) {
            // Update how far we could reach from this index.
            farthestReachable = Math.max(farthestReachable, i + nums[i]);

            // If we reached the end of the current jump range,
            // we must take another jump.
            if (i == currentJumpEnd) {
                jumps++;
                currentJumpEnd = farthestReachable;

                // If the new jump range reaches the last index, we are done.
                if (currentJumpEnd >= nums.length - 1) {
                    break;
                }
            }
        }

        return jumps;
    }
}