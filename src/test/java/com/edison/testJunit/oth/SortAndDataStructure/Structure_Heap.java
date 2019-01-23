package com.edison.testJunit.oth.SortAndDataStructure;

import java.util.Arrays;
import java.util.Comparator;

/**二叉堆，可分为大堆和小堆，即最大值或者最小值在堆顶.<p>
 *它是实现堆排序和优先级队列PriorityBlockingQueue的基础.<p>
 * 它是由数组实现的,有几个前提：
 *0. 堆顶的下标就是0，它的子节点下标是1，2，而下标1对应节点的左右子节点是3，4，而下标2 对应节点的左右子节点是5，6<p>
 *1. 即下标i对应节点的左节点是((i + 1) << 1) - 1--奇数; 右节点是(i + 1) << 1--偶数;<p>
 *2. 下标i对应的父节点： (i+1)/2-1=(i-1)/2 或者 i/2-1=(i-1)/2,所以是(i-1)/2;<p>
 *3. 最后一个节点的下标是size-1，它的父节点下标(size-1-1)/2=size/2 -1 ,
 *       所有二叉堆的最后一个非叶子节点的下标是size/2-1,不过在size=1时，这个表达式为-1，所以还是改为(size-1)/2;*/
public class Structure_Heap<T> {
	private int size=0;     //当前数组中元素个数
	private Object[] queue;      //数组，存放堆数据
	Comparator<? super T> comparator;  //比较器
	
	/**只有设置容量和比较器的构造方法*/
	public Structure_Heap(int capacity,Comparator<? super T> comparator){
		this.queue=new Object[capacity];
		this.comparator=comparator;
	}
	
	/**存放数据：一个个下沉*/
	public void put(T t) {
		compareAndDownShift(t);
		size++;
	}
	
	/**取出数据: 一个个上浮*/
	public T take() {
		T t=(T)queue[0];
		size--;
		return t; 
	}
	
	/**比较并上浮：因为新加的节点是从最末尾的非叶子节点开始，一层层往上比较并交换值，是上浮的*/
	public void compareAndUpShift(T t) {
		int k=size;
		while(k>0) {
			//最后一个非叶子节点下标
			int parentInd=(k-1)/2; //即 (size-1)>>1
			T parentValue=(T)queue[parentInd]; //父节点对象
		
			//看是否需要上浮
			if(comparator.compare(parentValue, t)<=0) {//待插入对象比父节点对象大
				break; //直接跳出
			}
			
			//待插入数据对象比父节点对象小
			//无需真正交换，单向赋值即可:把父节点的值复制到当前k位置
			queue[k]=parentValue; //先把父节点的值拷贝到当前位置k
			k=parentInd;
		}
		queue[k]=t; //最后才把待插入值放到k(size-1或者某一层的父节点)
	}
	
	/**比较并下沉：删除时，把最末尾的节点放到堆顶，并一级级往下比较并下沉*/
	public void compareAndDownShift() {
		
	}
	
	public void list() {
		for(Object i:queue) {
			System.out.print(i+" ");
		}
		System.out.println();
	}
	
	/**
	 * 上浮调整
	 * @param array     待调整的堆
	 */
	public static void upAdjust2(int[] array) {
	    int childIndex = array.length-1;
	    int parentIndex = (childIndex-1)/2;
	   
	    // temp保存插入的叶子节点值，用于最后的赋值
	    int temp = array[childIndex];
	    while (childIndex > 0 && temp < array[parentIndex])
	    {
	        //无需真正交换，单向赋值即可
	        array[childIndex] = array[parentIndex];
	        childIndex = parentIndex;
	        parentIndex = (parentIndex-1) / 2;
	    }
	    array[childIndex] = temp;
	}

	/**
	 * 下沉调整
	 * @param array     待调整的堆
	 * @param parentIndex    要下沉的父节点
	 * @param parentIndex    堆的有效大小
	 */
	public static void downAdjust2(int[] array, int parentIndex, int length) {
	    // temp保存父节点值，用于最后的赋值
	    int temp = array[parentIndex];
	    int childIndex = 2 * parentIndex + 1;
	    while (childIndex < length) {
	        // 如果有右孩子，且右孩子小于左孩子的值，则定位到右孩子
	        if (childIndex + 1 < length && array[childIndex + 1] < array[childIndex]) {
	            childIndex++;
	        }
	        // 如果父节点小于任何一个孩子的值，直接跳出
	        if (temp <= array[childIndex])
	            break;
	        //无需真正交换，单向赋值即可
	        array[parentIndex] = array[childIndex];
	        parentIndex = childIndex;
	        childIndex = 2 * childIndex + 1;
	    }
	    array[parentIndex] = temp;
	}


	/**
	 * 构建堆
	 * @param array     待调整的堆
	 */
	public static void buildHeap(int[] array) {
	    // 从最后一个非叶子节点开始，依次下沉调整
	    for (int i = array.length/2 -1; i >= 0; i--) {
	        downAdjust2(array, i, array.length - 1);
//	    	compareAndDownShift()
	    }
	}

	public static void main(String[] args) {
		Structure_Heap<Integer> heap=new Structure_Heap<Integer>(16, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}
		});
	    int[] array = new int[] {13,5,16,14,2,20,15};
	    for(int i:array) {
	    	heap.put(i);
	    	heap.list();
	    }
	   
	    
	    
	    upAdjust2(array);
	    System.out.println(Arrays.toString(array));
	    array = new int[] {7,1,3,10,5,2,8,9,6};
	    buildHeap(array);
	    System.out.println(Arrays.toString(array));
	}
}
