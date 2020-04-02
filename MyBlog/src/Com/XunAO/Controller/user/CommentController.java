package Com.XunAO.Controller.user;

import Com.XunAO.Projo.Blog;
import Com.XunAO.Projo.Comment;
import Com.XunAO.Service.BlogService;
import Com.XunAO.Service.CommentService;
import Com.XunAO.Utils.ResponseUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    public String save(Comment comment, @RequestParam("imageCode") String imageCode, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, javax.servlet.http.HttpSession session) throws Exception {
        String srand =(String) session.getAttribute("srand");
        JSONObject jsonObject=new JSONObject();
        int resulttotal=0;
        if (!imageCode.equals(srand))
        {
            jsonObject.put("success",false);
            jsonObject.put("erroinfo","ÑéÖ¤ÂëÌîÐ´´íÎó");
        }else
        {
            String remoteAddr = request.getRemoteAddr();
            comment.setUserIp(remoteAddr);
            if (comment.getId()==null)
            {
                resulttotal = commentService.add(comment);
                Blog findby = blogService.findby(comment.getBlog().getId());
                findby.setReplyHit(findby.getClickHit()+1);
                blogService.updatebyblog(findby);

            }else
            {

            }
            if (resulttotal>0)
            {
                jsonObject.put("success", true);

            }
            else
            {
                jsonObject.put("success", false);

            }

        }
        ResponseUtil.write(response,request);
        return  null;
    }


}
