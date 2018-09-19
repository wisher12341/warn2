package com.warn.interceptor;

import com.warn.dao.UserDao;
import com.warn.entity.Role;
import com.warn.entity.User;
import com.warn.service.UserService;
import com.warn.util.UserCookieUtil;
import com.warn.util.common.Const;
import com.warn.util.common.MD5;
import org.apache.commons.codec.binary.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zh
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
//	private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	@Autowired
	private UserService userService;
	@Autowired
	UserDao userDao;
	/*
	 * 利用正则映射到需要拦截的路径    
	 
    private String mappingURL;
    
    public void setMappingURL(String mappingURL) {    
               this.mappingURL = mappingURL;    
    }   
  */
    /** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *     
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */
	//登录判定 先是Session再是Cookie
    @Override  
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

		//直接访问系统的某个页面时执行，保存该页面的地址到Session中（如果之前登录过，则直接跳转到该地址，如果没有登录过，则跳转登录页面，登录后，直接跳转到该地址）
//    	if ("GET".equalsIgnoreCase(request.getMethod())) {
//    		RequestUtil.saveRequest();
//        }
    	
        //log.info("==============执行拦截: preHandle================");  
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
//		request.getContextPath()返回项目根路径，也就是项目的名字,如果项目为根目录,则得到一个"",即空的字条串,
//		如果项目为abc, <%=request.getContextPath()% >/   将得到abc/
		String url = requestUri.substring(contextPath.length());
		
		//这里对拉入黑名单的ip进行处理【扩展】
//		log.info("来自 >>>>>>"+RequestUtil.getIpAddr(request)+" 请求访问。");
		
        User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
        
        // 判断用户是否登录，一般是都没登录的    先判断Session再判断Cookie,   Cookie判定成功的话跳转至index.jsp而不是Session中存储的
        if(user == null){
        	// 取cookie值，这里还有其他网站的
        	Cookie[] cookies = request.getCookies();
        	if(cookies!=null && cookies.length>0){
	  			  String cookieValue = null;
	  			  // 下面是找到本项目的cookie
	  			  for (int i = 0; i < cookies.length; i++) {
	  				  if(Const.COOKIEDOMAINNAME.equals(cookies[i].getName())){
	  					  cookieValue = cookies[i].getValue();
	  					  break;
	  				  }
	  			  }
	  			  // 如果cookieValue为空说明用户上次没有选择“记住下次登录”执行其他
	  			  if(cookieValue==null){
	  				  if (url.contains("index")){
	  					  return true;
	  				  }
	  				  request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
		  	          return false;
	  			  }else{
	  				  // 先得到的CookieValue进行Base64解码
	  				  //String cookieValueAfterDecode = new String(base64.decode(cookieValue),"UTF-8");
	  				  String cookieValueAfterDecode = new String(Base64.decodeBase64(cookieValue),"UTF-8");
	  				  // 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
	  				  String cookieValues[] = cookieValueAfterDecode.split(":");
	  				  if(cookieValues.length!=3){ 
	  					  request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	  		        	  return false;
	  				  }
	  				  // 判断是否在有效期内,过期就删除Cookie
	  				  long validTimeInCookie = new Long(cookieValues[1]);
	  				  if (validTimeInCookie < System.currentTimeMillis()) {
	  					  // 删除Cookie
	  					  UserCookieUtil.clearCookie(response);
	  					  request.getRequestDispatcher("/WEB-INF/jsp/login_noUser_2.jsp").forward(request, response);
	  		        	  return false;
	  				  }
	  				  // 取出cookie中的用户名,并到数据库中检查这个用户名,
	  				  String userName = cookieValues[0];
	  				 User temp = userService.findUserByName(userName);
	  				  // 如果user返回不为空,就取出密码,使用用户名(userName)+密码+有效时间+ webSiteKey进行MD5加密。与前面设置的进行比较，看是否是同一个用户
	  				  if(temp!=null){
	  					  String md5ValueInCookie = cookieValues[2];
	  					  String md5ValueFromUser = MD5.md5crypt(temp.getUsername() + ":" + temp.getPassword() + ":" + validTimeInCookie + ":" + Const.COOKIEDOMAINNAME);
	  					  // 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
	  					  if (md5ValueFromUser.equals(md5ValueInCookie)) {
	  						  request.getSession().setAttribute(Const.SESSION_USER, temp);
	  						  request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	  			        	  return false;
	  					  }
	  				  }
  			      }
  		     }else{
  		    	if (url.contains("index")){
				    return true;
			    }
  		    	request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
  	            return false;
  		    }
        }
		Role role=userDao.getRoleByUserId(user.getId());
        if(url.contains("main")&&role.getName().equals("前台人员")){
			request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
			return false;
		}
        return true;   
    }  
  
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作   
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override  
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }  
  
    /** 
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等  
     *  
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */  
    @Override  
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {  
    }  

}  
