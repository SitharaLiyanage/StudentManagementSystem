package lk.ijse.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassScheduleDTO {
    private String tutorID;
    private String classID;
    private String hallID;
    private String date;
    private String time;
}
