package com.edison.testJunit.oth.SortAndDataStructure;
/**大文件里面取top n的问题：假设每一行就是一串数字或者字符串，求重复出现频率最高的项<p>
 * 分治：将大文件拆分成小文件-每一行读出来后获得hash值，将内容写到hash/n对应的小文件中，n为小文件个数<br>
 * hash值决定相应小文件能保证相同的项能写到同一个文件中<br>
 * 再分别统计各个小文件中相同项出现的次数，最后再汇总*/
public class Sort_TopN_BigFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		??未完成https://blog.csdn.net/qq_26437925/article/details/78531179
	}

}
