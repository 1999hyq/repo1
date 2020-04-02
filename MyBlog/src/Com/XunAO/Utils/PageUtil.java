package Com.XunAO.Utils;

public class PageUtil {
    /**
     * 生成分页代码
     * @param targetUrl 目标地址
     * @param totalNum 总记录数
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @return
     */
    public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param)
    {
        long totalpage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        if (totalpage==0)
        {
            return  "没有数据";
        }
        else
        {
            StringBuffer pageCode=new StringBuffer();
            pageCode.append("<li><a href='"+targetUrl+"?page=1&"+param+"'>首页</a></li>");
          if (currentPage>1)
          {
              pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a></li>");
          }
          else
          {
              pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a></li>");

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
                pageCode.append("<li><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a></li>");
            }else{
                pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a></li>");
            }
            pageCode.append("<li><a href='"+targetUrl+"?page="+totalpage+"&"+param+"'>尾页</a></li>");
            return pageCode.toString();
        }
    }
}
