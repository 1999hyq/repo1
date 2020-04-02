package Com.XunAO.Service;

import Com.XunAO.Dao.BloggerDao;
import Com.XunAO.Projo.Blogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BloggerServic")
public class BloggerServiceimpl implements BloggerService {
    @Autowired
    private BloggerDao bloggerDao;
    @Override
    public Blogger find(Integer id) {
    return  bloggerDao.find(id);
    }

    @Override
    public Blogger getByUserName(String userName) {
        return  bloggerDao.getByUserName(userName);
    }

    @Override
    public Integer update(Blogger blogger) {
        return  bloggerDao.update(blogger);
    }
    public Integer delete(Integer id) {
        return bloggerDao.delete(id);
    }

}
