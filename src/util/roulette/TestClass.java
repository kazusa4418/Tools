package util.roulette;

import util.playingcard.CardNumber;

public class TestClass {
    public static void main(String[] args) {
        //ここではルーレットパッケージに属するビンゴ関連クラスのテストを行うと同時に
        //その使用方法について説明します。
        //ここで紹介しているメソッドのほかにもメソッドがあります。
        //クラスをいじって追加したりもできます。
        //是非、ソースファイルをのぞいてみてください。

        //ビンゴをするのに使うビンゴマシーン的なものをインスタンス化
        //引数はBingoMachineクラスの実装を見てください。
        //ちなみにコンストラクタなのでコンストラクタと書いてあるところのメソッド的なものを見てね。
        BingoMachine bm = new BingoMachine(20);

        //ビンゴマシーンのなかのボールをシャッフルする
        bm.shuffle();

        //ビンゴマシーンのなかのボールを一つ取り出す
        BingoBall ball = bm.getBall();
        bm.removeBall(); //ここでは取得したビンゴボールをマシンのなかから削除してる

        //取り出したボールを画面に出力する
        System.out.println(ball);

        //取り出したボールなどをすべてマシンに戻してマシンをインスタンス化したときの状態に戻す
        bm.initialize();

        CardNumber.values();

    }
}
