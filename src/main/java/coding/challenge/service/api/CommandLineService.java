package coding.challenge.service.api;

import org.apache.commons.cli.Options;

import java.util.Optional;

public interface CommandLineService {

    Optional<String> processInput(String[] args, Options options);
}
