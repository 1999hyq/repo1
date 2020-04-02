package Com.XunAO.Controller.admin;

import Com.XunAO.Projo.Link;
import Com.XunAO.Projo.PageBean;
import Com.XunAO.Service.LinkService;
import Com.XunAO.Utils.ResponseUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkAdminController {

    @Resource
    private LinkService linkService;

    /**
     * 分页查询友情链接信息
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
        List<Link> linkList=linkService.list(map);
        Long total=linkService.getTotal(map);
        JSONObject result=new JSONObject();

        result.put("rows",linkList);
        result.put("total", total);
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 添加或者修改友情链接信息
     * @param link
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(Link link, javax.servlet.http.HttpServletResponse response)throws Exception{
        int resultTotal=0; // 操作的记录条数
        if(link.getId()==null){
            resultTotal=linkService.add(link);
        }else{
            resultTotal=linkService.update(link);
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
     * 删除友情链接信息
     * @param ids
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam(value="ids")String ids, javax.servlet.http.HttpServletResponse response)throws Exception{
        String []idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            linkService.delete(Integer.parseInt(idsStr[i]));
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
        return null;
    }
}
