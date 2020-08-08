package rest.chat.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseREST {
	String username;
	String password;
	String success;
	String token;
}
