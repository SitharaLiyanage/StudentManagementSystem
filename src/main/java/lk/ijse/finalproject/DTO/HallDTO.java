package lk.ijse.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {
    private String hallID;
    private String name;
    private String capacity;
}
