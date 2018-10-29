package com.edison.testJunit.oth.sort;

public class QuickSort {
	public static void sort(int arr[], int low, int high) {
		int l = low;
		int h = high;
		int povit = arr[low];

		while (l < h) {
			while (l < h && arr[h] >= povit)
				h--;
			if (l < h) {
				int temp = arr[h];
				arr[h] = arr[l];
				arr[l] = temp;
				l++;
			}

			while (l < h && arr[l] <= povit)
				l++;

			if (l < h) {
				int temp = arr[h];
				arr[h] = arr[l];
				arr[l] = temp;
				h--;
			}
		}
		for(int a:arr){
			System.out.print(a+" ");
		}
		System.out.println();
		System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit+ "\n");
		if (l > low)
			sort(arr, low, l - 1);
		if (h < high)
			sort(arr, l + 1, high);
	}
	public static void sort2(int arr[], int low, int high) {
		int l = low;
		int h = high;
		int povit = arr[low];

		while (l < h) {
			if(arr[h]<povit){
				
			}
		}
		for(int a:arr){
			System.out.print(a+" ");
		}
		System.out.println();
		System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit+ "\n");
		if (l > low)
			sort(arr, low, l - 1);
		if (h < high)
			sort(arr, l + 1, high);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={3,2,1,4,0,6,5};
		sort(arr,0,arr.length-1);
		for(int a:arr){
			System.out.print(a+" ");
		}
	}

}
