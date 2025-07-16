package ru.sinara;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S4738")
public class HelloOtus {
    private static final Logger LOG = LoggerFactory.getLogger(HelloOtus.class);

    public static void main(String[] args) {

        ImmutableList<String> immutableList = ImmutableList.of("Hello", "Otus", "give", "me", "money");

        LOG.atInfo()
                .setMessage("They said: {}")
                .addArgument(() -> String.join(" ", immutableList))
                .log();
    }
}
