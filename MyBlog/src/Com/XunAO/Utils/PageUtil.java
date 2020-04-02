package Com.XunAO.Utils;

public class PageUtil {
    /**
     * ���ɷ�ҳ����
     * @param targetUrl Ŀ���ַ
     * @param totalNum �ܼ�¼��
     * @param currentPage ��ǰҳ
     * @param pageSize ÿҳ��С
     * @return
     */
    public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param)
    {
        long totalpage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        if (totalpage==0)
        {
            return  "û������";
        }
        else
        {
            StringBuffer pageCode=new StringBuffer();
            pageCode.append("<li><a href='"+targetUrl+"?page=1&"+param+"'>��ҳ</a></li>");
          if (currentPage>1)
          {
              pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>��һҳ</a></li>");
          }
          else
          {
              pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>��һҳ</a></li>");

          }
            for(int i=currentPage-2;i<=currentPage+2;i++){
                if(i<1||i>totalpage){
                    continue;
                }
                if(i==currentPage){
                    pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></li>");
                }else{
                    pageCode.append("<li><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></li>");
                }
            }
            if(currentPage<totalpage){
                pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>��һҳ</a></li>");
            }else{
                pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>��һҳ</a></li>");
            }
            pageCode.append("<li><a href='"+targetUrl+"?page="+totalpage+"&"+param+"'>βҳ</a></li>");
            return pageCode.toString();
        }
    }
}
