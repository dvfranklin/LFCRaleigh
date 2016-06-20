package com.lfcraleigh;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MemberRepository extends CrudRepository<Member, Integer> {

    List<Member> findAllByOrderByIdAsc();
}
