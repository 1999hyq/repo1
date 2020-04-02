package Com.XunAO.Utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    /**
     * �ж��Ƿ��ǿ�
     * @param str
     * @return
     */
    public  static  boolean isempty(String str)
    {
        if (str.equals("")||"".equals(str.trim()))
        {
            return  true;
        }
        return  false;

    }

    /**
     * �ж��Ƿ��ǿ�
     * @param str
     * @return
     */
    public  static  boolean isnotempty(String str)
    {

        if (str!=null||!"".equals(str.trim()))
        {
            return  true;
        }
        return  false;

    }

    /**
     * ��ʽ��ģ����ѯ
     * @param str
     * @return
     */
    public static String formatLike(String str){
        if(isnotempty(str)){
            return "%"+str+"%";
        }else{
            return null;
        }
    }
    /**
     * ���˵�������Ŀո�
     * @param list
     * @return
     */
    public static List<String> filterWhite(List<String> list) {
        List<String> list1=new ArrayList<>();
        for(String l:list)
        {
            if(isnotempty(l))
            {
                list.add(l);
            }
        }
        return  list1;
    }

}
