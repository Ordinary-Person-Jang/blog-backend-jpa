package DevJang.BlogBackendJPA.web.api;

import DevJang.BlogBackendJPA.domain.auth.PrincipalDetails;
import DevJang.BlogBackendJPA.domain.entity.Member;
import DevJang.BlogBackendJPA.domain.member.MemberRepository;
import DevJang.BlogBackendJPA.web.form.login.LoginForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginApiController {
    private final MemberRepository memberRepository;
//    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public Object login(HttpServletRequest request) {
        log.info("로그인 요청");
        if (request.getAttribute("principal") != null) {
            PrincipalDetails principal = (PrincipalDetails) request.getAttribute("principal");
            Member member = principal.getMember();

            return new LoginResponse(member.getId(), member.getNickName());
        } else {
            throw new BadCredentialsException("등록되지 않은 회원입니다");
        }
    }

    @GetMapping("/loginfail")
    public Object loginfail(HttpServletRequest request) {
        log.info("로그인 실패 처리");
        Exception exception = (Exception) request.getAttribute("exception");
        log.error("execption", exception.getMessage());
        return new LoginResponse(1L, "devJang");
    }

    @Data
    static class LoginResponse {
        private Long id;
        private String nickName;

        public LoginResponse(Long id, String nickName) {
            this.id = id;
            this.nickName = nickName;
        }
    }
}
