package util.roulette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* めちゃくちゃコードが長いようにみえるだろ？？？？？？？？？？
 * 実はほとんどコードの説明でﾌﾟﾛｸﾞﾗﾑは数十行しか書いてないんだぜ。。。
 */

/**
 * BingoMachineクラスはビンゴゲームにおけるビンゴボールをランダムに吐き出す
 * 機械の機能と役割を提供します。
 * コンストラクタで指定された数までの各数字のBingoBall型オブジェクトを保持し、
 * それぞれに対して取得や削除の操作を行うことが可能です。
 */
public class BingoMachine {
    private int size;
    private List<BingoBall> balls = new ArrayList<>();

    /**
     * 与えられた引数からBingoMachineを作成します。
     * 引数はこのクラスのインスタンスが保持するボールの数を指定します。
     * もし、引数に２０が指定されたのであれば、このクラスの保持する
     * BingoBall型オブジェクトは1~20までの各数字を表現する20個です。
     *
     * 初期値では各BingoBall型オブジェクトは昇順に整列されて格納されます。
     *
     * @throws IllegalArgumentException 負の引数が与えられたとき
     * @param number 保持するBingoBall型オブジェクトの数を指定します。
     */
    public BingoMachine(int number) {
        //これがコンストラクタ
        //これがコンストラクタ
        //これがコンストラクタ
        //これがコンストラクタ
        //これがコンストラクタ
        //これがコンストラクタ
        if (number < 0) throw new IllegalArgumentException("引数の値が不正です。");
        size = number;
        for (int i = 1; i <= number; i++ ) {
            balls.add(new BingoBall(i));
        }
    }

    /**
     * 保持しているBingoBall型オブジェクトをシャッフルします。
     * コンストラクタでBingoBall型オブジェクトが作成されたとき、
     * その並びは昇順で整列されています。
     * したがって、このクラスのインスタンスが作成されてこのメソッドが
     * 呼ばれたとき、ランダムな数字の玉の出力を実現可能になります。
     */
    public void shuffle() {
        Collections.shuffle(balls);
    }

    /**
     * 保持しているBingoBall型オブジェクトをオブジェクトの持つ数字の昇順にソートします。
     */
    public void sortAsc() {
        /* くっそ読みづらいですが使うと便利な記法を用いています。
         * ラムダ式をしらないと呪文にしか見えないと思います。
         * この記法についてある程度勉強するまでこのコードには触らないことを推奨します。
         */
        balls.sort((BingoBall ball1, BingoBall ball2) -> {
                        if (ball1.equals(ball2)) return 0;
                        return (ball1.getNumber() > ball2.getNumber()) ? 1 : -1; });
    }

    /**
     * 保持しているBingoBall型オブジェクトをオブジェクトの持つ数字の降順にソートします。
     */
    public void sortDesc() {
        //同じく。。。
        balls.sort((BingoBall ball1, BingoBall ball2) -> {
                        if(ball1.equals(ball2)) return 0;
                        return (ball1.getNumber() > ball2.getNumber()) ? -1 : 1; });
    }

    /**
     * 保持しているBingoBall型オブジェクトをソートします。
     * ソート手段については呼び出し側が引数で与えます。
     *
     * @param sorter Comparator<BingoBall>を実装したソートクラス。
     */
    public void sort(Comparator<BingoBall> sorter) {
        balls.sort(sorter);
    }

    /**
     * 保存されているBingoBall型オブジェクトをListで取得します。
     * Listの要素はディープ・コピーされています。
     *
     * @return 保存されているBingoBall型オブジェクトが格納されているList
     */
    public List<BingoBall> getBallList() {
        List<BingoBall> ballList = new ArrayList<>();
        try {
            for(BingoBall ball : balls) {
                ballList.add(ball.clone());
            }
        } catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ballList;
    }

    /**
     * 保持されているBingoBall型オブジェクトのなかから一つ要素を取得します。
     * 指定される要素を表すインデックスは0が指定されます。
     * 要素がソートされている場合は要素をソート順に取り出すのに使用できますが、
     * シャッフルされてしまっている場合は、取り出す要素を特定することはできません。
     *
     * または、要素は取得されるだけであり、削除はされません。
     * 削除を行わずこのメソッドを連続で呼び出しても取得される要素は同じです。
     *
     * @throws BallNotFoundException 一つも要素が存在しないとき
     * @return 0番目の保存されているBingoBall型オブジェクト
     */

    public BingoBall getBall() {
        if (balls.size() == 0) throw new BallNotFoundException();
        return balls.get(0);
    }

    /**
     * 保持されているBingoBall型オブジェクトのなかから一つ要素を取得します。
     * 指定される要素を表すインデックスは引数で与えられます。
     * 要素がソートされている場合は、特定の意図したオブジェクトを取得することが
     * 可能です。
     * シャッフル後であれば{@link #getBall()}メソッドとなんら変わりはありません。
     * それは、取得する要素をインデックスで指定したとしても最初の要素を取り出したとしても
     * 結果を推測することができないからです。
     *
     * @param index 取り出す要素のインデックス
     * @throws BallNotFoundException 指定したインデックスに要素が存在しないとき
     * @return 取得したBingoBall型オブジェクト
     */
    public BingoBall getBall(int index) {
        if (index < 0 || index >= balls.size())
            throw new BallNotFoundException();
        return balls.get(index);
    }

    /**
     * 保持されているBingoBall型オブジェクトのうち一つを削除します。
     * 指定されるインデックスは0です。
     * 要素がソートされている場合は、ソート順に要素を削除することが可能です。
     * 要素がシャッフルされているのであれば削除する要素はランダムと等しくなります。
     *
     * @throws BallNotFoundException 一つも要素が存在しないとき
     */
    public void removeBall() {
        if (balls.size() == 0) throw new BallNotFoundException();
        balls.remove(0);
    }

    /**
     * 保持されているBingoBall型オブジェクトのうち一つを削除します。
     * 引数で削除する要素のインデックスを指定します。
     * 要素がソートされている場合は、特定の意図したオブジェクトを削除することが可能です。
     * シャッフル後であれば、{@link #removeBall()}メソッドとなんら変わりはありません。
     * それは、削除する要素をインデックスで指定したとしても最初の要素を削除したとしても
     * 結果を推測することができないからです。
     *
     * @throws BallNotFoundException 指定したインデックスに要素が存在しないとき
     * @param index 削除する要素のインデックス
     */
    public void removeBall(int index) {
        if (index < 0 || index >= balls.size()) throw new BallNotFoundException();
        balls.remove(index);
    }

    /**
     * このインスタンスが作られたときのBingoBall型オブジェクトの保持状態に復元します。
     * 要素が削除されたりした後でもこのメソッドを呼ぶことで初期化された状態に戻すことが可能です。
     */
    public void initialize() {
        balls.clear();
        for (int i = 1; i <= size; i++ ) {
            balls.add(new BingoBall(i));
        }
    }
}
