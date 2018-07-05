package com.iscas.component.core.intfs;

import java.util.List;

import com.iscas.component.core.PagerModel;

/**
 * @author adams socket公共接口
 */
public interface ComponentIntface <E extends PagerModel>{
	/**
	 * 更新数据
	 * @return
	 */
	public int updateData(E e);

	/**
	 * 删除数据
	 * 
	 * @return
	 */
	public int deleteData(E e);

	/**
	 * 添加数据
	 * 
	 * @return
	 */
	public int insertData(E e);

	/**
	 * 查询数据
	 * @return
	 */
	public List<E> selectDateAsList(E e);

}
