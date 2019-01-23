package com.edison.testJunit.oth.SortAndDataStructure;

import java.util.Arrays;
import java.util.Comparator;

/**二叉堆，可分为大堆和小堆，即最大值或者最小值在堆顶.<p>
 *它是实现堆排序和优先级队列PriorityBlockingQueue的基础.<p>
 * 它是由数组实现的,有几个前提：
 *0. 堆顶的下标就是0，它的子节点下标是1，2，而下标1对应节点的左右子节点是3，4，而下标2 对应节点的左右子节点是5，6<p>
 *1. 即下标i对应节点的左节点是(奇数(2*i+1); 右节点是偶数(2*i+2);<p>
 *2. 下标i对应的父节点：奇数：(i-1)/2 或者 偶数： (i-2)/2,在连续奇数偶数计算/求余时，实际上结果都是(i-1)/2;<p>
 *3. 最后一个节点的下标是size-1，它的父节点下标(size-1-1)/2=(size-2)/2,
 *       所以二叉堆的最后一个非叶子节点的下标(size-2)/2  -- size>=2;<br>
 *       换成随机i节点的话，其父节点下标是(i-2)/2-- i>=2*/
@SuppressWarnings("unchecked")  //代码中存在很多强制转换，加上这个
public class Structure_Heap<T> {
	private int size=0;     //当前数组中元素个数
	private Object[] queue;      //数组，存放堆数据
	Comparator<? super T> comparator;  //比较器
	
	/**只有设置容量和比较器的构造方法*/
	public Structure_Heap(int capacity,Comparator<? super T> comparator){
		this.queue=new Object[capacity];
		this.comparator=comparator;
	}
	
	/**存放数据：从数组数据末尾一层层往上浮*/
	public boolean put(T t) {
		if(size==queue.length || t==null) {
			return false;
		}
		compareAndUpShift(t);
		size++;
		return true;
	}
	
	/**取出数据: 弹出堆顶，把最后一个节点放到首尾并下沉*/
	public T take() {
		if(size==0) {
			return null;
		}
		T t=(T)queue[0];
		compareAndDownShift();
		size--;
		return t; 
	}
	
	/**比较并上浮：因为新加的节点是从最末尾的非叶子节点开始，一层层往上比较父节点并交换值，是上浮的*/
	public void compareAndUpShift(T t) {
		int k=size; //这其实就是要进行存放数据下标位置
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
		int k=0,c=(size-2)/2,length=queue.length;
		T tail=(T)queue[size-1]; //最末尾的节点
		queue[size-1]=null;
		while(k<=c) {//size=0,1时，实际上直接不执行
			int left=2*k+1;
			int right=2*k+2;
			T childleft=null;
			T childright=null;
			T min=tail;

			if(left<length){
				childleft=(T)queue[left];
				if(childleft!=null) {
					if(comparator.compare(min, childleft)>0) {
						min=childleft;
					}
					if(right<length) {
						childright=(T)queue[right];
						if(childright!=null) {//左右子节点都有值
							if(comparator.compare(min, childright)>0) {
								min=childright;
							}
						}
					}
				}
			}
		}
		
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
