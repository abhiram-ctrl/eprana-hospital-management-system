package com.eprana.backend.security;

import com.eprana.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    public  JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {

        String path = request.getServletPath();

        return path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader=
                request.getHeader("Authorization");
        //bearer token checking
        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {
            String token=authHeader.substring(7);
            boolean valid=
                    jwtService.isValidToken(token);
            if(valid)
            {
                String email=
                        jwtService.extractEmail(token);
                String role=
                        jwtService.extractRole(token);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email, null,
                        List.of(new SimpleGrantedAuthority("ROLE_"+role)));
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else
            {
                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );
                response.getWriter().write(
                        "Invalid token"
                );
                return;
            }

        }
        filterChain.doFilter(request,response);
    }
}
