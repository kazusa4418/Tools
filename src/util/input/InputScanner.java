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
 * <p>具体的にはｷｰﾎﾞｰﾄﾞから入力をする際に入力を受け付けるまえにテキストを表示したり、
 * 正規表現に一致したテキスト行のみを受け付ける、一致しなかった場合エラーメッセージを
 * 表示して再入力を求めるといった処理を簡単に実現することができます。</p>
 *
 * <p>入力された文字列をint型に変換するメソッドも実装されています。
 * このメソッドも同じく、テキストつきで入力を求めたり、関数型インターフェースや
 * ラムダ式、メソッド参照を用いて読み込みを制限することが可能です。</p>
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
 * <p>また、{@code InputScanner}の{@link #setIOErrMsg(String)}を除く、すべてのメソッドは
 * {@link IOException}を送出する恐れがあります。<br>
 * {@link #setIOErrMsg(String)}で、発生した際のエラーメッセージをあらかじめ
 * 設定しておくことを推奨します。<br>
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
     * これが送出されると読み込みをいったん取り消し、再入力を試行します。</p>
     *
     *
     * <p>以下、標準入力からテキスト行を読み込む使用例です。</p>
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
     * <p>以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readStr({@code "input > "});
     * </pre>
     * 上記の処理を{@link #readStr()}で実装した場合が下記のコードです。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.our.print({@code "input > "});
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
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readCheckedStr({@code "input > "},
     *                                     "[0-9]+",
     *                                     "[a-zA-Z]+");
     *
     * </pre>
     * <p>上記の例では、最初に{@code "input > "}と出力した後、
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
     * <p>処理の詳細は{@link #readCheckedStr(String, String...)}を参照してください。</p>
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

        /* 読み込んだ文字列が正規表現に一致しているか検証する */
        boolean flag = checkPatternMatch(s, patterns);

        /* 一致していればtrue 不一致ならばfalse */
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
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     String text = is.readStrUntilMatch({@code "input > "},
     *                                        "error try again.",
     *                                        "[0-9]+",
     *                                        "[a-zA-Z]+");
     * </pre>
     *
     * <p>上記の例では、最初に{@code "input > "}と出力した後、テキスト行を読み込み
     * 指定された正規表現と一致しているか検証します。
     * 一致していればテキスト行が返却され、不一致の場合はエラーメッセージを出力し、
     * 再度 {@code "input > "}と出力した後、テキスト行を読み込みます。
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
        /* String型で与えられた正規表現をPattern型配列に変換 */
        Stream<String> stream = Arrays.stream(patterns);
        Pattern[] p = stream.map(Pattern::compile).toArray(Pattern[]::new);

        return readStrUntilMatch(inMsg, errMsg, p);
    }

    /**
     * メッセージ付きで正規表現に一致したテキスト行が見つかるまで読み込みを繰り返します。
     * <p>基本的な動作は{@link #readStrUntilMatch(String, String, String...)}と同じです。<br>
     * 違いは正規表現をString型で指定するかPattern型で指定するかのみです。
     *
     * <p>処理の詳細は{@link #readStrUntilMatch(String, String, String...)}を参照してください。</p>
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
            /* 正規表現に一致しているか検査つきでテキスト行を読み込む */
            String s = readCheckedStr(inMsg, patterns);

            /* 不一致ならば空文字が返却されるのでisEmpty()で空文字が調べる */
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

    /**
     * テキスト行を読み込み、それをint型として解釈します。
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>読み込まれたテキスト行がint型に構文解析可能だった場合のみ
     * int型に変換され返却されます。
     * もし、構文解析不可能だった場合は例外がスローされます。</p>
     *
     * <p>このメソッドは{@link IOException}を送出する可能性があります。
     * これが送出されると読み込みをいったん取り消し、再入力を試行します。</p>
     *
     * <p>以下、標準入力でint値を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     int i = is.readInt();
     * </pre>
     *
     * @exception IllegalDataFormatException
     *            読み込んだテキスト行をint型に構文解析できなかったとき
     *
     * @return    終端文字を除く行の内容をint型に構文解析したもの
     */
    public int readInt() {
        return readInt("");
    }

    /**
     * メッセージを出力した後、テキスト行を読み込み、それをint型として解釈します。
     * <p>このメソッドは{@link java.io.PrintStream#print(String)}を呼び出してから
     * {@link #readInt()}を呼び出すのと同じ動作をします。</p>
     *
     * <p>以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     int i = is.readInt({@code "input > "});
     * </pre>
     *
     * 上記のコードを{@link #readInt()}を用いて実装すると以下になります。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.out.println({@code "input > "});
     *     int i = is.readInt();
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @return      終端文字を除く行の内容をint型に構文解析したもの
     */
    public int readInt(String inMsg) {
        String s = readStr(inMsg);
        if (s.matches("[0-9]+|-[0-9]+") && checkInIntRange(s)) {
            return Integer.parseInt(s);
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージ付きでInteger正規表現に一致したテキスト行が見つかるまで
     * 読み込みを繰り返し、見つかったらそれをint型に構文解析します。
     *
     * <p>{@link #readInt(String)}の動作に加え、読み込んだテキスト行を
     * int型に構文解析できなかった場合、読み込む前に出力したメッセージとは
     * 別にエラーメッセージを表示し、さらに1行を読み込みます。
     * これを構文解析に成功するまで繰り返します。<br>
     * メッセージは読み込みを繰り返すたびに表示されます。</p>
     *
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     int i = is.readInt({@code "input > "}, "error try again.");
     * </pre>
     *
     * <p>上記の例では、まず {@code "input > "}と出力し、その後テキスト行を読みます。
     * 読み込んだテキスト行がInteger正規表現に一致すればint型に構文解析され、
     * 返却されます。
     * 一致しなかった場合は、エラーメッセージ "error try again." を出力し、
     * 再度 {@code "input > "}を出力してさらにテキスト行を読み込みます。<br>
     * これを繰り返します。</p>
     *
     * @param inMsg  テキスト行を読み込む前に出力するメッセージ
     * @param errMsg 読み込んだテキスト行をint型に構文解析できなかったときに
     *               出力するメッセージ
     *
     * @return       終端文字を除く行の内容をint型に構文解析したもの
     */
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

    /* 引数で与えられた数字のみで構成されたString型文字列がInt型に
     * 構文解析できるかどうか検査するメソッドです。
     * まず、正の値か負の値かを識別します。
     *
     * 正の値ならば10文字未満、負の値ならば11文字未満であれば
     * 構文解析が可能で11文字以上、12文字以上ならば構文解析が
     * 不可能です。
     *
     * そして正の値で10文字、負の値で11文字だった場合、
     * 1桁1桁の数を検証し、範囲を超えることが分かった場合は
     * falseをリターンします。
     *
     * すべての桁においてintegerの値の範囲を超えていなかった場合は
     * trueをリターンします。
     *
     */
    private boolean checkInIntRange(String s) {
        /* 正の値かどうかを検査する */
        if (s.matches("[0-9]+")) {
            /* 10文字より多ければ構文解析できない */
            if (s.length() > 10) {
                return false;
            }
            /* 10文字より少なければ構文解析可能 */
            else if (s.length() < 10) {
                return true;
            }
            /* 10文字ぴったりであれば1桁ずつ値を検証していく */
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
        /* 負の値の場合 */
        else {
            /* 11文字より多ければ構文解析できない */
            if (s.length() > 11) {
                return false;
            }
            /* 11文字より少なければ構文解析可能 */
            else if (s.length() < 11) {
                return true;
            }
            /* 11文字ぴったりであれば1桁ずつ値を検証していく */
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

    /**
     * メッセージ付きで指定した条件式に一致するint値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readInt(String)}を拡張します。
     * {@link #readInt(String)}を呼び出した上で、
     * 読み込まれた値が指定した条件式に一致しているか検査し、
     * 一致している場合はそのまま返却、一致していなかった場合は
     * 例外がスローされます。</p>
     *
     * <p>条件式は{@link Predicate}で指定します。<br>
     * {@link Predicate}を継承し、{@link Predicate#test(Object)}
     * メソッドをオーバーライドした独自のクラスを作るか、ラムダ式または
     * メソッド参照で条件を指定することが可能です。
     * </p>
     *
     * <p>以下、独自のクラスを作成して条件を指定したときの使用例です(標準入力)。</p>
     *
     * <pre>
     *     独自クラス : SubPredicate
     *     条件　　　 : 0 ~ 100までの値のみ読み込む
     *
     *     public class SubPredicate extends Predicate{@code <Integer>} {
     *         public boolean test(int x) {
     *             if ({@code x >= 0 && x <= 100}) {
     *                 return true;
     *             }
     *             else {
     *                 false;
     *             }
     *         }
     *     }
     * </pre>
     * <pre>
     *     呼び出し元クラス   : Test
     *     呼び出し元メソッド : main
     *
     *     public class Test {
     *         public static void main(String[] args) {
     *             InputScanner is = new InputScanner();
     *             SubPredicate sp = new SubPredicate();
     *
     *             int i = is.readCheckedInt({@code "input > ", sp});
     *         }
     *     }
     * </pre>
     *
     * <p>以下、ラムダ式で条件を指定したときの使用例です(標準入力)。</p>
     *
     * <pre>
     *     呼び出し元クラス   : Test
     *     呼び出し元メソッド : main
     *     条件　　　　　　　 : 0 ~ 100までの値のみ読み込む
     *
     *     public class Test {
     *         public static void main(String[] args) {
     *             InputScanner is = new InputScanner();
     *             int i = is.readCheckedInt({@code "input > ", x -> x >= 0 && x <= 100});
     *         }
     *     }
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @param pred  構文解析されたint値の範囲を制限する条件式
     * @exception IllegalDataFormatException
     *              指定したint値の範囲を満たしていない場合、
     *              読み込んだテキスト行がInteger正規表現を満たしていない場合
     *
     * @return      終端文字を除く行の内容を条件に一致した範囲のint型に構文解析したもの
     */
    @SafeVarargs
    public final int readCheckedInt(String inMsg, Predicate<Integer>... pred) {
        int i = readInt(inMsg);

        boolean flag = checkInCondition(i, pred);

        if (flag) {
            return i;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }


    /**
     * メッセージ付きで指定した条件式に一致するint値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readCheckedInt(String, Predicate[])}の
     * 処理を拡張します。
     * {@link #readCheckedInt(String, Predicate[])}を呼び出し、
     * 読み込んだint値が条件式を正しく満たした場合はその値をそのまま
     * 返却し、満たさず例外をスローした場合は、エラーメッセージを表示して
     * 再度{@link #readCheckedInt(String, Predicate[])}を呼び出します。<br>
     * これを読み込んだint値が条件式を満たすまで繰り返します。</p>
     *
     * <p>使用例は{@link #readCheckedInt(String, Predicate[])}を参照してください。</p>
     *
     * @param inMsg  読み込む前に出力されるメッセージ
     * @param errMsg 読み込んだテキスト行がInteger正規表現を満たしていないか、
     *               読み込んだint値が範囲を満たしていないとき
     *
     * @param pred   構文解析されたint値の範囲を制限する条件式
     * @return      終端文字を除く行の内容を条件に一致した範囲のint型に構文解析したもの
     */
    @SafeVarargs
    public final int readCheckedInt(String inMsg, String errMsg, Predicate<Integer>... pred) {
        while (true) {
            try {
                int i = readInt(inMsg);

                boolean flag = checkInCondition(i, pred);
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

    /* 引数で与えらえたint値が指定されたPredicateの条件式に一致するか検査します。
     * Predicate配列として受け取ったpredからひとつずつ要素を取り出し、
     * testメソッドを使って検証していきます。
     * ひとつでも一致する条件式があればtrueを返却し、すべてに一致しなければ
     * falseを返却します。*/

    private boolean checkInCondition(int i, Predicate<Integer>[] pred) {
        for (Predicate<Integer> p : pred) {
            if (p.test(i)) {
                return true;
            }
        }
        return false;
    }
    /* ------------------------------------------------------- */

    /**
     * {@link IOException}を送出したときに出力するエラーメッセージを変更します。
     * <p>{@code InputScanner}のこのメソッドを除くすべてのメソッドは
     * {@link IOException}を送出する恐れがあります。</p>
     *
     * <p>クライアントはこのメソッドを用いてエラーメッセージをセットしておくことで
     * 実際に{@link IOException}が発生したときのエラーメッセージを任意に
     * 設定することができます。</p>
     *
     * @param msg {@link IOException}を送出したときに出力するエラーメッセージ
     */
    public void setIOErrMsg(String msg) {
        IOErrMsg = msg;
    }
}
