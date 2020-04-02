package Com.XunAO.Controller.user;

import Com.XunAO.Projo.Blog;
import Com.XunAO.Service.BlogService;
import Com.XunAO.Service.CommentService;
import Com.XunAO.Utils.StringUtil;
import Com.XunAO.lucene.Myindex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/blog")
public class BlogControl {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    private Myindex myindex=new Myindex();
    /**
     * 请求博客详细信息
     * @return
     * @throws Exception
     */
    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request)throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        Blog blog = blogService.findby(id);
        String keyWord = blog.getKeyWord();
        if (StringUtil.isnotempty(keyWord))
        {
            String[] s = keyWord.split(" ");
            modelAndView.addObject("keyWord",StringUtil.filterWhite(Arrays.asList(s)));
        }else
        {
            modelAndView.addObject("keyWord",null);
        }
        modelAndView.addObject("blog",blog);
       blog.setClickHit(blog.getClickHit()+1);
       blogService.updatebyblog(blog);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("blogId",blog.getId());
        map.put("state",1);//查询审核通过的评论
        modelAndView.addObject("commentList", commentService.list(map));
        modelAndView.addObject("mainPage", "foreground/blog/view.jsp");
        modelAndView.addObject("pageTitle",blog.getTitle()+"_Java开源博客系统");
        modelAndView.setViewName("mainTemp");
        return  modelAndView;
    }
    /**
     * 根据关键字查询相关博客信息
     * @param q
     * @return
     * @throws Exception
     */
    @RequestMapping("/q")
    public  ModelAndView search(@RequestParam(value = "q",required = false)String q, @RequestParam(value = "page",required = false)String page, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView=new ModelAndView();
        if (StringUtil.isempty(page))
        {
            page="1";
        }
        modelAndView.addObject("mainPage", "foreground/blog/result.jsp");
        List<Blog> list = myindex.searchBlog(q.trim());
        Integer toIndex=list.size()>=Integer.parseInt(page)*10?Integer.parseInt(page)*10:list.size();
        modelAndView.addObject("blogList",list.subList((Integer.parseInt(page)-1)*10, toIndex));
        modelAndView.addObject("q",q);
        modelAndView.addObject("pageTitle","搜索关键字'"+q+"'结果页面_Java开源博客系统");
        modelAndView.setViewName("mainTemp");
        return modelAndView;
    }
    /**
     * 获取下一篇博客和下一篇博客代码
     * @param lastBlog
     * @param nextBlog
     * @return
     */
    private String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext)
    {
        StringBuffer pagecode=new StringBuffer();
        if (lastBlog==null||lastBlog.getId()==null)
        {
            pagecode.append("<p>上一篇：没有了</p>");
        }
        else
        {
            pagecode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");

        }
        if (nextBlog==null||nextBlog.getId()==null)
        {
            pagecode.append("<p>下一篇：没有了</p>");
        }
        else
        {
            pagecode.append("<p><a href='"+projectContext+"+/blog/article/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"+</a>");
        }
        return pagecode.toString();

    }
    /**
     * 获取上一页，下一页代码 查询博客用到
     * @param page 当前页
     * @param totalNum 总记录数
     * @param q 查询关键字
     * @param pageSize 每页大小
     * @param projectContext
     * @return
     */
    private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext)
    {
        int totalnum=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pagecode=new StringBuffer();
        if (totalNum==0)
        {
            return null;
        }
        else
        {
            pagecode.append("<nav>");
            if (page>1)
            {
                pagecode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");

            }
            else
            {
                   pagecode.append("<li class='disabled'><a href='#'>上一页</a></li>");
            }
            if (totalNum>page)
            {
                pagecode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");

            }
            else
            {
                pagecode.append("<li class='disabled'><a href='#'>下一页</a></li>");

            }
            pagecode.append("</ul>");
            pagecode.append("</nav>");

        }
        return  pagecode.toString();

    }
    }

