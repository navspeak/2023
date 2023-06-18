package javapractice;

public class _0Test {
    public static void main(String[] args) {
        String s = "Navneet";
        char[] chars = s.toCharArray();

        System.out.println(c1.i);
        c1 objc1 = new c11();
        objc1.f();
        int i = objc1.i;
        System.out.println(i);
        System.out.println(c1.i);

    }
}


abstract class c1 {
    public static int i = 9;
    c1(){
        System.out.println("base ctor");
        i = 10;
    }

    public void t(){
        System.out.println("Base t ");
    }

    abstract void f();
}

class c11 extends c1 {
    @Override
    public void t() {
        System.out.println("Child t did not need to provide impl");
    }

    @Override
    void f() {
        System.out.println("Had to provide impl");
    }
}