package algamoneyapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
public class FieldMessageError {

    private final String field;
    private final String message;
}
