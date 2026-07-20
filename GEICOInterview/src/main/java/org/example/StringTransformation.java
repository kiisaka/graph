package org.example;

import java.util.ArrayDeque;
import java.util.Deque;

public class StringTransformation {

        public String transform(String s) {
            // Stack holds the partially reduced characters.
            Deque<Character> stack = new ArrayDeque<>();

            // Process characters from left to right.
            for (char c : s.toCharArray()) {
                char current = c;

                // If the current character matches the top of the stack,
                // combine the pair into the next character.
                //
                // Example:
                // 'a' + 'a' -> 'b'
                // 'b' + 'b' -> 'c'
                //
                // The new character may itself match the next stack top,
                // causing another carry.
                while (!stack.isEmpty() && stack.peek() == current) {
                    stack.pop();
                    current = nextChar(current);
                }

                // Push the resulting character after all possible carries.
                stack.push(current);
            }

            // The stack is reversed because we pushed onto the front.
            StringBuilder result = new StringBuilder();
            while (!stack.isEmpty()) {
                result.append(stack.removeLast());
            }

            return result.toString();
        }

        private char nextChar(char c) {
            // Assumes input and intermediate values do not go beyond 'z'.
            // In a real interview, clarify what should happen after 'z'.
            return (char) (c + 1);
        }
    }
}
