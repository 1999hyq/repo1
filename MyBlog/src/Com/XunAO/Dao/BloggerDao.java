package Com.XunAO.Dao;

import Com.XunAO.Projo.Blogger;

public interface BloggerDao {
    /**
     * ��ѯ������Ϣ
     * @return
     */
    public Blogger find(Integer id);
    /**
     * ͨ���û�����ѯ�û�
     * @param userName
     * @return
     */
    public Blogger getByUserName(String userName);
    /**
     * ���²�����Ϣ
     * @param blogger
     * @return
     */
    public Integer update(Blogger blogger);
    public Integer delete(Integer id);


}
