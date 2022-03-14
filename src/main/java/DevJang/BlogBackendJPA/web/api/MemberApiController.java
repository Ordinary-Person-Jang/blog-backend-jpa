package DevJang.BlogBackendJPA.web.api;

import DevJang.BlogBackendJPA.domain.entity.Authority;
import DevJang.BlogBackendJPA.domain.entity.Member;
import DevJang.BlogBackendJPA.domain.service.MemberService;
import DevJang.BlogBackendJPA.web.form.member.CreateMemberForm;
import DevJang.BlogBackendJPA.web.form.member.UpdateMemberForm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/new")
    public Object saveMember(@RequestBody @Validated CreateMemberForm form, BindingResult bindingResult) {
        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        Long result = memberService.join(form);
        return new MemberResponse(result);
    }

    @GetMapping("/check")
    public Object duplicateMemberCheck(@RequestParam String email) {
        memberService.checkEmail(email);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("/checknick")
    public Object duplicateNickName(@RequestParam String nickName) {
        memberService.checkNickName(nickName);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/update")
    public Object updateMember(@RequestBody @Validated UpdateMemberForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 error = {}", bindingResult);
            return bindingResult.getAllErrors();
        }
        Long result = memberService.updateMemberInfo(form);
        return new MemberResponse(result);
    }

    @Data
    static class MemberResponse {
        private Long id;

        public MemberResponse(Long id) {
            this.id = id;
        }
    }
}
