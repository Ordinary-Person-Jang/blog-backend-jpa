package DevJang.BlogBackendJPA.domain.jwt;

public interface JwtProperties {
    String SECRET = "DevJang";
    int EXPIRATION_TIME = 60000 * 30;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
