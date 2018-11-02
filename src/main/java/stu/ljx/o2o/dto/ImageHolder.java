package stu.ljx.o2o.dto;

import java.io.InputStream;

/**
 * 图片持有类
 * @author Lijinxuan
 *
 */
public class ImageHolder {
	
	private InputStream ins;
	private String fileName;
	
	//无参构造
	public ImageHolder() {}
	
	//带参构造，新增时使用
	public ImageHolder(InputStream ins, String fileName) {
		this.ins = ins;
		this.fileName = fileName;
	}

	public InputStream getIns() {
		return ins;
	}

	public void setIns(InputStream ins) {
		this.ins = ins;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageHolder other = (ImageHolder) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		return true;
	}

}
