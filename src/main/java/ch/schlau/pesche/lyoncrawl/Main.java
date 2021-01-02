package ch.schlau.pesche.lyoncrawl;

import static ch.schlau.pesche.lyoncrawl.Main.APP_NAME;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.QuarkusApplication;
import picocli.CommandLine;
import picocli.CommandLine.PropertiesDefaultProvider;

@CommandLine.Command(name = APP_NAME, mixinStandardHelpOptions = true)
public class Main implements Runnable, QuarkusApplication {

    public static final String APP_NAME = "lyon-crawl";

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @CommandLine.Option(names = { "-u", "--user" }, description = "User name for the confluence site. Default from dot file: ${DEFAULT-VALUE}")
    String user;

    @CommandLine.Option(names = { "--password" }, description = "Password for the confluence site. Default might be stored in dot file",
                        arity = "0..1", interactive = true)
    String password;

    @CommandLine.Option(names = { "--baseurl" }, description = "Base URL of the confluence site. Default from dot file: ${DEFAULT-VALUE}")
    URL baseurl;

    @Inject
    CommandLine.IFactory factory;

    @Override
    public void run() {
        // business logic
        //        System.out.printf("Hello %s:%s %s%n", user, password, baseurl);
        String authorization = ContentServiceClient.basicAuth(user, password);
        ContentServiceClient lyon = new ContentServiceClient(baseurl, authorization);
        String jsonString = lyon.getManyAsString("LYON");
        System.out.println(jsonString);
    }

    @Override
    public int run(String... args) throws Exception {
        CommandLine commandLine = new CommandLine(this, factory);
        picocliDefaultsFile()
                .ifPresent(f -> {
                    logger.info("Picocli properties file found: {}", f);
                    commandLine.setDefaultValueProvider(new PropertiesDefaultProvider(f));
                });
        return commandLine.execute(args);
    }

    /**
     * Find the defaults properties file for picocli's PropertiesDefaultProvider
     * <p>
     * Try the current base directory first, fallback to a properties file in the user's home directory
     *
     * @return
     */
    private Optional<File> picocliDefaultsFile() {

        Path currentDir = Paths.get(System.getProperty("user.dir"));
        Path baseDir = currentDir.endsWith("target") ? currentDir.getParent() : currentDir;
        logger.debug("current: {}, base: {}", currentDir, baseDir);

        File picocliDefaultLocation = new File(System.getProperty("user.home"), "." + APP_NAME + ".properties");
        File currentDirectoryLocation = new File(baseDir.toString(), "." + APP_NAME + ".properties");
        logger.debug("current location: {}", currentDirectoryLocation.getAbsolutePath());

        if (currentDirectoryLocation.isFile()) {
            return Optional.of(currentDirectoryLocation);
        } else if (picocliDefaultLocation.isFile()) {
            return Optional.of(picocliDefaultLocation);
        } else {
            return Optional.empty();
        }
    }
}
