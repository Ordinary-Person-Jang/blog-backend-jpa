package DevJang.BlogBackendJPA.domain.jwt;

import DevJang.BlogBackendJPA.domain.auth.PrincipalDetails;
import DevJang.BlogBackendJPA.domain.entity.Member;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(member.getLoginId(), member.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);

            System.out.println("authentication = " + authentication);
            PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("principal.getMember().getLoginId() = " + principal.getMember().getLoginId());
            return authentication;
        } catch (BadCredentialsException e ) {
            log.error("BadCredentialsException = ",e );
            throw new BadCredentialsException(e.getMessage());
        } catch (InternalAuthenticationServiceException e) {
            log.error("InternalAuthenticationServiceException = ",e );
            throw new InternalAuthenticationServiceException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject("DevJang토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principal.getMember().getId())
                .withClaim("loginId", principal.getMember().getLoginId())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        request.setAttribute("principal", principal);
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        chain.doFilter(request,response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        System.out.println("로그인 실패 프로세스");
        request.setAttribute("exception", failed);
    }
}
