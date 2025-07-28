package com.bezkoder.spring.security.login.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class aspect {

    @Before("execution(* com.bezkoder.spring.security.login.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        System.out.println("Aboudeh Aspect 🤷‍♂️🤷‍♂️🤷‍♂️" );
        System.out.println("Before method: " + joinPoint.getSignature().getName());
    }
}