package com.edison.testJunit.oth.SortAndDataStructure;

/**二分查找*/
public class Search_BinarySearch {
	public static int binarySearch(int[] arr,int beg,int end,int key) {
		int mid=beg+((end-beg)>>1);//0-9:4
		if(beg==end) {
			if(arr[beg]==key) 
				return beg;
			else
				return -1;
		}
		
		if(arr[mid]<key)
			return binarySearch(arr, mid+1, end, key);
		else if(arr[mid]>key)
			return binarySearch(arr, beg, mid, key);
		else
			return mid;
	}
	/**二分查找-有重复项找最小下标*/
	public static int binarySearchDup(int[] arr,int beg,int end,int key) {
		int mid=beg+((end-beg)>>1);//0-9:4
//		System.out.println(beg+"-"+end+"-"+mid);
		if(beg==end) {
			if(arr[beg]==key) {
				int i=beg;
				for(;i>=0&&arr[i]==key;i--) {}
				return i+1;
			}else
				return -1;
		}
		
		if(arr[mid]<key)
			return binarySearchDup(arr, mid+1, end, key);
		else if(arr[mid]>key)
			return binarySearchDup(arr, beg, mid, key);
		else
			return binarySearchDup(arr,mid,mid,key);
	}
	
	public static void main(String[] args) {
		int[] arr= {0,1,2,3,4,5,6,7,8,9,10};
		int[] arrdup= {0,1,2,3,4,4,4,5,6,8,8,8,9,10};
		for(int i=0;i<20;i++) {
			System.out.println("i="+i+"-"+binarySearch(arr, 0, arr.length-1,i));
		}
		for(int i=0;i<20;i++) {
			System.out.println("i="+i+"-"+binarySearchDup(arrdup, 0, arrdup.length-1,i));
		}
	}

}
