package Com.XunAO.Controller.user;

import Com.XunAO.Projo.Blogger;
import Com.XunAO.Service.BloggerService;
import Com.XunAO.Utils.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/blogger")
public class BloggerController {
    @Autowired
    private BloggerService bloggerService;
    /**
     * 用户登录
     * @param blogger
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/login")
    public String login(Blogger blogger, HttpServletRequest httpServletRequest )
    {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken newHxsoft = new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "newHxsoft"));
         try {
             subject.login(newHxsoft);
             return "redirect:/admin/main.jsp";
         }catch (Exception e)
         {
             e.printStackTrace();
             httpServletRequest.setAttribute("blogger", blogger);
             httpServletRequest.setAttribute("errorInfo", "用户名或密码错误！");
             return "login";
         }
        /**
         * 查找博主信息
         * @return
         * @throws Exception
         */



    }
   @ RequestMapping("/infome")
    public ModelAndView  aboutMe(@RequestParam(value = "id",required = true) Integer id)throws Exception
    {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("blogger",bloggerService.find(id));
        modelAndView.addObject("mainPage", "foreground/blogger/info.jsp");
         modelAndView.addObject("pagetitle","Newhxsoft_博客");
         modelAndView.setViewName("mainTemp");
         return modelAndView;
    }

}
