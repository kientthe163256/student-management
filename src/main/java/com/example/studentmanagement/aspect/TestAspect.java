package com.example.studentmanagement.aspect;

import com.example.studentmanagement.entity.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.List;


@Aspect
@Component
public class TestAspect {
    private Logger logger = LoggerFactory.getLogger(TestAspect.class);

//    @Before("execution(* com.example.studentmanagement.service.*.*(..))")
//    public void before(JoinPoint joinPoint){
//        logger.info("Before calling " + joinPoint.toString());
//    }

//    @Around("execution(* com.example.studentmanagement.service.*.*(..))")
//    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        Long startTime = System.currentTimeMillis();
//        logger.info("Start Time Taken by {} is {}", joinPoint, startTime);
//        joinPoint.proceed();
//
//        Long timeTaken = System.currentTimeMillis() - startTime;
//        logger.info("Time Taken by {} is {} milliseconds", joinPoint, timeTaken);
//    }

    @Around("execution(* com.example.studentmanagement.service.StudentService.getAllStudent(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        logger.info("Start Time Taken by {} is {}", joinPoint, startTime);
        Object objectProceed = joinPoint.proceed();

        Long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken by {} is {} milliseconds", joinPoint, timeTaken);
        return objectProceed;
    }

    @Pointcut("@target(org.springframework.stereotype.Service) && within(com.example.studentmanagement.*)")
    public void serviceMethods() {}

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {}
    @Pointcut("execution(* *..create*(Long,..))")
    public void firstLongParamMethods() {}
    @Pointcut("repositoryMethods() && firstLongParamMethods()")
    public void entityCreationMethods() {}



    @Before("entityCreationMethods()")
    public void before(JoinPoint joinPoint){
        logger.info("-----------------------Before calling " + joinPoint.toString());
//        System.out.println("-------------------------zobro-----------");
    }
}
