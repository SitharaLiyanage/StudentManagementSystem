package lk.ijse.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class UserDTO {
    private String mail;
    private String userName;
    private String password;

}
