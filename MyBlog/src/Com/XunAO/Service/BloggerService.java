package Com.XunAO.Service;

import Com.XunAO.Projo.Blogger;

public interface BloggerService {
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
}
