package util.parser;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Parser {
    public static String[] toStringArray(int[] ii) {
        IntStream st = Arrays.stream(ii);
        return st.boxed().map(String::valueOf).toArray(String[]::new);
    }

    public static String[] toStringArray(double[] dd) {
        DoubleStream ds = Arrays.stream(dd);
        return ds.boxed().map(String::valueOf).toArray(String[]::new);
    }
}
