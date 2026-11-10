package com.jsh.erp.datasource.vo;

import com.jsh.erp.datasource.entities.DepotItemVo4DetailByTypeAndMId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepotItemVo4DetailWithCurrentUnitPrice extends DepotItemVo4DetailByTypeAndMId {

    private BigDecimal currentUnitPrice;

}
