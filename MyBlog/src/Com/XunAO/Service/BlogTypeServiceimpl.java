package Com.XunAO.Service;

import Com.XunAO.Dao.BlogTypeDao;
import Com.XunAO.Projo.BlogType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("BlogTypeService")
public class BlogTypeServiceimpl implements  BlogTypeService {
    @Autowired
    private BlogTypeDao blogTypeDao;
    @Override
    public List<BlogType> countList() {
        return blogTypeDao.countList();
    }

    @Override
    public BlogType findById(Integer id) {
        return blogTypeDao.findById(id);
    }

    @Override
    public List<BlogType> list(Map<String, Object> map) {
        return blogTypeDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogTypeDao.getTotal(map);
    }

    @Override
    public Integer add(BlogType blogType) {
        return blogTypeDao.add(blogType);
    }

    @Override
    public Integer update(BlogType blogType) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }
}
