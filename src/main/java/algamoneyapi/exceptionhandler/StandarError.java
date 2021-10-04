package algamoneyapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
public class StandarError {

    private final String message;
    private final String messageDev;
}
