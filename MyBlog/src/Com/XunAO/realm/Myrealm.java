package Com.XunAO.realm;

import Com.XunAO.Projo.Blogger;
import Com.XunAO.Service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class Myrealm extends AuthorizingRealm {
    @Resource
    private BloggerService bloggerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username =(String) authenticationToken.getPrincipal();
        Blogger byUserName = bloggerService.getByUserName(username);
        if (byUserName!=null)
        {
            SecurityUtils.getSubject().getSession().setAttribute("currentUser",username);
            AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(byUserName.getUserName(),byUserName.getPassword(),"xx");
            return  authcInfo;

        }else
        {
   return null;

        }    }
}
