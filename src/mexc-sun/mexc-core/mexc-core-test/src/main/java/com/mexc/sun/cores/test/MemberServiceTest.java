//package com.mexc.sun.corestest;
//
//import SpringTestCase;
//import com.mexc.sun.coremember.entity.Member;
//import com.mexc.sun.coremember.entity.builder.MemberBuilder;
//import com.mexc.sun.coremember.service.MemberService;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * Created by easyfun on 2018/4/24.
// */
//public class MemberServiceTest extends SpringTestCase{
//    private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceTest.class);
//
//    @Autowired
//    private MemberService memberService;
//
//    @Test
//    public void createMember() {
//        Member member = MemberBuilder.buildTest();
//        memberService.createMember(member);
//    }
//}
