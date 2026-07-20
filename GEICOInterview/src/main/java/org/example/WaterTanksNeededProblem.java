package org.example;

public class WaterTanksNeededProblem {
        public int minimumTanks(String street) {
            char[] cells = street.toCharArray();
            int tanks = 0;

            for (int i = 0; i < cells.length; i++) {
                // Empty plots do not need service.
                if (cells[i] != 'H') {
                    continue;
                }

                // If there is already a tank immediately to the left,
                // this house is already covered.
                if (i > 0 && cells[i - 1] == 'T') {
                    continue;
                }

                // Prefer placing a tank on the right if possible.
                // This may cover both the current house and a future house.
                if (i + 1 < cells.length && cells[i + 1] == '-') {
                    cells[i + 1] = 'T';
                    tanks++;
                    continue;
                }

                // If the right side is not available, try placing a tank on the left.
                // This is less ideal because it cannot help future houses.
                if (i > 0 && cells[i - 1] == '-') {
                    cells[i - 1] = 'T';
                    tanks++;
                    continue;
                }

                // No adjacent empty plot is available, so this house cannot be served.
                return -1;
            }

            return tanks;
        }
    }
}
