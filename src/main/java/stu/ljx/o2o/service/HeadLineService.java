package stu.ljx.o2o.service;


import stu.ljx.o2o.dto.HeadLineExecution;
import stu.ljx.o2o.entity.HeadLine;

public interface HeadLineService {
	
	public static final String HEADLINEKEY = "headline";
	
	HeadLineExecution getHeadLineList(HeadLine headLineCnd);
	
}
