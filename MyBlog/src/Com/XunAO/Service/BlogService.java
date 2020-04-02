package Com.XunAO.Service;

import Com.XunAO.Projo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    /**
     * ���������·ݷ����ѯ
     * @return
     */
    public List<Blog> countlist();
    /**
     * ��ҳ��ѯ����
     * @return
     */
    public  List<Blog> list(Map<String,Object> map);
    /**
     * ��ȡ�ܼ�¼��
     * @param map
     * @return
     */
    public  Long gettotal(Map<String,Object>map);

    /**
     * ͨ��Id����ʵ��
     * @param id
     * @return
     */
    public Blog findby(Integer id);
    /**
     * ���²�����Ϣ
     * @param blog
     * @return
     */
    public Integer updatebyblog(Blog blog);
    /**
     * ��ȡ��һ������
     * @param id
     * @return
     */
    public Blog getLastBlog(Integer id);
    /**
     * ��ȡ��һ������
     * @param id
     * @return
     */
    public Blog getNextBlog(Integer id);
    /**
     * ��Ӳ�����Ϣ
     * @param blog
     * @return
     */
    public  Integer add(Blog blog);
    public Integer delete(Integer id);

}
