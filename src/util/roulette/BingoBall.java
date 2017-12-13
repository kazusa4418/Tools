package util.roulette;

class BingoBall implements Cloneable {
    //インスタンスが持つボールの数字
    private int number;

    //インスタンス化されるときに引数でボールの数字を指定する
    BingoBall(int number) {
        this.number = number;
    }

    //このインスタンスの持つボールの数字を取得する
    int getNumber() {
        return number;
    }

    //引数とこのインスタンスのボールの数字が等しいか判定する
    //引数で与えられたオブジェクトがBingoBallクラスのインスタンスではなかった場合、
    //またはボールの数字が一致しなかった場合はfalse
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BingoBall)) return false;
        BingoBall ball = (BingoBall)obj;
        return this.getNumber() == ball.getNumber();
    }

    //ただSystem.out.printlnでこのインスタンスが出力されたときに
    //ボールの数字が画面に出力されるようにしてるだけ。
    //実はSystem.out.printlnメソッドは引数で与えられたインスタンスの
    //toString()メソッドを呼んで取得されるString型オブジェクトを画面に出力してる。
    //だからこういう風に数字が文字列で返却されるようにtoString()を再定義してあげないと
    //printlnで呼ばれたときにこのインスタンスのハッシュコードっていうわけの分からない
    //文字列が画面に出力されてしまう。
    @Override
    public String toString() {
        return String.valueOf(number);
    }

    //これはクローン処理っていって参照型のオブジェクトを参照先をコピーするのではなく
    //インスタンス自体をフルコピーしたいときに使用する。
    //専門用語を使うとシャロー・コピーではなくディープ・コピーしたいときに呼ばれるｗ
    //正直参照うんぬんについてそれなりに詳しくないと"??????"状態になると思うけど一応書いとく。
    @Override
    protected BingoBall clone() throws CloneNotSupportedException {
        return (BingoBall) super.clone();
    }
}
