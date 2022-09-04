package com.example.ToDoList.Security;

import com.example.ToDoList.Exception.TokenRefreshException;
import com.example.ToDoList.Model.RefreshToken;
import com.example.ToDoList.Repository.RefreshTokenRepository;
import com.example.ToDoList.Repository.UserRepository;
import com.example.ToDoList.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;


@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${todolist.app.jwtSecret}")
    private String jwtSecret;
    @Value("${todolist.app.jwtExpirationMs}")
    private int jwtExpirationMs;
    @Value("${todolist.app.jwtCookieName}")
    private String jwtCookie;
    @Value("${todolist.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;
    @Value("${todolist.app.jwtRefreshSecret}")
    private String jwtSecretRefresh;




    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;



    //////РЕФРЕШ ТОКЕН ///////////////////// ///////////////////// ///////////////////// ///////////////////// /////////////////////



    public String createRefreshToken(String email) {

        return Jwts.builder()
                .setSubject((email))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecretRefresh)
                .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
                .compact();

    }
    public RefreshToken saveRefreshToken(String email, Long id)
    {
        if(refreshTokenRepository.findUser(id)==null)
        {
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setToken(createRefreshToken(email));
            refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
            refreshToken.setUser(userRepository.findByEmail(email).get());
            refreshTokenRepository.save(refreshToken);
            return refreshToken;
        }
        else {
            RefreshToken refreshToken1 = refreshTokenRepository.findToken(id);
                refreshToken1.setToken(createRefreshToken(email));
                refreshToken1.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
                refreshToken1.setUser(userRepository.findByEmail(email).get());
                refreshTokenRepository.save(refreshToken1);
                return refreshToken1;
            }
        }


    public Optional<RefreshToken> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    public String getEmailFromRefreshToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecretRefresh).parseClaimsJws(token).getBody().getSubject();
    }


    /////////////////////АКСЕСС ТОКЕН ///////////////////// ///////////////////// ///////////////////// ///////////////////// /////////////////////
    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateJwtTokenFromEmail(userPrincipal.getEmail());
    }

    public String generateJwtTokenFromEmail(String email ) {

        return Jwts.builder()
                .setSubject((email))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    ///////////////////// КУКИ ///////////////////// ///////////////////// ///////////////////// ///////////////////// /////////////////////

    public ResponseCookie generateJwtCookie(RefreshToken refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, refreshToken.getToken()).path("/").maxAge(24*60*60*15).sameSite("None").secure(true).httpOnly(true).build();
        return cookie;
    }


    public String getJwtFromCookies(HttpServletRequest request) {

        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }

    }
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }


}