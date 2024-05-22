package Book_Toy_Project.BOOK.Form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginForm {

    @Email(message = "올바른 이메일 주소 형식이어야 합니다.")
    @NotBlank(message = "회원 아이디는 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!~`*()_+=]).*$",
            message = "비밀번호가 일치하지 않습니다. 다시 한 번 확인해주세요.")
    private String password;

}
