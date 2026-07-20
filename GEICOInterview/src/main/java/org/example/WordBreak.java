package org.example;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

    public class WordBreakSolution {
        public boolean wordBreak(String s, List<String> wordDict) {
            Set<String> dictionary = new HashSet<>(wordDict);

            // canBreak[i] means s[0..i-1] can be segmented into dictionary words.
            boolean[] canBreak = new boolean[s.length() + 1];

            // Empty prefix can always be segmented.
            canBreak[0] = true;

            for (int end = 1; end <= s.length(); end++) {
                for (int start = 0; start < end; start++) {
                    // If the prefix up to start is breakable,
                    // and s[start..end-1] is a dictionary word,
                    // then the prefix up to end is breakable.
                    if (canBreak[start] && dictionary.contains(s.substring(start, end))) {
                        canBreak[end] = true;
                        break;
                    }
                }
            }

            return canBreak[s.length()];
        }
    }
}