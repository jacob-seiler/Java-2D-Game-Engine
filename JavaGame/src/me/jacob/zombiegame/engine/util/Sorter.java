package me.jacob.zombiegame.engine.util;

import java.util.List;

public class Sorter {

    public static void sort(List items, SortingMethod method) {
        if (items.size() <= 1 || !(items.get(0) instanceof Sortable))
            return;

        if (method == SortingMethod.QUICK)
            quickSort(items, 0, items.size() - 1);
        else if (method == SortingMethod.INSERTION)
            insertionSort(items);
        else if (method == SortingMethod.HEAP)
            return;
    }

    public static void sort(List items) {
        // TODO determine the best sorting algorithm to use
        /*
        When to use each sorting algorithm:
        - Insertion Sort: Only a few items
        - Insertion Sort: Items are mostly sorted already
        - Heap Sort: Concerned about worst-case scenarios
        - Quicksort: Interested in a good average-case result
         */

        // TODO 1000 correct size??
        if (items.size() <= 1000)
            sort(items, SortingMethod.INSERTION);
        else
            sort(items, SortingMethod.QUICK);
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

        quickSort(items, begin, partitionIndex - 1);
        quickSort(items, partitionIndex + 1, end);
    }

    private static void insertionSort(List items) {
        for (int i = 1; i < items.size(); i++) {
            Object key = items.get(i);
            int j = i - 1;

            while (j >= 0 && ((Sortable) items.get(j)).getDepth() > ((Sortable) key).getDepth()) {
                items.set(j + 1, items.get(j));
                j = j - 1;
            }

            items.set(j + 1, key);
        }
    }
}

enum SortingMethod {
    QUICK, INSERTION, HEAP
}
