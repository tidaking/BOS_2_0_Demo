package com.robin.bos.web.action.system;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.robin.bos.domain.system.User;
import com.robin.bos.web.action.BaseAction;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午7:57:55 <br/>       
 */
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class UserAction extends BaseAction<User> {

    private static final long serialVersionUID = 6909986179340042170L;
    
    private String vcode;
    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
    
    
    @Action(value="userAction_login",
    results={@Result(name=SUCCESS,type="redirect",location="/index.html"),
             @Result(name=LOGIN,type="redirect",location="/login.html")})
    public String login()
    {
        HttpSession session = ServletActionContext.getRequest().getSession();
        String serverCode = (String) session.getAttribute("key");
        System.out.println("serverCode:"+serverCode);
        System.out.println("vCode:"+vcode);
        if(StringUtils.isEmpty(serverCode) || StringUtils.isEmpty(vcode) || !serverCode.equals(vcode))
        {
            System.out.println("验证码有误!");
            return LOGIN;
        }
        
        
        // 1.创建subject
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());
        
        try {
            // 2.subject.login(token)-->token会传给自己定义的ramle中,由securityManager去对比
            // 3.捕捉异常
            subject.login(token);
            
            User user = (User) subject.getPrincipal();
            
            
            
            session.setAttribute("user", user);
        } catch (UnknownAccountException  e) {
            System.out.println("账号不存在");
            return LOGIN;
        }catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
            return LOGIN;
        }catch (Exception e) {
            System.out.println("其他错误");
            return LOGIN;
        }
        return SUCCESS;
    }
    

}
  
