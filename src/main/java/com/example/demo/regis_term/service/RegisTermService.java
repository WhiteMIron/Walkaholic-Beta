package com.example.demo.regis_term.service;

import com.example.demo.regis_term.model.entity.RegisTermResponse;
import com.example.demo.regis_term.repository.RegisTermRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegisTermService
{

    private final RegisTermRepository regisTermRepository;

    public RegisTermService (RegisTermRepository regisTermRepository){
        this.regisTermRepository= regisTermRepository;
    }

    @Transactional
    public RegisTermResponse getRegisTermContent(String regisTermName){
        return regisTermRepository.findContentByName(regisTermName);
    }

}
