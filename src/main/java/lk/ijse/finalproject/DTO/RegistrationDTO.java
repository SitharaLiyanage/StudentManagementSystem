package lk.ijse.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private String classId;
    private String studentId;
    private double regFees;
    private String remark;
    private String date;
}
