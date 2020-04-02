package Com.XunAO.Service;

import Com.XunAO.Dao.BlogDao;
import Com.XunAO.Projo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("BlogService")
public class BlogServiceimpl implements BlogService {
    @Autowired
    private BlogDao blogDao;
    @Override
    public List<Blog> countlist() {
        return blogDao.countlist();
    }

    @Override
    public List<Blog> list(Map<String, Object> map) {
        return  blogDao.list(map);
    }

    @Override
    public Long gettotal(Map<String, Object> map) {
        return  blogDao.gettotal(map);
    }

    @Override
    public Blog findby(Integer id) {
        return  blogDao.findby(id);
    }

    @Override
    public void updatebyblog(Blog blog) {
         blogDao.updatebyblog(blog);
    }

    @Override
    public Blog getLastBlog(Integer id) {
        return blogDao.getLastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogDao.getNextBlog(id);
    }

    @Override
    public Integer add(Blog blog) {
        return blogDao.add(blog);
    }
}
