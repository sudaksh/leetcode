package netflix;

import util.TestUtil;

import java.util.ArrayList;
import java.util.List;



interface MathFunction{
    public int eval(int a, int b);
    public String print(int a, int b);
}

class Multiply implements MathFunction {
    public int eval(int a, int b) {
        return a * b;
    }

    public String print(int a, int b){
        return a + "*" + b;
    }
}

class Divide implements MathFunction {
    public int eval(int a, int b) {
        if(b == 0 || a % b !=0){
            throw new UnsupportedOperationException("Division not possible");
        }
        return a / b;
    }

    public String print(int a, int b){
        return a + "/" + b;
    }
}

class Subtract implements MathFunction {
    public int eval(int a, int b) {
        return a - b;
    }

    public String print(int a, int b){
        return a + "-" + b;
    }
}

class Add implements MathFunction {
    public int eval(int a, int b) {
        return a + b;
    }

    public String print(int a, int b){
        return a + "+" + b;
    }
}

public class ArithmeticTarget {

    private static final List<MathFunction> functions = List.of(new Multiply(), new Divide(), new Subtract(), new Add());

    public static List<String> arithmeticTarget(int[] nums, int target) {
        List<String> result = new ArrayList<>();
        int first = nums[0];
        int second = nums[1];

        for (MathFunction fun : functions){
            try {
                if(fun.eval(first, second) == target) {
                    result.add(fun.print(first,second));
                }
            } catch (UnsupportedOperationException e){
                // do nothing
            }

            try {
                if(fun.eval(second, first) == target) {
                    result.add(fun.print(second,first));
                }
            } catch (UnsupportedOperationException e){
                // do nothing
            }
        }

        System.out.println("result =" + result);
        return result;
    }


    static void main() {
        System.out.println("starting asserts");

        TestUtil.checkCollectionEqual(arithmeticTarget(new int[]{1, 2}, 3),List.of("1+2", "2+1"));
        TestUtil.checkCollectionEqual(arithmeticTarget(new int[]{6, 3}, 2),List.of("6/3"));
        TestUtil.checkCollectionEqual(arithmeticTarget(new int[]{5, 0}, 0),List.of("5*0", "0*5", "0/5"));
        TestUtil.checkCollectionEqual(arithmeticTarget(new int[]{0, 0}, 0),List.of("0+0", "0+0", "0-0", "0-0", "0*0", "0*0"));
    }

}
