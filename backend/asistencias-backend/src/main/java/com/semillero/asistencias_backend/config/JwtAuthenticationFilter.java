package com.semillero.asistencias_backend.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.semillero.asistencias_backend.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component//para inyectar en SecurityConfig
//@RequiredArgsConstructor//Se usa para hacer la inyeccion con lombok
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil ) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws IOException,ServletException {
        String authHeader = request.getHeader("Authorization");
        String username=null;
        String jwt =null;
        List<String> roles = new ArrayList<>();

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwt =authHeader.substring(7); // Quita "Bearer "
            try {
                username = jwtUtil.getUsername(jwt);
                roles = jwtUtil.getRoles(jwt);
            } catch (Exception e) {
                //El token esta expirado o invalido, SpringSecurity puede manejarlo 401/403
            }
        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
            if(jwtUtil.validateToken(jwt, username)){

                List<SimpleGrantedAuthority> authorities =roles.stream()
                                                        .map(role ->{
                                                            return role.startsWith("ROLE_") ?
                                                                new SimpleGrantedAuthority(role):
                                                                new SimpleGrantedAuthority("ROLE_"+role.toUpperCase());
                                                        }).toList();
                UsernamePasswordAuthenticationToken auth =
                                    new UsernamePasswordAuthenticationToken(username, null,authorities);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }
        filterChain.doFilter(request, response);
    }
    
}
