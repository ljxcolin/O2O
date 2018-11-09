package stu.ljx.o2o.exception;

public class ThrowDemo {
	
	public void testThrow01() {
		throw new MyException("Occur MyException 01");
	}
	
	public void testThrow02() throws MyException{
		throw new MyException("Occur MyException 02");
	}

}
