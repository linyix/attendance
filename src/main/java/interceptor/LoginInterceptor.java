package interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pojo.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

    public class LoginInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
        {
            /*
            Employee employeetemp = new Employee();
            employeetemp.setId(2);
            employeetemp.setName("张五");
            request.getSession().setAttribute("user",employeetemp);
            */
            Employee employee = (Employee) request.getSession().getAttribute("user");
            if(employee==null)
            {
                response.sendRedirect("/login");
                return false;
            }
            return true;

        }
    }

