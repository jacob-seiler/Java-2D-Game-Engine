package me.jacob.zombiegame.engine.util;

public class Sorter {
	
	public static void quicksort(Sortable[] items) {
		if (items.length == 0)
			return;
		
		// Quick sort
		boolean sorted = false;
		int lowerIndex = 0;
		int higherIndex = items.length - 1;
		
		while (!sorted) {
			int i = lowerIndex;
			int j = higherIndex;
			
			double pivot = items[lowerIndex + (higherIndex - lowerIndex)/2].getDepth();
			
			while (i <= j) {
				while (items[i].getDepth() < pivot)
					i++;
				
				while (items[j].getDepth() < pivot)
					j--;
				
				if (i <= j) {
					// Exchange
					Sortable temp = items[i];
			        items[i] = items[j];
			        items[j] = temp;
			        
			        i++;
			        j--;
				}
			}
			
			// Repeat
			if (lowerIndex < j)
				higherIndex = j;
			else if (i < higherIndex)
				lowerIndex = i;
			else
				sorted = true;
		}
	}
	
}
