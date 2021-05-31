package com.example.demo.regis_term.repository;

import com.example.demo.regis_term.model.entity.RegisTerm;
import com.example.demo.regis_term.model.entity.RegisTermResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegisTermRepository extends JpaRepository<RegisTerm,Long> {

    @Query(value="select registerm_content as RegisTermContent from regis_term where registerm_name=:regisTermName",nativeQuery=true)
    RegisTermResponse findContentByName(String regisTermName);
}
