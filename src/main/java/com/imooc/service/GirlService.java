package com.imooc.service;

import com.imooc.domain.Girl;
import com.imooc.enums.ResultEnums;
import com.imooc.exception.GirlException;
import com.imooc.repository.GirlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 廖师兄
 * 2016-11-04 00:08
 */
@Service
public class GirlService {

    @Autowired
    private GirlRepository girlRepository;

    @Transactional
    public void insertTwo() {
        Girl girlA = new Girl();
        girlA.setCupSize("A");
        girlA.setAge(18);
        girlRepository.save(girlA);


        Girl girlB = new Girl();
        girlB.setCupSize("BBBB");
        girlB.setAge(19);
        girlRepository.save(girlB);
    }


    public Integer getAge(Integer id) throws Exception {
        Girl girl = girlRepository.findOne(id);
        if (girl.getAge() < 10) {
            //code=100
            throw new GirlException(ResultEnums.PRIMARY_SCHOOL);
        } else if (girl.getAge() > 10 && girl.getAge() < 16) {
            //code=101
            throw new GirlException(ResultEnums.MIDDLE_SCHOLL);
        }
        return girl.getAge();
    }
}
