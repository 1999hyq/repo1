package Com.XunAO.Controller.admin;

import Com.XunAO.Projo.Blog;
import Com.XunAO.Projo.Blogger;
import Com.XunAO.Projo.PageBean;
import Com.XunAO.Service.BlogService;
import Com.XunAO.Utils.CryptographyUtil;
import Com.XunAO.Utils.ResponseUtil;
import Com.XunAO.Utils.StringUtil;
import Com.XunAO.lucene.Myindex;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

    @Resource
    private BlogService blogService;

    // 博客索引
    private Myindex blogIndex=new Myindex();

    /**
     * 添加或者修改博客信息
     * @param blog
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Blog blog, javax.servlet.http.HttpServletResponse response)throws Exception{
        int resultTotal=0; // 操作的记录条数
        if(blog.getId()==null){
            resultTotal=blogService.add(blog);
            blogIndex.addIndex(blog); // 添加博客索引
        }else{
            resultTotal=blogService.updatebyblog(blog);
            blogIndex.updateIndex(blog); // 更新博客索引
        }
        JSONObject result=new JSONObject();
        if(resultTotal>0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 分页查询博客信息
     * @param page
     * @param rows
     * @param s_customer
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page, @RequestParam(value="rows",required=false)String rows, Blog s_blog, javax.servlet.http.HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("title", StringUtil.formatLike(s_blog.getTitle()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Blog> blogList=blogService.list(map);
        Long total=blogService.gettotal(map);
        JSONObject result=new JSONObject();


        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 删除博客信息
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids")String ids, javax.servlet.http.HttpServletResponse response)throws Exception{
        String []idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            blogService.delete(Integer.parseInt(idsStr[i]));
            blogIndex.deleteIndex(idsStr[i]); // 删除对应博客的索引
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 通过ID查找实体
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/findById")
    public String findById(@RequestParam(value="id")String id, javax.servlet.http.HttpServletResponse response)throws Exception {
        Blog blog = blogService.findby(Integer.parseInt(id));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("adminblogger",blog);
        ResponseUtil.write(response, jsonObject);
        return null;
    }
}
