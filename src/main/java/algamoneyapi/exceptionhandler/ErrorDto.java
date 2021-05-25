package algamoneyapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
public class ErrorDto {

    private final String field;
    private final String message;
    private final String messageDev;
}
