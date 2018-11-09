package stu.ljx.o2o.mytest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class MyTest {

	@Test
	public void test0() {
		Set<String> set = new HashSet<String>();
		set.add("ljx");
		set.add("tsq");
		set.add("cxy");
		//String[] names = new String[set.size()];
		//String[] names = new String[2];
		String[] names = new String[4];
		set.toArray(names);
		//System.out.println(Arrays.toString(names)); //[tsq, cxy, ljx]
		//System.out.println(Arrays.toString(names)); //[null, null]
		System.out.println(Arrays.toString(names)); //[tsq, cxy, ljx, null]
		
		Integer[] temp = new Integer[4];
		Object[] result = set.toArray(temp); //java.lang.ArrayStoreException: java.lang.String
		System.out.println(Arrays.toString(temp));
		System.out.println(Arrays.toString(result));
	}
	
}
