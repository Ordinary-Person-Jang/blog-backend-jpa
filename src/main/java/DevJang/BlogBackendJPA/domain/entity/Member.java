package DevJang.BlogBackendJPA.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter @Setter
//@Table(uniqueConstraints = {
//        @UniqueConstraint(
//            columnNames = {"email", "MEMBER_NAME"}
//)})
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "MEMBER_NAME")
    private String nickName;

    @Column(name = "USER_ROLE")
    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String provider;

    private String providerId;

    private String loginId;

    @OneToMany(mappedBy = "member")
    private List<Content> contents = new ArrayList<>();

    public Member() {
    }

    public Member(String email) {
        this.email = email;
    }

    public Member(String password, String nickName) {
        this.password = password;
        this.nickName = nickName;
    }

    public Member(String email, String password, String nickName, Authority authority) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.authority = authority;
    }

    public Member(String email, String password, String nickName, Authority authority, String loginId) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.authority = authority;
        this.loginId = loginId;
    }

    public void ChangeMemeberInfo(String password, String nickName) {
        this.password = password;
        this.nickName = nickName;
    }
}
