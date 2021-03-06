package Com.XunAO.Controller.admin;

import Com.XunAO.Projo.Blogger;
import Com.XunAO.Service.BloggerService;
import Com.XunAO.Utils.CryptographyUtil;
import Com.XunAO.Utils.DateUtil;
import Com.XunAO.Utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

public class BloggerAdminController {
    @Resource
    private BloggerService bloggerService;

    /**
     * 修改博主信息
     * @param
     * @param blogger
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/save")
    public String save(@RequestParam("imageFile") MultipartFile imageFile, Blogger blogger, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)throws Exception{
        if(!imageFile.isEmpty()){
            String filePath=request.getServletContext().getRealPath("/");
            String imageName= DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal=bloggerService.update(blogger);
        StringBuffer result=new StringBuffer();
        if(resultTotal>0){
            result.append("<script language='javascript'>alert('修改成功！');</script>");
        }else{
            result.append("<script language='javascript'>alert('修改失败！');</script>");
        }
        ResponseUtil.write(response, result);
        return null;
    }

    /**
     * 查询博主信息
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/find")
    public String find(javax.servlet.http.HttpServletResponse response,Integer id)throws Exception{
        Blogger blogger=bloggerService.find(id);
        JSONObject jsonObject=new JSONObject();

        ResponseUtil.write(response, jsonObject);
        return null;
    }

    /**
     * 修改博主密码
     * @param newPassword
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyPassword")
    public String modifyPassword(String newPassword, javax.servlet.http.HttpServletResponse response)throws Exception{
        Blogger blogger=new Blogger();
        blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
        int resultTotal=bloggerService.update(blogger);
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
     * 注销
     * @return
     * @throws Exception
     */
    @RequestMapping("/logout")
    public String  logout()throws Exception{
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}
