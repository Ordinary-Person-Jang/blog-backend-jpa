package DevJang.BlogBackendJPA.domain.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private ObjectMapper om = new ObjectMapper();
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();
        data.put("timeStamp", LocalDateTime.now());
        data.put("exception", exception.getMessage());
        response.getOutputStream().print(om.writeValueAsString(data));
    }
}
