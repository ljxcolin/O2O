package stu.ljx.o2o.exception;

/**
 * 商品操作异常处理类
 * 继承自RuntimeException，这样在标注了@Transactional事务的方法中，出现了异常，才能回滚数据。
 * 默认情况下，如果在事务中抛出了未检查异常（继承自 RuntimeException的异常）或者 Error，
 * 那么Spring将回滚事务；除此之外，Spring不会回滚事务。
 * @author Lijinxuan
 */
public class ProductException extends RuntimeException {

	private static final long serialVersionUID = 3229290201619812533L;

	public ProductException(String message) {
		super(message);
	}
	
}
