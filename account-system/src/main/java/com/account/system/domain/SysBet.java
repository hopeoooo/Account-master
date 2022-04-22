package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author hope
 * @since 2022/4/22
 */
@Data
public class SysBet extends BaseEntity {

    private Long betId;

    private String card;

    private Long tableId;

    private Long bootNum;

    private Long gameNum;

    private int type;

    private Long gameId;

    private String gameResult;
}
