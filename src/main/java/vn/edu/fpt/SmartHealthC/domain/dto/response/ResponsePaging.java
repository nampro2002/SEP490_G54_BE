package vn.edu.fpt.SmartHealthC.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePaging<T> {
    private Integer currentPage;
    private Integer totalItems;
    private Integer totalPages;
    private T dataResponse;
}
