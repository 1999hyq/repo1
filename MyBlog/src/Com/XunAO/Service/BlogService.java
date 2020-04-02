package Com.XunAO.Service;

import Com.XunAO.Projo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    /**
     * 根据日期月份分组查询
     * @return
     */
    public List<Blog> countlist();
    /**
     * 分页查询博客
     * @return
     */
    public  List<Blog> list(Map<String,Object> map);
    /**
     * 获取总记录数
     * @param map
     * @return
     */
    public  Long gettotal(Map<String,Object>map);

    /**
     * 通过Id查找实体
     * @param id
     * @return
     */
    public Blog findby(Integer id);
    /**
     * 更新博客信息
     * @param blog
     * @return
     */
    public Integer updatebyblog(Blog blog);
    /**
     * 获取上一个博客
     * @param id
     * @return
     */
    public Blog getLastBlog(Integer id);
    /**
     * 获取下一个博客
     * @param id
     * @return
     */
    public Blog getNextBlog(Integer id);
    /**
     * 添加博客信息
     * @param blog
     * @return
     */
    public  Integer add(Blog blog);
    public Integer delete(Integer id);

}
