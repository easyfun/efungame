//package com.mexc.sun.corestest.service.impl;
//
//import ExceptionGenerator;
//import com.mexc.sun.coremember.entity.Member;
//import com.mexc.sun.coremember.entity.builder.MemberBuilder;
//import com.mexc.sun.coremember.service.CoreMemberIndependentTransactionService;
//import com.mexc.sun.coremember.service.MemberService;
//import TransactionTestService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by easyfun on 2018/4/24.
// */
//@Service
//public class TransactionTestServiceImpl implements TransactionTestService {
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private CoreMemberIndependentTransactionService coreMemberIndependentTransactionService;
//
//    @Override
//    public int createMember() {
//        Member member = MemberBuilder.buildTest();
//        memberService.createMember(member);
//
//        member = MemberBuilder.buildTest();
//        coreMemberIndependentTransactionService.insertMember(member);
//        ExceptionGenerator.createException();
//        return 0;
//    }
//}
