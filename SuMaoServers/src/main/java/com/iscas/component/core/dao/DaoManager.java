package com.iscas.component.core.dao;

import java.util.List;

import com.iscas.component.core.PagerModel;




/**
 * 查询Dao核心接口，泛型的形式封装分页查询
 * @author adams
 *
 * @param <E>
 */
public interface DaoManager<E extends PagerModel> {
	/**
	 * 添加
	 * 
	 * @param e
	 * @return
	 */
	public int insert(E e);

	/**
	 * 删除
	 * 
	 * @param e
	 * @return
	 */
	public int delete(E e);

	/**
	 * 修改
	 * 
	 * @param e
	 * @return
	 */
	public int update(E e);

	/**
	 * 查询一条记录
	 * 
	 * @param e
	 * @return
	 */
	public E selectOne(E e);

	/**
	 * 分页查询
	 * 
	 * @param e
	 * @return
	 */
	public PagerModel selectPageList(E e);
	
	/**
	 * 根据条件查询所有
	 * @return
	 */
	public List<E> selectList(E e);

	/**
	 * 根据ID来删除一条记录
	 * @param id
	 */
	public int deleteById(int id);

	/**
	 * 根据ID查询一条记录
	 * @param id
	 * @return
	 */
	public E selectById(String id);
	
}
