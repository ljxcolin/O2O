package stu.ljx.o2o.util;

/**
 * 分页工具类
 * @author Lijinxuan
 *
 */
public class PageUtil {
	
	public static final int PAGESIZE = 8;
	
	/**
	 * 通过pageIndex和pageSize计算获得rowIndex
	 * @param pageIndex 前端传入的页数（第几页，从1开始）
	 * @param pageSize
	 * @return
	 */
	public static int calculate(int pageIndex, int pageSize) {
		return pageIndex > 1 ? (pageIndex - 1) * pageSize : 0;
	}
	
}
