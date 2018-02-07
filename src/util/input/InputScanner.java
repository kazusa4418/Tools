package util.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 文字型入力ストリームから効率よくテキスト行を読み込みます。
 * <p>デフォルトではストリームの接続先はｷｰﾎﾞｰﾄﾞとなり、標準入力を提供します。</p>
 * <p>デフォルトの{@code InputScanner}は引数に{@code InputStreamReader(System.in)}
 * を与えた{@code BufferedReader}と似た使い方ができますが、より実用的な使用ができます。</p>
 *
 * 具体的にはｷｰﾎﾞｰﾄﾞから入力をする際に入力を受け付けるまえにテキストを表示したり、
 * 正規表現に一致した入力のみを受け付ける、一致しなかった場合エラーメッセージを
 * 表示して再入力を求めるといった処理を簡単に実現することができます。</p>
 * <p>入力された文字列をint型に変換するメソッドも実装されています。
 * このメソッドもString型入力と同じく、テキストつきで入力を求めたり、正規表現を用いて
 * 入力を制限することが可能です。</p>
 * <p>具体的には各メソッドを参照してください。</p>
 *
 * <p>標準入力として{@code InputScanner}を利用する場合は、以下のメソッドが便利です。</p>
 * <pre>
 *     {@link #readStr(String)}
 *     {@link #readStrUntilMatch(String, String, String...)}
 *
 *     {@link #readInt(String, String)}
 *     {@link #readCheckedInt(String, String, Predicate[])}
 * </pre>
 *
 * <p>また、{@code InputScanner}のすべてのメソッドは{@link IOException}を送出する恐れがあります。
 * {@link #setIOErrMsg(String)}で、発生した際のエラーメッセージをあらかじめ
 * 設定しておくことを推奨します。
 * デフォルトでは、"IOError : try again."が設定されています。</p>
 *
 * @author kazusa4418
 * @see BufferedReader
 * @see Predicate
 * @see Reader
 * @version 1.0
 */
public final class InputScanner {
    private BufferedReader reader;

    /* IOErrorが発生したときに表示されるエラーメッセージ */
    private String IOErrMsg = "IOError : try again.";

    /* int型の最大値と最小値の絶対値 */
    private static final byte[] intMaxValue = { 2, 1, 4, 7, 4, 8, 3, 6, 4, 7 };
    private static final byte[] intMinValue = { 2, 1, 4, 7, 4, 8, 3, 6, 4, 8 };

    /**
     * 標準入力に接続された文字型ストリームを作成します。
     * この文字型ストリームはバッファサイズ8192バイトでバッファリングされています。
     */
    /* -------------------- コンストラクター -------------------- */
    public InputScanner() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * 文字型ストリームを作成します。
     * <p>標準入力以外のストリームの場合、正しく動作しません。
     * 今後のバージョンアップで実装する予定です。</p>
     *
     * <b>現状、このコンストラクターは使用しないでください</b>
     *
     * @param reader reader
     * @deprecated 接続先が標準入力以外のストリームは正しく動作しません。
     *             このコンストラクターは使用しないでください。
     *             今後のアップデートで実装する予定です。
     */
    public InputScanner(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    /* --------------------------------------------------------- */

    /**
     * テキスト行を読み込みます。
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>このメソッドは{@link IOException}を送出する可能性があります。
     * これが送出されると入力をいったん取り消し、再入力を試行します。</p>
     *
     *
     * 以下、標準入力からテキスト行を読み込む使用例です。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readStr();
     * </pre>
     *
     * @return 行の内容を含む文字列、ただし行の終端文字は含まれない。
     */
    /* -------------------- STRING INPUT -------------------- */
    public String readStr() {
        return readStr("");
    }

    /**
     * メッセージを出力し、テキスト行を読み込みます。
     * <p>このメソッドは{@link java.io.PrintStream#print(String)}を
     * 呼び出してから{@link #readStr()}を呼び出すのと同じ動作をします。</p>
     *
     * 以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readStr("input > ");
     * </pre>
     * 上記の処理を{@link #readStr()}で実装した場合が下記のコードです。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.our.print("input > ");
     *     String text = is.readStr();
     * </pre>
     *
     * @param  msg 出力するメッセージ
     * @return 行の内容を含む文字列、ただし行の終端文字は含まれない。
     */
    public String readStr(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return reader.readLine();
            }
            catch (IOException e) {
                System.err.println(IOErrMsg);
            }
        }
    }

    /**
     * メッセージを出力し、正規表現に一致するテキスト行を読み込もうとします。
     * <p>{@link #readStr(String)}の動作に加え、引数に正規表現を表現した
     * 文字列を指定することで読み込むテキスト行に制限をかけることができます。
     * 指定した正規表現を表現する文字列のうちどれかひとつでも一致すれば
     * テキスト行を読み込みます。</p>
     *
     * <p>テキスト行が正規表現にひとつも一致しなかった場合、空文字 "" が返却されます。</p>
     *
     * <p>以下、使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readCheckedStr("input > ",
     *                                     "[0-9]+",
     *                                     "[a-zA-Z]+");
     *
     * </pre>
     * <p>上記の例では、最初に"input > "と出力した後、
     * 数字のみ、またはローマ字のみで構成された文字列のみの
     * テキスト行を読み込みます。<br>
     * "123"や"abc"といった文字列は正規表現に一致するので読み込みますが、
     * "123abc"や"123$"のようなものは一致しないので空文字 "" が返却されます。</p>
     *
     * @param inMsg    出力するメッセージ
     * @param patterns 読み込んだテキスト行との一致を判定する正規表現
     * @exception java.util.regex.PatternSyntaxException
     *                 引数に与えられた正規表現の構文が無効な場合
     *
     * @return         読み込んだテキスト行が指定された正規表現とひとつでも一致すれば
     *                 そのテキスト行。
     *                 すべて一致しなければ空文字 ""
     */
    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readCheckedStr(String inMsg, String... patterns) {
        /* String型で与えられた正規表現をPattern型配列に変換する */
        Stream<String> stream = Arrays.stream(patterns);
        Pattern[] p = stream.map(Pattern::compile).toArray(Pattern[]::new);

        return readCheckedStr(inMsg, p);

    }

    /**
     * メッセージを出力し、正規表現に一致するテキスト行を読み込もうとします。
     * <p>基本的な動作は{@link #readCheckedStr(String, String...)}と同じです。<br>
     * 違いは正規表現をString型で指定するかPattern型で指定するかのみです。
     *
     * <p>処理の詳細は{@link #readCheckedStr(String, String...)}を見てください。</p>
     *
     * @param inMsg    出力するメッセージ
     * @param patterns 読み込んだテキスト行との一致を判定する正規表現
     * @return         読み込んだテキスト行が指定された正規表現とひとつでも一致すれば
     *                 そのテキスト行。
     *                 すべて一致しなければ空文字 ""
     */
    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readCheckedStr(String inMsg, Pattern... patterns) {
        String s = readStr(inMsg);

        boolean flag = checkPatternMatch(s, patterns);

        if (flag) {
            return s;
        }
        else {
            return "";
        }
    }

    /**
     * メッセージ付きで正規表現に一致したテキスト行が見つかるまで読み込みを繰り返します。
     * <p>{@link #readCheckedStr(String, String...)}の動作に加え、
     * 正規表現に一致するテキスト行ではなかった場合、読み込む前に出力したメッセージとは
     * 別にエラーメッセージが出力され、さらに一行読み込みます。<br>
     * これを一致する行が見つかるまで繰り返します。
     * メッセージは行を読み込むたびに繰り返し表示されます。</p>
     *
     * <p>以下、使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readStrUntilMatch("input > ",
     *                                        "error try again.",
     *                                        "[0-9]+",
     *                                        "[a-zA-Z]+");
     * </pre>
     *
     * <p>上記の例では、最初に"input > "と出力した後、テキスト行を読み込み
     * 指定された正規表現と一致しているか検証します。
     * 一致していればテキスト行が返却され、不一致の場合はエラーメッセージを出力し、
     * 再度 "input > "と出力した後、テキスト行を読み込みます。
     * これを正規表現に一致するまで繰り返します。<br>
     * この例の場合は、数字のみで構成されたテキスト行かアルファベットのみで
     * 構成されたテキスト行の場合、正規表現と一致します。</p>
     *
     * @param inMsg    読み込みを実行するまえに出力するメッセージ
     * @param errMsg   正規表現に一致してない行を読み込んだ場合に出力するメッセージ
     * @param patterns 読み込んだテキスト行との一致を判定する正規表現
     * @exception java.util.regex.PatternSyntaxException
     *                 引数に与えられた正規表現の構文が無効な場合
     *
     * @return         読み込んだテキスト行が指定された正規表現とひとつでも一致すれば
     *                 そのテキスト行。
     *                 すべて一致しなければ空文字 ""
     */
    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readStrUntilMatch(String inMsg, String errMsg, String... patterns) {
        Stream<String> stream = Arrays.stream(patterns);
        Pattern[] p = stream.map(Pattern::compile).toArray(Pattern[]::new);

        return readStrUntilMatch(inMsg, errMsg, p);
    }

    /**
     * メッセージ付きで正規表現に一致したテキスト行が見つかるまで読み込みを繰り返します。
     * <p>基本的な動作は{@link #readStrUntilMatch(String, String, String...)}と同じです。<br>
     * 違いは正規表現をString型で指定するかPattern型で指定するかのみです。
     *
     * <p>処理の詳細は{@link #readStrUntilMatch(String, String, String...)}を見てください。</p>
     *
     * @param inMsg    読み込みを実行するまえに出力するメッセージ
     * @param errMsg   正規表現に一致してない行を読み込んだ場合に出力するメッセージ
     * @param patterns 読み込んだテキスト行との一致を判定する正規表現
     * @return         読み込んだテキスト行が指定された正規表現とひとつでも一致すれば
     *                 そのテキスト行。
     *                 すべて一致しなければ空文字 ""
     */
    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readStrUntilMatch(String inMsg, String errMsg, Pattern... patterns) {
        while (true) {
            String s = readCheckedStr(inMsg, patterns);

            if (!s.isEmpty()) {
                return s;
            }
            else {
                System.err.println(errMsg);
            }
        }
    }

    /* 引数の文字列が引数で与えられたPattern型配列に格納されている正規表現と
     * 一致するかを検証するメソッドです。
     * 呼び出し元メソッドが与えられた正規表現に読み込んだテキスト行が
     * 一致しているかを調べるときにこのメソッドは呼ばれます。
     *
     * Pattern型配列の要素のすべての正規表現に対して、1つでも一致すればtrueが
     * 返却され、すべてに不一致だった場合はfalseが返却されます。
     */
    private boolean checkPatternMatch(String s, Pattern[] patterns) {
        for (Pattern p : patterns) {
            Matcher m = p.matcher(s);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }
    /* ------------------------------------------------------ */

    /* -------------------- INTEGER INPUT -------------------- */
    public int readInt() {
        return readInt("");
    }

    public int readInt(String inMsg) {
        String s = readStr(inMsg);
        if (s.matches("[0-9]+|-[0-9]+") && checkInIntRange(s)) {
            return Integer.parseInt(s);
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    public int readInt(String inMsg, String errMsg) {
        while (true) {
            try {
                return readInt(inMsg);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    private boolean checkInIntRange(String s) {
        if (s.matches("[0-9]+")) {
            if (s.length() > 10) {
                return false;
            }
            else if (s.length() < 10) {
                return true;
            }
            else {
                String[] ss = s.split("");
                for (int i = 0; i < intMaxValue.length; i++ ) {
                    if (Byte.parseByte(ss[i]) > intMaxValue[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        else {
            if (s.length() > 11) {
                return false;
            }
            else if (s.length() < 11) {
                return true;
            }
            else {
                s = s.replace("-", "");
                String[] ss = s.split("");
                for (int i = 0; i < intMinValue.length; i++ ) {
                    if (Byte.parseByte(ss[i]) > intMinValue[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @SafeVarargs
    public final int readCheckedInt(String inMsg, Predicate<Integer>... pred) {
        boolean flag = true;

        int i = readInt(inMsg);

        for (Predicate<Integer> pre : pred) {
            if (!pre.test(i)) {
                flag = false;
            }
        }
        if (flag) {
            return i;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }


    @SafeVarargs
    public final int readCheckedInt(String inMsg, String errMsg, Predicate<Integer>... pred) {
        boolean flag = true;

        while (true) {
            try {
                int i = readInt(inMsg);

                for (Predicate<Integer> pre : pred) {
                    if (!pre.test(i)) {
                        flag = false;
                    }
                }
                if (flag) {
                    return i;
                }
                else {
                    System.err.println(errMsg);
                }
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }
    /* ------------------------------------------------------- */

    public void setIOErrMsg(String msg) {
        IOErrMsg = msg;
    }
}
