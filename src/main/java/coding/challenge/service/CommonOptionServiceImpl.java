package coding.challenge.service;

import coding.challenge.service.api.CommonOptionService;
import coding.challenge.type.OptionType;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.Objects;

public class CommonOptionServiceImpl implements CommonOptionService {
    
    @Override
    public Options createOptions() {
        return new Options()
                .addOption(createOption(OptionType.COUNT_BYTES))
                .addOption(createOption(OptionType.COUNT_LINES))
                .addOption(createOption(OptionType.COUNT_WORDS))
                .addOption(createOption(OptionType.COUNT_CHARS))
                .addOption(createOption(OptionType.HELP));
    }

    private Option createOption(OptionType optionType) {
        String opt = optionType.getOpt();
        String longOpt = optionType.getLongOpt();
        boolean hasArgs = optionType.hasArgs();
        String description = optionType.getDescription();

        if (Objects.nonNull(longOpt)) {
            return Option.builder(opt)
                    .longOpt(longOpt)
                    .hasArg(hasArgs)
                    .desc(description)
                    .build();
        }

        return Option.builder(opt)
                .hasArg(hasArgs)
                .desc(description)
                .build();
    }
}
