package com.shoppingMall.reggie;

import com.shoppingMall.reggie.controller.ScoreLoanController;
import com.shoppingMall.reggie.dto.ScoreLoanDTO;
import com.shoppingMall.reggie.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReggieApplicationTests {
    //
    @Autowired
    private ScoreLoanController scoreLoanController;

    @Test
    void testScoreLoanController(){

    }
}
