package com.gdg.coffee.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gdg.coffee.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
