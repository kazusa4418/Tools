package util.roulette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BingoMachineクラスはビンゴゲームにおけるビンゴボールをランダムに吐き出す
 * 機械の機能と役割を提供します。
 * コンストラクタで指定された数までの各数字のBingoBall型オブジェクトを保持し、
 * それぞれに対して取得や削除の動作を行うことが可能です。
 */
public class BingoMachine {
    private List<BingoBall> balls = new ArrayList<>();

    /**
     * 与えられた引数からBingoMachineを作成します。
     * 引数はこのクラスのインスタンスが保持するボールの数を指定します。
     * もし、引数に２０が指定されたのであれば、このクラスの保持する
     * BingoBall型オブジェクトは1~20までの各数字を表現する20個です。
     *
     * 初期値では各BingoBall型オブジェクトは昇順に整列されて格納されます。
     *
     * @param number 保持するBingoBall型オブジェクトの数を指定します。
     */
    public BingoMachine(int number) {
        if (number < 0) throw new IllegalArgumentException("引数の値が不正です。");
        for (int i = 1; i <= number; i++ ) {
            balls.add(new BingoBall(i));
        }
    }

    /**
     * 保持しているBingoBall型オブジェクトをシャッフルします。
     * コンストラクタでBingoBall型オブジェクトが作成されたとき、
     * その並びは昇順で整列されています。
     * したがって、このクラスのインスタンスが作成されてすぐこのメソッドが
     * 呼ばれたとき、ランダムな数字の玉の出力を実現可能になります。
     */
    public void shuffle() {
        Collections.shuffle(balls);
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
     * @throws Index
     * @return 0番目の保存されているBingoBall型オブジェクト
     */
    public BingoBall getBall() {
        return balls.get(0);
    }

    /**
     * 保持されているBingoBall型オブジェクトのなかから一つ要素を取得します。
     * 指定される要素を表すインデックスは引数で与えられます。
     * 要素がソートされている場合は、特定の意図したオブジェクトを取得することが
     * 可能です。
     * シャッフル後であれば{@link #getBall()}メソッドとなんら変わりはありません。
     * 取得する要素をインデックスで指定したとしても最初の要素を取り出したとしても
     * 結果を推測することはできません。
     *
     * @param index 取り出す要素のインデックス
     * @return 取得したBingoBall型オブジェクト
     */
    public BingoBall getBall(int index) {
        return balls.get(index);
    }

    /**
     * 保持されているBingoBall型オブジェクトのうち一つを削除します。
     * 指定されるインデックスは0が指定されます。
     * 要素がソートされている場合は、ソート順に要素を削除することが可能です。
     * 要素がシャッフルされているのであれば削除する要素はランダムになります。
     *
     */
    public void removeBall() {
        balls.remove(0);
    }

    public void removeBall(int index) {
        balls.remove(index);
    }
}
