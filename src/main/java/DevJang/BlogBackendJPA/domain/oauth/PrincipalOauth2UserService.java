//package DevJang.BlogBackendJPA.domain.oauth;
//
//import DevJang.BlogBackendJPA.domain.member.MemberRepository;
//import DevJang.BlogBackendJPA.domain.oauth.provider.GoogleUserInfo;
//import DevJang.BlogBackendJPA.domain.oauth.provider.OAuth2UserInfo;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Slf4j
//@Service
//public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
//    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder encoder;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//        log.info("oAuth2User.getAttributes() = {}", oAuth2User.getAttributes());
//
//        OAuth2UserInfo oAuth2UserInfo = null;
//        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
//            log.info("구글 로그인 요청");
//            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
//        }else {
//            log.info("지원하지 않는 서비스");
//        }
//
//        String provider = oAuth2UserInfo.getProvider();
//        String providerId = oAuth2UserInfo.getProviderId();
//        String username = provider + "-" + providerId;
//        String password = encoder.encode("DevJang");
//        String email = oAuth2UserInfo.getEmail();
//        String role = "ROLE_USER";
//
//        return super.loadUser(userRequest);
//    }
//}
