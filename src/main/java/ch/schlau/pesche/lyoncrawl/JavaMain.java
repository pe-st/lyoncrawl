package ch.schlau.pesche.lyoncrawl;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Class with a Java main()
 * <p>
 * This is effectively the same as running the {@link Main} directly, but has the advantage it can be run from the IDE.
 */
@QuarkusMain
public class JavaMain {

    public static void main(String... args) {
        Quarkus.run(Main.class, args);
    }
}
