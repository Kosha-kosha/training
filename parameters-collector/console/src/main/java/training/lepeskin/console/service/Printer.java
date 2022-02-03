package training.lepeskin.console.service;

import java.util.Map;

public class Printer {
    public void printMap(Map<?, ?> map, String stringFormat) {
        map.entrySet().stream().map(key -> String.format(stringFormat, key.getKey(), key.getValue()))
                .forEach(System.out::println);
    }

    public void printPair(Object first, Object second, String stringFormat) {
        System.out.println(String.format(stringFormat, first, second));
    }
}
