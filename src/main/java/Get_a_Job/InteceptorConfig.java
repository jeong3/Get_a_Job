package Get_a_Job;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import Get_a_Job.domain.AuthInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class InteceptorConfig implements HandlerInterceptor {
	@Override //컨트롤러에 들어가기 전에 차단 (컨트롤러에 들어오기전에 캐치하는 함수)
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		AuthInfoDTO auth = (AuthInfoDTO) session.getAttribute("auth");
		  if (ObjectUtils.isEmpty(auth)) {
		        response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().write("<script>alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.'); location.href='/login/login';</script>");
		        response.getWriter().flush();
		        return false;
		    }
		return true;
	}
	@Override // 컨트롤러 들어왔을 때 차단
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override // html페이지가 열렸을 때 차단
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
