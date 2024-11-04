package net.plexverse.mapparser.parser;

public interface ParsingStrategy {
    void parse(Runnable onComplete, final boolean complete);

    boolean validate(Runnable runnable, boolean ignoreBorder);
}
