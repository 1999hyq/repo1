package Com.XunAO.Dao;

import Com.XunAO.Projo.Blogger;

public interface BloggerDao {
    /**
     * 查询博主信息
     * @return
     */
    public Blogger find(Integer id);
    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    public Blogger getByUserName(String userName);
    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    public Integer update(Blogger blogger);
    public Integer delete(Integer id);


}
