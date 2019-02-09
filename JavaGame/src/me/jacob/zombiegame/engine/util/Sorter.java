package me.jacob.zombiegame.engine.util;

import java.util.List;

public class Sorter {

    public static void sort(List items) {
        if (items.size() <= 1 || !(items.get(0) instanceof Sortable))
            return;

        // TODO determine the best sorting algorithm to use
        quickSort(items, 0, items.size() - 1);
    }

    private static void quickSort(List items, int begin, int end) {
        if (begin >= end)
            return;

        // Partition
        double pivot = ((Sortable) items.get(end)).getDepth();
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (((Sortable) items.get(j)).getDepth() <= pivot) {
                i++;

                // Swap
                Object swapTerm = items.get(i);
                items.set(i, items.get(j));
                items.set(j, swapTerm);
            }
        }

        // Swap
        Object swapTerm = items.get(i + 1);
        items.set(i + 1, items.get(end));
        items.set(end, swapTerm);

        int partitionIndex = i + 1;

        quickSort(items, begin, partitionIndex-1);
        quickSort(items, partitionIndex+1, end);

    }
}
