package org.example;

/*private になっているフィールド変数を
変更するのが setter
取得するのが getter

フィールドとして宣言した変数はコンストラクタやメソッドで宣言することなく
そのクラス内であればどこからでも参照したり更新したりすることができる。
*/

public class BmrDTO {

    private int bmr;
    private int tdee;

    public BmrDTO(int bmr, int tdee) {
        this.bmr = bmr;
        this.tdee = tdee;
    }

    public int getBmr() {
        return bmr;
    }

    public void setBmr(int bmr) {
        this.bmr = bmr;
    }

    public int getTdee() {
        return tdee;
    }

    public void setTdee(int tdee) {
        this.tdee = tdee;
    }
}
