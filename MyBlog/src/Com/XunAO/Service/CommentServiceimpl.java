package Com.XunAO.Service;

import Com.XunAO.Dao.CommentDao;
import Com.XunAO.Projo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("CommentService")
public class CommentServiceimpl implements  CommentService {
    @Autowired
    private CommentDao commentDao;
    @Override
    public int add(Comment comment) {
        return commentDao.add(comment);
    }

    @Override
    public int update(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer delete(Integer id) {
        return commentDao.delete(id);
    }
}
