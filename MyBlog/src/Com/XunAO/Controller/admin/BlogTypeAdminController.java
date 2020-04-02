package Com.XunAO.Controller.admin;

import Com.XunAO.Projo.BlogType;
import Com.XunAO.Projo.PageBean;
import Com.XunAO.Service.BlogTypeService;
import Com.XunAO.Utils.ResponseUtil;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlogTypeAdminController {

    @Resource
    private BlogTypeService blogTypeService;

    /**
     * 分页查询博客类别信息
     * @param page
     * @param rows
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=false)String page, @RequestParam(value="rows",required=false)String rows, javax.servlet.http.HttpServletResponse response)throws Exception{
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<BlogType> blogTypeList=blogTypeService.list(map);
        Long total=blogTypeService.getTotal(map);
        JSONObject result=new JSONObject();


        result.put("rows", blogTypeList);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加或者修改博客类别信息
     * @param blogType
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(BlogType blogType, javax.servlet.http.HttpServletResponse response)throws Exception{
        int resultTotal=0; // 操作的记录条数
        if(blogType.getId()==null){
            resultTotal=blogTypeService.add(blogType);
        }else{
            resultTotal=blogTypeService.update(blogType);
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
     * 删除博客类别信息
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids")String ids, javax.servlet.http.HttpServletResponse response)throws Exception{
        String []idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            blogTypeService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
    
}
