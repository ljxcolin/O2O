package stu.ljx.o2o.exception;

public class TestThrow {

	public static void main(String[] args) {
		ThrowDemo td = new ThrowDemo();
		td.testThrow01();
		try {
			td.testThrow02();
		} catch (MyException mye) {
			System.out.println(mye.getMessage());
		}
		
		/*td.testThrow02();
		try {
			td.testThrow01();
		} catch (MyException mye) {
			System.out.println(mye.getMessage());
		}*/
		
		/*try {
			td.testThrow01();
			td.testThrow02();
			//td.testThrow02();
			//td.testThrow01();
		} catch (MyException mye) {
			System.out.println(mye.getMessage());
		}*/
		
		/*try {
			td.testThrow01();
		} catch (MyException mye) {
			System.out.println(mye.getMessage());
		}
		try {
			td.testThrow02();
		} catch (MyException mye) {
			System.out.println(mye.getMessage());
		}*/
	}

}
