package oauth2;

public class AuthzCode {
    /*
  1. https://auth0.com/intro-to-iam/what-is-oauth-2

     */

    static void _1AuthorizationCodeGrant(){
        System.out.println("1. Authorize User");
        System.out.println("==========================");
        System.out.println("" +
                "https://{yourDomain}/authorize?\n" +
                "    response_type=code&\n" +
                "    client_id={yourClientId}&\n" +
                "    redirect_uri={https://yourApp/callback}&\n" +
                "    scope={scope}&\n" +
                "    audience={apiAudience}&\n" +
                "    state={state}");
        /*
        <a href="https://{yourDomain}/authorize?
          response_type=code&
          client_id={yourClientId}&
          redirect_uri={https://yourApp/callback}&
          scope=appointments%20contacts&
          audience=appointments:api&
          state=xyzABC123">
          Sign In
        </a>
         */
        System.out.println("Response:" +
                "HTTP/1.1 302 Found\n" +
                "Location: {https://yourApp/callback}?code={authorizationCode}&state=xyzABC123");
        // https://openid.net/specs/openid-connect-core-1_0.html#StandardClaims
        System.out.println("2. Request Token");
        System.out.println("" +
                "curl --request POST \\\n" +
                "  --url 'https://{yourDomain}/oauth/token' \\\n" +
                "  --header 'content-type: application/x-www-form-urlencoded' \\\n" +
                "  --data grant_type=authorization_code \\\n" +
                "  --data 'client_id={yourClientId}' \\\n" +
                "  --data 'client_secret={yourClientSecret}' \\\n" +
                "  --data 'code=yourAuthorizationCode}' \\\n" +
                "  --data 'redirect_uri={https://yourApp/callback}'");
        System.out.println("Response: " +
                "{\n" +
                "  \"access_token\": \"eyJz93a...k4laUWw\",\n" +
                "  \"refresh_token\": \"GEbRxBN...edjnXbL\",\n" +
                "  \"id_token\": \"eyJ0XAi...4faeEoQ\",\n" +
                "  \"token_type\": \"Bearer\"\n" +
                "}");
        System.out.println("3. Make API call:" +
                "curl --request GET \\\n" +
                "  --url https://myapi.com/api \\\n" +
                "  --header 'authorization: Bearer {accessToken}' \\\n" +
                "  --header 'content-type: application/json'" +
                "Refresh Token:" +
                "curl --request POST \\\n" +
                "  --url 'https://{yourDomain}/oauth/token' \\\n" +
                "  --header 'content-type: application/x-www-form-urlencoded' \\\n" +
                "  --data grant_type=refresh_token \\\n" +
                "  --data 'client_id={yourClientId}' \\\n" +
                "  --data 'refresh_token={yourRefreshToken}'");
        System.out.println("==========================");
    }
}

