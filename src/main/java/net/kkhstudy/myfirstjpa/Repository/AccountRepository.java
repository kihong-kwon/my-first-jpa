package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// 여기는 @Repository를 붙일 필요없다.실제 구현체 SimpleJpaRepository에 붙어있기 떄문에
// Spring @Repository를 붙이면 SQLException 또는 JPA 관련 예외를 DataAccessException으로 변환해 준다.
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

}
