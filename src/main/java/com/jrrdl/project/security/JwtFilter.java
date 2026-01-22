package com.jrrdl.project.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter{
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService userDetailService;

    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailService userDetailService){
        this.jwtProvider = jwtProvider;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
    throws ServletException, IOException{
        String header = request.getHeader("Authorization");
        
        if(header != null && header.startsWith("Bearer ")){
            String token = header.substring(7);


            if(jwtProvider.validateToken(token)){
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }


        filterChain.doFilter(request, response);
    
    }
    
}
