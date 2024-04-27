package lw.com.Admin.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVo {
//    第几页
    private Integer page = 1;
//    每页大小
    private Integer size = 3;
}
