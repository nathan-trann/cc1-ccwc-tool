package coding.challenge.app;

import coding.challenge.service.*;
import coding.challenge.service.api.*;

import java.util.Optional;
import org.apache.commons.cli.Options;

public class WC {

    private final CommonOptionService commonOptionService;
    private final CommandLineService commandLineService;

    public WC(CommonOptionService commonOptionService, 
              CommandLineService commandLineService) {
        this.commonOptionService = commonOptionService;
        this.commandLineService = commandLineService;
    }

    public static void main(String[] args) {
        WC wc = initialize();
        wc.run(args);
    }

    private static WC initialize() {
        CommonOptionService commonOptionService = new CommonOptionServiceImpl();
        InputProcessingService inputProcessingService = new InputProcessingServiceImpl();
        FileProcessingService fileProcessingService = new FileProcessingServiceImpl();
        FileStatisticsProcessingService fileStatisticsProcessingService = new FileStatisticsProcessingServiceImpl(inputProcessingService, fileProcessingService);
        CommandLineService commandLineService = new CommandLineServiceImpl(commonOptionService, fileStatisticsProcessingService);

        return new WC(commonOptionService, commandLineService);
    }

    private void run(String[] args) {

        Options options = commonOptionService.createOptions();
        Optional<String> result = commandLineService.processInput(args, options);

        result.ifPresent(System.out::println);
    }
}