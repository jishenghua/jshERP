package com.jsh.erp.datasource.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author jishenghua
 * @Date 2024-04-26 22:23
 */
@Data
public class InOutPriceVo {

    private Long id;

    private BigDecimal discountLastMoney;

    private BigDecimal totalPrice;

    private String type;

    private String subType;

    private Date operTime;

}
