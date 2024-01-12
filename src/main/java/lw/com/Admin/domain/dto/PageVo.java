package lw.com.Admin.domain.dto;

import lombok.Data;

@Data
public class PageVo {
//    第几页
    private Integer page = 1;
//    每页大小
    private Integer size = 3;
}
