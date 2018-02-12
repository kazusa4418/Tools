package util.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/* 2018/2/11 :
 *     とりあえず使える状態にするという考えで実装。
 *     クラスの出来としては最低になっているので必ずリファクタリングをすること。
 *     特にメソッド名、引数の数などかなりひどい出来になっているし、そもそもいらないメソッドも
 *     きっと残っているはず。
 *     TODOは必ずすべて消化すること。
 */

//TODO: メソッド名の最適化
//TODO: IllegalDataFormatExceptionで例外内容がわかるように再実装
//TODO: いらないメソッド、必要なメソッドの選別
//TODO: 全体的なコードのリファクタリング
//TODO: ボクシングされたデータ型のparseメソッドを使用しないアルゴリズムを自分で実装する
//TODO: byte, short, float型入力を実装する

/**
 * 文字型入力ストリームから効率よくテキスト行を読み込みます。
 * <p>デフォルトではストリームの接続先はｷｰﾎﾞｰﾄﾞとなり、標準入力を提供します。</p>
 * <p>デフォルトの{@code InputScanner}は引数に{@code InputStreamReader(System.in)}
 * を与えた{@code BufferedReader}と似た使い方ができますが、より実用的な使用ができます。</p>
 *
 * <pre>
 * *現状では標準入力しか正確に動作しません。
 *     {@link #InputScanner(Reader)}を用いて標準入力以外に接続した場合、
 *     想定外の動作をする恐れがあります。
 *     今後実装予定ですが、現状使用しないでください。
 * </pre>
 *
 * <p>具体的にはｷｰﾎﾞｰﾄﾞから入力をする際に入力を受け付けるまえにテキストを表示したり、
 * 正規表現に一致したテキスト行のみを受け付ける、一致しなかった場合エラーメッセージを
 * 表示して再入力を求めるといった処理を簡単に実現することができます。</p>
 *
 * <p>入力された文字列を様々な型に変換して読み込むメソッドも実装されています。
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
 *     {@link #readIntUntilMatch(String, String, Predicate[])}
 *
 *     {@link #readLong(String, String)}
 *     {@link #readLongUntilMatch(String, String, Predicate[])}
 *
 *     {@link #readDouble(String, String)}
 *     {@link #readDoubleUntilMatch(String, String, Predicate[])}
 *
 *     {@link #readChar(String, String)}
 *     {@link #readCharUntilMatch(String, String, Predicate[])}
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
    /* long型の最大値と最小値の絶対値 */
    private static final byte[] longMaxValue = { 9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 7 };
    private static final byte[] longMinValue = { 9, 2, 2, 3, 3, 7, 2, 0, 3, 6, 8, 5, 4, 7, 7, 5, 8, 0, 8 };

    /**
     * 標準入力に接続された文字型ストリームを作成します。
     * この文字型ストリームはバッファサイズ8192バイトでバッファリングされています。
     *
     * @since 1.0
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
     *
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
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
     *
     * @since 1.0
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
     *
     * @since 1.0
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
     *
     * @since 1.0
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
     *
     * @since 1.0
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
     * @since 1.0
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
     * @since 1.0
     */
    public int readInt(String inMsg) {
        String s = readStr(inMsg);
        if (s.matches("[+-]?[0-9]+") && checkInIntRange(s)) {
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
     * @since 1.0
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

    /* 引数で与えられたプラス記号とマイナス記号、または数字のみで構成された
     * String型文字列がInt型に構文解析できるかどうか検査するメソッドです。
     *
     * まず、正の値か負の値か識別します。
     *
     * その後、正負記号を取り除き文字数を確認します。
     * 11文字以上ならばInt型に構文解析することは不可能です。
     * 9文字以下ならばInt型に構文解析可能です。
     *
     * 10文字ちょうどであったならば一桁ずつ値を検証していきます。
     *
     * その結果、1桁でもInt型の範囲外の数値が存在すればその時点で
     * falseが返却されます。
     *
     * すべての桁においてInt型の値の範囲を超えていなかった場合は
     * trueを返却します。
     *
     */
    private boolean checkInIntRange(String s) {
        s = deleteFront0(s);
        /* 正負を確認する */
        /* 正の値の場合 */
        if (s.charAt(0) == '-') {
            return intRangeCheck(s, "-", intMinValue);
        }
        /* 正の値の場合 */
        else {
            return intRangeCheck(s, "+", intMaxValue);
        }
    }

    private boolean intRangeCheck(String s, String ch, byte[] intValue) {
        /* 正負記号を削除する */
        s = s.replace(ch, "");

        /* 10桁より多ければ構文解析できない */
        if (s.length() > 10) {
            return false;
        }
        /* 10桁より少なければ構文解析可能 */
        else if (s.length() < 10) {
            return true;
        }
        /* 10桁ぴったりであれば1桁ずつ値を検証していく */
        else {
            String[] ss = s.split("");
            for (int i = 0; i < intValue.length; i++ ) {
                if (Byte.parseByte(ss[i]) > intValue[i]) {
                    return false;
                }
            }
            return true;
        }
    }


    /*
     * 引数で与えられた文字列から先頭の0をすべて取り除きます。
     *
     * 例えば "+000450"という値ならば"+450"という値に、
     * "-000450"という値ならば"-450"に、"000450"という
     * 値ならば"450"に変換します。
     */

    private String deleteFront0(String s) {
        /* 引数の文字列を一文字ずつcharとして格納するリスト */
        List<Character> list = new ArrayList<>();
        /* 引数の文字列を一文字ずつ取り出してリストに格納する */
        for (char c : s.toCharArray()) {
            list.add(c);
        }

        /* 先頭の文字が正負記号だった場合 */
        if (list.get(0) != '-' && list.get(0) != '+') {
            while (true) {
                if (list.get(0).equals('0')) {
                    list.remove(0);
                }
                else {
                    break;
                }
            }
            /* 与えられた引数が0のみで構成された文字列だった場合 */
            if (list.size() == 0) {
                list.add('0');
            }
        }
        /* 正負記号が存在しなかった場合 */
        else {
            while (true) {
                if (list.get(1).equals('0')) {
                    list.remove(1);
                }
                else {
                    break;
                }
            }
            /* 与えられた引数が正負記号と0のみで構成された文字列だった場合 */
            if (list.size() == 1) {
                list.add('0');
            }
        }
        /* 先頭の0の削除が終わったCharacter型リストをString型に変換して返却する */
        StringBuilder sb = new StringBuilder();
        for (Character c : list) {
            sb.append(c);
        }
        return sb.toString();
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
     * {@link Predicate}を実装し、{@link Predicate#test(Object)}
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
     *     public class SubPredicate implements Predicate{@code <Integer>} {
     *         public boolean test(Integer x) {
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
     * @since 1.0
     */
    @SafeVarargs
    public final int readCheckedInt(String inMsg, Predicate<Integer>... pred) {
        int i = readInt(inMsg);

        boolean flag = checkInIntCondition(i, pred);

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
     * @return       終端文字を除く行の内容を条件に一致した範囲のint型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final int readIntUntilMatch(String inMsg, String errMsg, Predicate<Integer>... pred) {
        while (true) {
            try {
                int i = readInt(inMsg);

                boolean flag = checkInIntCondition(i, pred);
                if (flag) {
                    return i;
                }
                else {
                    throw new IllegalDataFormatException();
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
     * falseを返却します。
     */

    private boolean checkInIntCondition(int i, Predicate<Integer>[] pred) {
        for (Predicate<Integer> p : pred) {
            if (p.test(i)) {
                return true;
            }
        }
        return false;
    }
    /* ------------------------------------------------------- */

    /* -------------------- LONG INPUT -------------------- */

    /**
     * テキスト行を読み込み、それをlong型として解釈します。
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>読み込まれたテキスト行がlong型に構文解析可能だった場合のみ
     * long型に変換され返却されます。
     * もし、構文解析不可能だった場合は例外がスローされます。</p>
     *
     * <p>このメソッドは{@link IOException}を送出する可能性があります。
     * これが送出されると読み込みをいったん取り消し、再入力を試行します。</p>
     *
     * <p>以下、標準入力でlong値を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     long l = is.readLong();
     * </pre>
     *
     * @exception IllegalDataFormatException
     *            読み込んだテキスト行をlong型に構文解析できなかったとき
     *
     * @return    終端文字を除く行の内容をlong型に構文解析したもの
     * @since 1.0
     */
    public long readLong() {
        return readLong("");
    }

    /**
     * メッセージを出力した後、テキスト行を読み込み、それをlong型として解釈します。
     * <p>このメソッドは{@link java.io.PrintStream#print(String)}を呼び出してから
     * {@link #readLong()}を呼び出すのと同じ動作をします。</p>
     *
     * <p>以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     long l = is.readLong({@code "input > "});
     * </pre>
     *
     * 上記のコードを{@link #readLong()}を用いて実装すると以下になります。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.out.println({@code "input > "});
     *     long l = is.readLong();
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @return      終端文字を除く行の内容をlong型に構文解析したもの
     * @since 1.0
     */
    public long readLong(String inMsg) {
        String s = readStr(inMsg);
        if (s.matches("[+-]?[0-9]+") && checkInLongRange(s)) {
            return Long.parseLong(s);
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージ付きでLong正規表現に一致したテキスト行が見つかるまで
     * 読み込みを繰り返し、見つかったらそれをlong型に構文解析します。
     *
     * <p>{@link #readLong(String)}の動作に加え、読み込んだテキスト行を
     * long型に構文解析できなかった場合、読み込む前に出力したメッセージとは
     * 別にエラーメッセージを表示し、さらに1行を読み込みます。
     * これを構文解析に成功するまで繰り返します。<br>
     * メッセージは読み込みを繰り返すたびに表示されます。</p>
     *
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     long i = is.readLong({@code "input > "}, "error try again.");
     * </pre>
     *
     * <p>上記の例では、まず {@code "input > "}と出力し、その後テキスト行を読みます。
     * 読み込んだテキスト行がLong正規表現に一致すればlong型に構文解析され、
     * 返却されます。
     * 一致しなかった場合は、エラーメッセージ "error try again." を出力し、
     * 再度 {@code "input > "}を出力してさらにテキスト行を読み込みます。<br>
     * これを繰り返します。</p>
     *
     * @param inMsg  テキスト行を読み込む前に出力するメッセージ
     * @param errMsg 読み込んだテキスト行をlong型に構文解析できなかったときに
     *               出力するメッセージ
     *
     * @return       終端文字を除く行の内容をlong型に構文解析したもの
     * @since 1.0
     */
    public long readLong(String inMsg, String errMsg) {
        while (true) {
            try {
                return readLong(inMsg);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /* 引数で与えられたプラス記号とマイナス記号、または数字のみで構成された
     * String型文字列がlong型に構文解析できるかどうか検査するメソッドです。
     *
     * まず、正の値か負の値か識別します。
     *
     * その後、正負記号を取り除き文字数を確認します。
     * 20文字以上ならばlong型に構文解析することは不可能です。
     * 18文字以下ならばlong型に構文解析可能です。
     *
     * 19文字ちょうどであったならば一桁ずつ値を検証していきます。
     *
     * その結果、1桁でもlong型の範囲外の数値が存在すればその時点で
     * falseが返却されます。
     *
     * すべての桁においてlong型の値の範囲を超えていなかった場合は
     * trueを返却します。
     *
     */
    private boolean checkInLongRange(String s) {
        s = deleteFront0(s);
        /* 正の値かどうかを検査する */
        /* 負の値の場合 */
        if (s.charAt(0) == '-') {
            return longRangeCheck(s, "-", longMinValue);
        }
        /* 正の値の場合 */
        else {
            return longRangeCheck(s, "+", longMaxValue);
        }
    }

    private boolean longRangeCheck(String s, String ch, byte[] longValue) {
        /* 正負記号を削除する */
        s = s.replace(ch, "");

        /* 19桁より多ければ構文解析できない */
        if (s.length() > 19) {
            return false;
        }
        /* 19桁より少なければ構文解析可能 */
        else if (s.length() < 19) {
            return true;
        }
        /* 19桁ぴったりであれば1桁ずつ値を検証していく */
        else {
            String[] ss = s.split("");
            for (int i = 0; i < longValue.length; i++ ) {
                if (Byte.parseByte(ss[i]) > longValue[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * メッセージ付きで指定した条件式に一致するlong値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readLong(String)}を拡張します。
     * {@link #readLong(String)}を呼び出した上で、
     * 読み込まれた値が指定した条件式に一致しているか検査し、
     * 一致している場合はそのまま返却、一致していなかった場合は
     * 例外がスローされます。</p>
     *
     * <p>条件式は{@link Predicate}で指定します。<br>
     * {@link Predicate}を実装し、{@link Predicate#test(Object)}
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
     *     public class SubPredicate implements Predicate{@code <Long>} {
     *         public boolean test(long x) {
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
     *             long l = is.readCheckedLong({@code "input > ", sp});
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
     *             long l = is.readCheckedLong({@code "input > ", x -> x >= 0 && x <= 100});
     *         }
     *     }
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @param pred  構文解析されたlong値の範囲を制限する条件式
     * @exception IllegalDataFormatException
     *              指定したlong値の範囲を満たしていない場合、
     *              読み込んだテキスト行がLong正規表現を満たしていない場合
     *
     * @return      終端文字を除く行の内容を条件に一致した範囲のlong型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final long readCheckedLong(String inMsg, Predicate<Long>... pred) {
        long l = readLong(inMsg);

        boolean flag = checkInLongCondition(l, pred);
        if (flag) {
            return l;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージ付きで指定した条件式に一致するlong値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readCheckedLong(String, Predicate[])}の
     * 処理を拡張します。
     * {@link #readCheckedLong(String, Predicate[])}を呼び出し、
     * 読み込んだlong値が条件式を正しく満たした場合はその値をそのまま
     * 返却し、満たさず例外をスローした場合は、エラーメッセージを表示して
     * 再度{@link #readCheckedLong(String, Predicate[])}を呼び出します。<br>
     * これを読み込んだlong値が条件式を満たすまで繰り返します。</p>
     *
     * <p>使用例は{@link #readCheckedLong(String, Predicate[])}を参照してください。</p>
     *
     * @param inMsg  読み込む前に出力されるメッセージ
     * @param errMsg 読み込んだテキスト行がLong正規表現を満たしていないか、
     *               読み込んだlong値が範囲を満たしていないとき
     *
     * @param pred   構文解析されたint値の範囲を制限する条件式
     * @return       終端文字を除く行の内容を条件に一致した範囲のlong型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final long readLongUntilMatch(String inMsg, String errMsg, Predicate<Long>... pred)  {
        while (true) {
            try {
                long l = readLong(inMsg);

                boolean flag = checkInLongCondition(l, pred);
                if (flag) {
                    return l;
                }
                else {
                    throw new IllegalDataFormatException();
                }
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /* 引数で与えらえたlong値が指定されたPredicateの条件式に一致するか検査します。
     * Predicate配列として受け取ったpredからひとつずつ要素を取り出し、
     * testメソッドを使って検証していきます。
     * ひとつでも一致する条件式があればtrueを返却し、すべてに一致しなければ
     * falseを返却します。
     */

    private boolean checkInLongCondition(long l, Predicate<Long>[] pred) {
        for (Predicate<Long> p : pred) {
            if (p.test(l)) {
                return true;
            }
        }
        return false;
    }
    /* ---------------------------------------------------- */

    /* -------------------- CHAR INPUT -------------------- */

    /**
     * テキスト行を読み込み、それをchar型として解釈します。
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>読み込まれたテキスト行がchar型に構文解析可能だった場合のみ
     * char型に変換され返却されます。
     * もし、構文解析不可能だった場合は例外がスローされます。</p>
     *
     * <p>このメソッドは{@link IOException}を送出する可能性があります。
     * これが送出されると読み込みをいったん取り消し、再入力を試行します。</p>
     *
     * <p>以下、標準入力でchar値を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     char c = is.readChar();
     * </pre>
     *
     * @exception IllegalDataFormatException
     *            読み込んだテキスト行をchar型に構文解析できなかったとき
     *
     * @return    終端文字を除く行の内容をchar型に構文解析したもの
     * @since 1.0
     */
    public char readChar() {
        String s = readStr();

        char[] c = s.toCharArray();
        if (c.length == 1) {
            return c[0];
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージを出力した後、テキスト行を読み込み、それをchar型として解釈します。
     * <p>このメソッドは{@link java.io.PrintStream#print(String)}を呼び出してから
     * {@link #readChar()}を呼び出すのと同じ動作をします。</p>
     *
     * <p>以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     char c = is.readChar({@code "input > "});
     * </pre>
     *
     * 上記のコードを{@link #readChar()}を用いて実装すると以下になります。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.out.println({@code "input > "});
     *     char c = is.readChar();
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @return      終端文字を除く行の内容をchar型に構文解析したもの
     * @since 1.0
     */
    public char readChar(String inMsg) {
        System.out.print(inMsg);
        return readChar();
    }

    /**
     * メッセージ付きでCharacter正規表現に一致したテキスト行が見つかるまで
     * 読み込みを繰り返し、見つかったらそれをchar型に構文解析します。
     *
     * <p>{@link #readChar(String)}の動作に加え、読み込んだテキスト行を
     * char型に構文解析できなかった場合、読み込む前に出力したメッセージとは
     * 別にエラーメッセージを表示し、さらに1行を読み込みます。
     * これを構文解析に成功するまで繰り返します。<br>
     * メッセージは読み込みを繰り返すたびに表示されます。</p>
     *
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     char c = is.readChar({@code "input > "}, "error try again.");
     * </pre>
     *
     * <p>上記の例では、まず {@code "input > "}と出力し、その後テキスト行を読みます。
     * 読み込んだテキスト行がLong正規表現に一致すればlong型に構文解析され、
     * 返却されます。
     * 一致しなかった場合は、エラーメッセージ "error try again." を出力し、
     * 再度 {@code "input > "}を出力してさらにテキスト行を読み込みます。<br>
     * これを繰り返します。</p>
     *
     * @param inMsg  テキスト行を読み込む前に出力するメッセージ
     * @param errMsg 読み込んだテキスト行をchar型に構文解析できなかったときに
     *               出力するメッセージ
     *
     * @return       終端文字を除く行の内容をchar型に構文解析したもの
     * @since 1.0
     */
    public char readChar(String inMsg, String errMsg) {
        while (true) {
            try {
                return readChar(inMsg);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /**
     * メッセージ付きで指定した条件式に一致するchar値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readChar(String)}を拡張します。
     * {@link #readChar(String)}を呼び出した上で、
     * 読み込まれた値が指定した条件式に一致しているか検査し、
     * 一致している場合はそのまま返却、一致していなかった場合は
     * 例外がスローされます。</p>
     *
     * <p>条件式は{@link Predicate}で指定します。<br>
     * {@link Predicate}を実装し、{@link Predicate#test(Object)}
     * メソッドをオーバーライドした独自のクラスを作るか、ラムダ式または
     * メソッド参照で条件を指定することが可能です。
     * </p>
     *
     * <p>以下、独自のクラスを作成して条件を指定したときの使用例です(標準入力)。</p>
     *
     * <pre>
     *     独自クラス : SubPredicate
     *     条件　　　 : 'a'または'A','b','B'の入力のみ
     *
     *     public class SubPredicate implements Predicate{@code <Character>} {
     *         public boolean test(Character c) {
     *             if ({@code c == 'a' || c == 'A' || c == 'b' || c == 'B'}) {
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
     *             char c = is.readCheckedChar({@code "input > ", sp});
     *         }
     *     }
     * </pre>
     *
     * <p>以下、ラムダ式で条件を指定したときの使用例です(標準入力)。</p>
     *
     * <pre>
     *     呼び出し元クラス   : Test
     *     呼び出し元メソッド : main
     *     条件　　　　　　　 : 'a'または'A','b','B'の入力のみ
     *
     *     public class Test {
     *         public static void main(String[] args) {
     *             InputScanner is = new InputScanner();
     *             char c = is.readCheckedChar({@code "input > ",
     *                                              c == 'a' || c == 'A' || c == 'b' || c == 'B'});
     *         }
     *     }
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @param pred  構文解析されたchar値の範囲を制限する条件式
     * @exception IllegalDataFormatException
     *              指定したchar値の範囲を満たしていない場合、
     *              読み込んだテキスト行がCharacter正規表現を満たしていない場合
     *
     * @return      終端文字を除く行の内容を条件に一致した範囲のchar型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final char readCheckedChar(String inMsg, Predicate<Character>... pred) {
        char c = readChar(inMsg);

        boolean flag = checkInCharCondition(c, pred);
        if (flag) {
            return c;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージ付きで指定した条件式に一致するchar値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readCheckedChar(String, Predicate[])}の
     * 処理を拡張します。
     * {@link #readCheckedChar(String, Predicate[])}を呼び出し、
     * 読み込んだchar値が条件式を正しく満たした場合はその値をそのまま
     * 返却し、満たさず例外をスローした場合は、エラーメッセージを表示して
     * 再度{@link #readCheckedChar(String, Predicate[])}を呼び出します。<br>
     * これを読み込んだchar値が条件式を満たすまで繰り返します。</p>
     *
     * <p>使用例は{@link #readCheckedChar(String, Predicate[])}を参照してください。</p>
     *
     * @param inMsg  読み込む前に出力されるメッセージ
     * @param errMsg 読み込んだテキスト行がCharacter正規表現を満たしていないか、
     *               読み込んだchar値が範囲を満たしていないとき
     *
     * @param pred   構文解析されたchar値の範囲を制限する条件式
     * @return       終端文字を除く行の内容を条件に一致した範囲のchar型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final char readCharUntilMatch(String inMsg, String errMsg, Predicate<Character>... pred) {
        while (true) {
            try {
                return readCheckedChar(inMsg, pred);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /* 引数で与えらえたchar値が指定されたPredicateの条件式に一致するか検査します。
     * Predicate配列として受け取ったpredからひとつずつ要素を取り出し、
     * testメソッドを使って検証していきます。
     * ひとつでも一致する条件式があればtrueを返却し、すべてに一致しなければ
     * falseを返却します。
     */

    @SafeVarargs
    private final boolean checkInCharCondition(char c, Predicate<Character>... pred) {
        for (Predicate<Character> p : pred) {
            if (p.test(c)) {
                return true;
            }
        }
        return false;
    }
    /* ---------------------------------------------------- */

    /* -------------------- DOUBLE INPUT -------------------- */

    /**
     * テキスト行を読み込み、それをdouble型として解釈します。
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>読み込まれたテキスト行がdouble型に構文解析可能だった場合のみ
     * double型に変換され返却されます。
     * もし、構文解析不可能だった場合は例外がスローされます。</p>
     *
     * <p>このメソッドは{@link IOException}を送出する可能性があります。
     * これが送出されると読み込みをいったん取り消し、再入力を試行します。</p>
     *
     * <p>以下、標準入力でdouble値を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     double d = is.readDouble();
     * </pre>
     *
     * @exception IllegalDataFormatException
     *            読み込んだテキスト行をdouble型に構文解析できなかったとき
     *
     * @return    終端文字を除く行の内容をdouble型に構文解析したもの
     * @since 1.0
     */
    //TODO: DOUBLE型の変換アルゴリズムを自分で考えて実装する
    public double readDouble() {
        String s = readStr();

        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException e) {
            //TODO: 例外を握りつぶしているので違うアルゴリズムに変換するか例外情報を引きつぐ
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージを出力した後、テキスト行を読み込み、それをdouble型として解釈します。
     * <p>このメソッドは{@link java.io.PrintStream#print(String)}を呼び出してから
     * {@link #readDouble()}を呼び出すのと同じ動作をします。</p>
     *
     * <p>以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     double d = is.readDouble({@code "input > "});
     * </pre>
     *
     * 上記のコードを{@link #readDouble()}を用いて実装すると以下になります。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.out.println({@code "input > "});
     *     double d = is.readDouble();
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @return      終端文字を除く行の内容をdouble型に構文解析したもの
     * @since 1.0
     */
    public double readDouble(String inMsg) {
        System.out.print(inMsg);
        return readDouble();
    }

    /**
     * メッセージ付きでDouble正規表現に一致したテキスト行が見つかるまで
     * 読み込みを繰り返し、見つかったらそれをdouble型に構文解析します。
     *
     * <p>{@link #readDouble(String)}の動作に加え、読み込んだテキスト行を
     * double型に構文解析できなかった場合、読み込む前に出力したメッセージとは
     * 別にエラーメッセージを表示し、さらに1行を読み込みます。
     * これを構文解析に成功するまで繰り返します。<br>
     * メッセージは読み込みを繰り返すたびに表示されます。</p>
     *
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     double d = is.readDouble({@code "input > "}, "error try again.");
     * </pre>
     *
     * <p>上記の例では、まず {@code "input > "}と出力し、その後テキスト行を読みます。
     * 読み込んだテキスト行がDouble正規表現に一致すればdouble型に構文解析され、
     * 返却されます。
     * 一致しなかった場合は、エラーメッセージ "error try again." を出力し、
     * 再度 {@code "input > "}を出力してさらにテキスト行を読み込みます。<br>
     * これを繰り返します。</p>
     *
     * @param inMsg  テキスト行を読み込む前に出力するメッセージ
     * @param errMsg 読み込んだテキスト行をdouble型に構文解析できなかったときに
     *               出力するメッセージ
     *
     * @return       終端文字を除く行の内容をdouble型に構文解析したもの
     * @since 1.0
     */
    public double readDouble(String inMsg, String errMsg) {
        while (true) {
            try {
                return readDouble(inMsg);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /**
     * メッセージ付きで指定した条件式に一致するdouble値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readDouble(String)}を拡張します。
     * {@link #readDouble(String)}を呼び出した上で、
     * 読み込まれた値が指定した条件式に一致しているか検査し、
     * 一致している場合はそのまま返却、一致していなかった場合は
     * 例外がスローされます。</p>
     *
     * <p>条件式は{@link Predicate}で指定します。<br>
     * {@link Predicate}を実装し、{@link Predicate#test(Object)}
     * メソッドをオーバーライドした独自のクラスを作るか、ラムダ式または
     * メソッド参照で条件を指定することが可能です。
     * </p>
     *
     * <p>以下、独自のクラスを作成して条件を指定したときの使用例です(標準入力)。</p>
     *
     * <pre>
     *     独自クラス : SubPredicate
     *     条件　　　 : 3.14以上の値のみ
     *
     *     public class SubPredicate implements Predicate{@code <Double>} {
     *         public boolean test(Double d) {
     *             if ({@code d >= 3.14}) {
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
     *             double d = is.readCheckedDouble({@code "input > ", sp});
     *         }
     *     }
     * </pre>
     *
     * <p>以下、ラムダ式で条件を指定したときの使用例です(標準入力)。</p>
     *
     * <pre>
     *     呼び出し元クラス   : Test
     *     呼び出し元メソッド : main
     *     条件　　　　　　　 : 'a'または'A','b','B'の入力のみ
     *
     *     public class Test {
     *         public static void main(String[] args) {
     *             InputScanner is = new InputScanner();
     *             double d = is.readCheckedDouble({@code "input > ", x -> x >= 3.14});
     *         }
     *     }
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @param pred  構文解析されたchar値の範囲を制限する条件式
     * @exception IllegalDataFormatException
     *              指定したdouble値の範囲を満たしていない場合、
     *              読み込んだテキスト行がDouble正規表現を満たしていない場合
     *
     * @return      終端文字を除く行の内容を条件に一致した範囲のdouble型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final double readCheckedDouble(String inMsg, Predicate<Double>... pred) {
        double d = readDouble(inMsg);

        boolean flag = checkInDoubleCondition(d, pred);

        if (flag) {
            return d;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * メッセージ付きで指定した条件式に一致するdouble値に構文解析が可能な
     * テキスト行が見つかるまで読み込みを繰り返します。
     *
     * <p>このメソッドは{@link #readCheckedDouble(String, Predicate[])}の
     * 処理を拡張します。
     * {@link #readCheckedDouble(String, Predicate[])}を呼び出し、
     * 読み込んだdouble値が条件式を正しく満たした場合はその値をそのまま
     * 返却し、満たさず例外をスローした場合は、エラーメッセージを表示して
     * 再度{@link #readCheckedDouble(String, Predicate[])}を呼び出します。<br>
     * これを読み込んだdouble値が条件式を満たすまで繰り返します。</p>
     *
     * <p>使用例は{@link #readCheckedDouble(String, Predicate[])}を参照してください。</p>
     *
     * @param inMsg  読み込む前に出力されるメッセージ
     * @param errMsg 読み込んだテキスト行がDouble正規表現を満たしていないか、
     *               読み込んだdouble値が範囲を満たしていないとき
     *
     * @param pred   構文解析されたdouble値の範囲を制限する条件式
     * @return       終端文字を除く行の内容を条件に一致した範囲のdouble型に構文解析したもの
     * @since 1.0
     */
    @SafeVarargs
    public final double readDoubleUntilMatch(String inMsg, String errMsg, Predicate<Double>... pred) {
        while (true) {
            try {
                return readCheckedDouble(inMsg, pred);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /* 引数で与えらえたchar値が指定されたPredicateの条件式に一致するか検査します。
     * Predicate配列として受け取ったpredからひとつずつ要素を取り出し、
     * testメソッドを使って検証していきます。
     * ひとつでも一致する条件式があればtrueを返却し、すべてに一致しなければ
     * falseを返却します。
     */

    @SafeVarargs
    private final boolean checkInDoubleCondition(double d, Predicate<Double>... pred) {
        for (Predicate<Double> p : pred) {
            if (p.test(d)) {
                return true;
            }
        }
        return false;
    }


    /* 未使用 */
    private int dotCount(String s) {
        char[] cc = s.toCharArray();

        int count = 0;
        for (char c : cc) {
            if (c == '.') {
                count++;
            }
        }
        return count;
    }

    /* ------------------------------------------------------ */

    /* -------------------- BOOLEAN INPUT -------------------- */

    /* 読み込まれた文字列が引数で与えられた２つの正規表現に一致した場合に
     * 対応するbooleanを返却するメソッド。
     * truePtに一致した場合はtrueを、falsePtに一致した場合はfalseを
     * 返却する。
     * 文字列がtruePt,falsePtの両方を満たした場合、trueが優先的に
     * 返却される。
     */
    private boolean readBoo(Pattern truePt, Pattern falsePt) {
        String s = readStr();

        Matcher m_1 = truePt.matcher(s);
        Matcher m_2 = falsePt.matcher(s);

        if (m_1.matches()) {
            return true;
        }
        else if (m_2.matches()) {
            return false;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    /**
     * テキスト行を読み込み、それが"true"または"false"(大文字小文字は区別しない)
     * に一致した場合に対応するbooleanを返却します。
     *
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>読み込まれたテキスト行が大文字、小文字区別せずに
     * "true"または"false"と一致した場合にのみそれぞれの文字に対応した
     * booleanを返却します。<br>
     * つまり、"true","True","tRue","trUe","truE"や"TRUE"などはすべて
     * boolean : true として認識され、返却されます。<br>
     * falseの場合も同様です。<br>
     * もし、一致しなかった場合は例外がスローされます。</p>
     *
     * <p>このメソッドは{@link IOException}を送出する可能性があります。
     * これが送出されると読み込みをいったん取り消し、再入力を試行します。</p>
     *
     * <p>以下、標準入力でboolean値を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.readBoolean();
     * </pre>
     *
     * @exception IllegalDataFormatException
     *            読み込んだテキスト行が"true"または"false"を表す文字列でなかった場合
     *
     * @return    boolean : trueかfalse
     * @since 1.0
     */
    public boolean readBoolean() {
        return readBoo(Pattern.compile("true", Pattern.CASE_INSENSITIVE),
                       Pattern.compile("false", Pattern.CASE_INSENSITIVE));
    }

    /**
     * メッセージを出力した後、テキスト行を読み込み、それが"true"または"false"
     * (大文字小文字は区別しない)に一致した場合に対応するbooleanを返却します。
     *
     * <p>このメソッドは{@link java.io.PrintStream#print(String)}を呼び出してから
     * {@link #readBoolean()}を呼び出すのと同じ動作をします。</p>
     *
     * <p>以下、標準入力からメッセージを出力してからテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.readBoolean({@code "input > "});
     * </pre>
     *
     * 上記のコードを{@link #readBoolean()}を用いて実装すると以下になります。
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     System.out.println({@code "input > "});
     *     boolean b = is.readBoolean();
     * </pre>
     *
     * @param inMsg 出力するメッセージ
     * @return      boolean : trueかfalse
     * @since 1.0
     */
    public boolean readBoolean(String inMsg) {
        System.out.print(inMsg);
        return readBoolean();
    }

    /**
     * メッセージ付きで"true"か"false"(大文字小文字区別しない)に
     * 一致したテキスト行が見つかるまで読み込みを繰り返し、
     * 見つかったらそれに対応するbooleanを返却します。
     *
     * <p>{@link #readBoolean(String)}の動作に加え、読み込んだテキスト行が、
     * "true"または"false"(大文字小文字区別しない)に一致しなかった場合、
     * 読み込む前に出力したメッセージとは別にエラーメッセージを表示し、
     * さらに1行を読み込みます。<br>
     * これを一致まで繰り返します。<br>
     * メッセージは読み込みを繰り返すたびに表示されます。</p>
     *
     * <p>以下、標準入力での使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.readBoolean({@code "input > "}, "error try again.");
     * </pre>
     *
     * <p>上記の例では、まず {@code "input > "}と出力し、その後テキスト行を読みます。
     * 読み込んだテキスト行が"true"または"false"(大文字小文字区別しない)に一致すれば、
     * テキストに対応するbooleanが返却されます。<br>
     * 一致しなかった場合は、エラーメッセージ "error try again." を出力し、
     * 再度 {@code "input > "}を出力してさらにテキスト行を読み込みます。<br>
     * これを繰り返します。</p>
     *
     * @param inMsg  テキスト行を読み込む前に出力するメッセージ
     * @param errMsg 読み込んだテキスト行が"true"または"false"(大文字小文字区別しない)に
     *               一致しなかった場合に出力するメッセージ
     *
     * @return       boolean : trueかfalse
     * @since 1.0
     */
    public boolean readBoolean(String inMsg, String errMsg) {
        while (true) {
            try {
                return readBoolean(inMsg);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    /**
     * テキスト行を読み込み、引数で指定した正規表現に一致した場合、
     * 対応するbooleanを返却します。
     *
     * <p>1行の終端は改行文字 "\n"か復帰 "\r" またはその両方の
     * 改行と復帰 "\n\r" で認識されます。</p>
     * <p>読み込むテキスト行に終端文字は含まず、その直前の文字1行を
     * 読み込みます。</p>
     *
     * <p>引数にはtrueかfalseを識別する正規表現を指定します。<br>
     * 例えば、読み込んだテキストが"yes"だった場合にtrueを返却したい場合は
     * 引数 : truePtに"yes"を与えます。大文字小文字は区別されません。</p>
     *
     * <p>以下、標準入力からテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.readCheckedBoolean("yes", "no");
     * </pre>
     *
     * <p>上記の例では、読み込んだ文字列が"yes"の場合はtrueを、
     * "no"の場合はfalseを返却します。大文字小文字を区別しないので、
     * 読み込んだ文字列が"Yes"や"YES"の場合であっても、trueが返却されます。<br>
     * また、そのどれにも一致しなかった場合は例外がスローされます。</p>
     *
     * @param truePt  一致した場合にtrueを返却する正規表現
     * @param falsePt 一致した場合にfalseを返却する正規表現
     * @return        boolean : trueかfalse
     */
    public boolean readCheckedBoolean(String truePt, String falsePt) {
        return readCheckedBoolean(truePt, falsePt, false);
    }

    /**
     * テキスト行を読み込み、引数で指定した正規表現に一致した場合、
     * 対応するbooleanを返却します。
     *
     * <p>{@link #readCheckedBoolean(String, String)}との違いは、
     * 引数がString型かPattern型か、大文字小文字を区別するかどうかです。
     * {@link #readCheckedBoolean(String, String)}では、
     * 大文字小文字は区別されませんがこのメソッドでは、
     * 引数の正規表現に順守します。</p>
     *
     * <p>以下、標準入力からテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.readCheckedBoolean(Pattern.compile("yes"),
     *                                       Pattern.compile("no"));
     * </pre>
     *
     * <p>上記の例では、読み込んだ文字列が"yes"の場合はtrueを、
     * "no"の場合はfalseを返却します。<br>
     * また、そのどれにも一致しなかった場合は例外がスローされます。</p>
     *
     * @param truePt  一致した場合にtrueを返却する正規表現
     * @param falsePt 一致した場合にfalseを返却する正規表現
     * @return        boolean : trueかfalse
     */
    public boolean readCheckedBoolean(Pattern truePt, Pattern falsePt) {
        return readBoo(truePt, falsePt);
    }

    /**
     * テキスト行を読み込み、引数で指定した正規表現に一致した場合、
     * 対応するbooleanを返却します。
     *
     * <p>このメソッドは{@link #readCheckedBoolean(String, String)}と大して
     * 違いはありません。<br>
     * 違いは引数のbooleanで大文字小文字を区別するかしないかを選択することができることです。</p>
     *
     * <p>引数にtrueを与えることで大文字小文字を区別します。<br>
     * falseが与えられた場合は大文字小文字を区別しません。</p>
     *
     * @param truePt  一致した場合にtrueを返却する正規表現
     * @param falsePt 一致した場合にfalseを返却する正規表現
     * @param flag    大文字小文字を区別するかしないかを表現するboolean
     *                true  : 区別する
     *                false : 区別しない
     *
     * @return        boolean : trueかfalse
     */
    public boolean readCheckedBoolean(String truePt, String falsePt, boolean flag) {
        if (flag) {
            return readBoo(Pattern.compile(truePt, Pattern.CASE_INSENSITIVE),
                           Pattern.compile(falsePt, Pattern.CASE_INSENSITIVE));
        }
        else {
            return readBoo(Pattern.compile(truePt), Pattern.compile(falsePt));
        }
    }

    /**
     * メッセージを出力してからテキスト行を読み込み、
     * 引数で指定した正規表現に一致した場合、
     * 対応するbooleanを返却します。
     *
     * <p>このメソッドは「問いかける」-> 「返事をきく」 -> 「trueかfalseで解釈する」
     * といった問いかけに似た処理を実現します。<br>
     *
     * 引数inMsgを出力し、テキスト行を読み込みます。<br>
     * 読み込んだテキストが引数truePt, falsePtに一致した場合、
     * 対応するbooleanが返却されます。<br>
     * 一致しなかった場合、例外がスローされます。</p>
     *
     * <p>具体的な使用法を以下に示します。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.ask("Do you want to overwrite? > ",
     *                        "yes", "no");
     * </pre>
     *
     * <p>上記の例は、上書きするか尋ねたうえでテキストを読み込み、
     * その結果によってtrueかfalseを返却します。<br>
     * "yes"という文字列だった場合はtrueが返却され、
     * "no"という文字列だった場合はfalseが返却されます。<br>
     * それ以外だった場合、例外がスローされます。</p>
     *
     * @param inMsg   出力するメッセージ
     * @param truePt  一致した場合にtrueを返却する正規表現
     * @param falsePt 一致した場合にfalseを返却する正規表現
     * @return        boolean : trueかfalse
     */
    public boolean ask(String inMsg, String truePt, String falsePt) {
        return ask(inMsg, truePt, falsePt, false);
    }

    /**
     * メッセージを出力してからテキスト行を読み込み、
     * 引数で指定した正規表現に一致した場合、
     * 対応するbooleanを返却します。
     *
     * <p>このメソッドは{@link #ask(String, String, String)}と大して
     * 違いはありません。<br>
     * 違いは引数のbooleanで大文字小文字を区別するかしないかを選択することができることです。</p>
     *
     * <p>引数にtrueを与えることで大文字小文字を区別します。<br>
     * falseが与えられた場合は大文字小文字を区別しません。</p>
     *
     * @param inMsg   出力するメッセージ
     * @param truePt  一致した場合にtrueを返却する正規表現
     * @param falsePt 一致した場合にfalseを返却する正規表現
     * @param flag    大文字小文字を区別するかしないかを表現するboolean
     *                true  : 区別する
     *                false : 区別しない
     *
     * @return        boolean : trueかfalse
     */
    public boolean ask(String inMsg, String truePt, String falsePt, boolean flag) {
        if (flag) {
            return ask(inMsg, Pattern.compile(truePt, Pattern.CASE_INSENSITIVE),
                              Pattern.compile(falsePt, Pattern.CASE_INSENSITIVE));
        }
        else {
            return ask(inMsg, Pattern.compile(truePt), Pattern.compile(falsePt));
        }
    }

    /**
     * メッセージを出力してからテキスト行を読み込み、
     * 引数で指定した正規表現に一致した場合、
     * 対応するbooleanを返却します。
     *
     * <p>{@link #ask(String, String, String)}との違いは、
     * 正規表現がString型かPattern型か、大文字小文字を区別するかどうかです。
     * {@link #ask(String, String, String)}では、
     * 大文字小文字は区別されませんがこのメソッドでは、
     * 引数の正規表現に順守します。</p>
     *
     * <p>以下、標準入力からテキスト行を読み込む使用例です。</p>
     *
     * <pre>
     *     InputScanner is = new InputScanner();
     *     boolean b = is.ask("Do you want to overwrite? > ",
     *                        Pattern.compile("yes"),
     *                        Pattern.compile("no"));
     * </pre>
     *
     * <p>上記の例では、"Do you want to overwrite? > "と出力したのち、
     * テキスト行を読み込み、読み込んだ文字列が"yes"の場合はtrueを、
     * "no"の場合はfalseを返却します。<br>
     * また、そのどれにも一致しなかった場合は例外がスローされます。</p>
     *
     * @param inMsg   出力するメッセージ
     * @param truePt  一致した場合にtrueを返却する正規表現
     * @param falsePt 一致した場合にfalseを返却する正規表現
     * @return        boolean : trueかfalse
     */
    public boolean ask(String inMsg, Pattern truePt, Pattern falsePt) {
        System.out.print(inMsg);
        return readBoo(truePt, falsePt);
    }

    public boolean ask(String inMsg, String errMsg, String truePt, String falsePt) {
        while (true) {
            try {
                return ask(inMsg, truePt, falsePt, false);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    //TODO: 引数多すぎ。このメソッドはたぶんいらない。消すことになると思う。
    public boolean ask(String inMsg, String errMsg, String truePt, String falsePt, boolean b) {
        while (true) {
            try {
                return ask(inMsg, truePt, falsePt, b);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }

    public boolean ask(String inMsg, String errMsg, Pattern truePt, Pattern falsePt) {
        while (true) {
            try {
                return ask(inMsg, truePt, falsePt);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
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
