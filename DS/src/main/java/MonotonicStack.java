import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class MonotonicStack {

    public static void main(String[] args) {
//        int[] arr = {5,3,-1,-3,5,3,6,7}; => -1
        //  Here we want to get max at ay spot => monotonically decreasing array
        //   5,3,-1,
        //   is 3(at 1) > 1(at 0) yes, so insert in array keeping montonic property
        //   3(at 1),
        //   -1(at 2)
        //   -3(at 3)
        //    5 voilates monotonic property but 3(at 1) is out of bound

        int[] arr = {1,-1};
        System.out.println(Arrays.toString(maxSlidingWindow(arr, 1)));
    }

    // https://leetcode.com/problems/sliding-window-maximum/description/
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            if(!deque.isEmpty() && nums[i] > nums[deque.peekLast()]){
                deque.removeLast();
            }
            deque.addFirst(i);
            if (deque.getFirst()+k == i) deque.removeFirst();
            if (i>=k-1)
                ans[i-k+1] = deque.getFirst();
        }
        return ans;
    }
}
