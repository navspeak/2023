package oauth2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;

public class PkceUtil {
    String generateCodeVerifier() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }

    String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bytes, 0, bytes.length);
        byte[] digest = messageDigest.digest();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    public static void main(String[] args) {
        System.out.println(new PkceUtil().generateCodeVerifier());
    }
}
/*

https://www.baeldung.com/spring-security-oauth-resource-server
Resource Server - Before adding Role-based access control

https://github.com/simplyi/ResourceServer/tree/before-adding-role-based-acceess-control

Resource Server - After adding Role-based access control

https://github.com/simplyi/ResourceServer/tree/after-adding-role-based-acceess-control

Resource Server - Before adding method-level security code

https://github.com/simplyi/ResourceServer/tree/resourceserver-before-adding-method-level-security

Resource Server - After adding method-level security code

https://github.com/simplyi/ResourceServer/tree/resourceserver-after-adding-method-level-security

Before
Photos Resource Server before configuring it to be a Eureka Discovery Client

https://github.com/simplyi/Photos-Resource-Server/tree/Photos-Resource-Server-Before-Eureka-Client

Albums Resource Server before configuring it to be a Eureka Discovery Client

https://github.com/simplyi/Albums-Resource-Server/tree/Albums-Resource-Server-Before-Eureka-Client


After
Photos Resource Server after configuring it to be a Eureka Discovery Client

https://github.com/simplyi/Photos-Resource-Server/tree/Eureka-Client

Albums Resource Server after configuring it to be a Eureka Discovery Client

https://github.com/simplyi/Albums-Resource-Server/tree/Eureka-Client


!!! Important
A web client application - Initial empty project

https://github.com/simplyi/PhotoAppWebClient/tree/initial-project

A web client application - Example with RestTemplate

https://github.com/simplyi/PhotoAppWebClient/tree/rest-template-example

A web client application - Example using WebClient

https://github.com/simplyi/PhotoAppWebClient/tree/webclient-example
https://www.baeldung.com/spring-security-oauth-cognito

https://github.com/simplyi/SocialLoginWebClient

--
(for google, FB, most if below are preconfigured with spring security
like:
spring.security.oauth2.client.registration.google.client-id = photo-app-webclient
spring.security.oauth2.client.registration.google.client-secret = xxx
)

spring.security.oauth2.client.registration.mywebclient.client-id = photo-app-webclient
spring.security.oauth2.client.registration.mywebclient.client-secret = 5065bd8e-7cee-4f7a-b0d3-3848dce5010c
spring.security.oauth2.client.registration.mywebclient.scope = openid, profile, roles
spring.security.oauth2.client.registration.mywebclient.authorization-grant-type = authorization_code
spring.security.oauth2.client.registration.mywebclient.redirect-uri = http://localhost:8087/login/oauth2/code/mywebclient

spring.security.oauth2.client.provider.mywebclient.authorization-uri = http://localhost:8080/auth/realms/appsdeveloperblog/protocol/openid-connect/auth
spring.security.oauth2.client.provider.mywebclient.token-uri = http://localhost:8080/auth/realms/appsdeveloperblog/protocol/openid-connect/token
spring.security.oauth2.client.provider.mywebclient.jwk-set-uri=http://localhost:8080/auth/realms/appsdeveloperblog/protocol/openid-connect/certs
spring.security.oauth2.client.provider.mywebclient.user-info-uri = http://localhost:8080/auth/realms/appsdeveloperblog/protocol/openid-connect/userinfo
spring.security.oauth2.client.provider.mywebclient.user-name-attribute = preferred_username
--

https://auth0.com/blog/id-token-access-token-what-is-the-difference/
 */