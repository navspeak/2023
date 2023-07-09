public class TwoSum {
    public static void main(String[] args) {
        int[] array = {1,3,4,7,9};
        TwoSum twoSum = new TwoSum();
        System.out.println(twoSum.twoSum(array, 5)); // true
        System.out.println(twoSum.twoSum(array, 10)); // true
        System.out.println(twoSum.twoSum(array, 14)); // fasle
    }

    boolean twoSum(int[] array, int sum){
        int i = 0, j = array.length - 1;
        while(i < j){
            int pairSum = array[i] + array[j];
            if (pairSum == sum) return true;
            if (pairSum < sum) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }
}
