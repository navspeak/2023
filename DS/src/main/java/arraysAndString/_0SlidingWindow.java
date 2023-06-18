package arraysAndString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class _0SlidingWindow {
    /* Example 1: Given an array of positive integers nums and an integer k,
    find the length of the longest subarray whose sum is less than or equal to k.
    nums = [3, 1, 8, 7, 4, 2, 1, 1, 5] and k = 8.
     */
    public static void main(String[] args) {

        System.out.println(subarraySum(new int[]{1, 1, 1}, 2));
        System.out.println(longestOnes(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2));
        int i = Integer.MAX_VALUE; // -2.1L to 2.1L
        System.out.println(i+1L);// wrongValue if L is not put
        long j = Long.MAX_VALUE;
        System.out.println(j);
        String s = " A !nav";
        s.length();
        char c = s.charAt(0) ;
        if (c >= 'A' && c<='Z' || c>='a' && c<= 'z'){
            System.out.println((c + " is an English Alphabet"));
        } else {
            System.out.println((c + " is not an English Alphabet"));
        }
        int[] nums = new int[]{0,1,0,3,12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));

    }

    /*
       Example 1: Given an array of positive integers nums and an integer k,
       find the length of the longest subarray whose sum is less than or equal to k.
     */
    public static int lengthOfLongestSubarrayLessThanTarget(int[] arr, int target) {
        int left = 0, currSum = 0;
        int length = 0;
        for (int right = 0; right < arr.length; right++) {
            currSum += arr[right];
            if (currSum > target) {
                currSum -= arr[left];
                left++;
            }
            length = Math.max(length, right - left + 1);
        }
        return length;
    }

    /* 560 - 560. Subarray Sum Equals K */
    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k)
                result++;
            if (map.containsKey(sum - k))
                result += map.get(sum - k);

            map.put(sum, map.getOrDefault(sum, 0) + 1);

        }
        return result;
    }


/*
Example 2: You are given a binary substring s (a string containing only "0" and "1"). An operation involves flipping a "0" into a "1".
What is the length of the longest substring containing only "1" after performing at most one operation?
For example, given s = "1101100111", the answer is 5. If you perform the operation at index 2, the string becomes 1111100111.
 */

    public static int lenOfLongestSubstringContainingOnlyOne(String str) {
        // constraint = window with at most 1 0
        int left = 0, currSum = 0, length = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '0'){
                currSum++;
            }

            while(length > 1){
                if (chars[left] == '0') currSum--;
            }
            length = Math.max(length, i - left + 1);
        }
        return length;
    }


    // 713. Subarray Product Less Than K.
    // [1,2,3] & 0 | [10,5,2,6], k = 100
    /*
    Explanation: The 8 subarrays that have product less than 100 are:
    [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6]
    Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        //if (k <= 1) return 0;
        int left = 0, right = 0;
        int ans = 0, product = 1;

        for (right = 0; right < nums.length; right++) {
            product *= nums[right];

            while (left<=right && product >= k) {
                product = product / nums[left];
                left++;
            }

            ans += right - left + 1;
        }

        return ans;
    }

    public static int longestOnes(int[] nums, int k) {
        int left = 0;
        int sum = 0; // sum of zeros in curr window
        int len = 0;


        for (int right = 0; right < nums.length; right++){
            if (nums[right] == 0)
                sum ++;

            while(left <= right && sum > k){
                if (nums[left] == 0)
                    sum -=nums[left];
                left++;
            }

            len = Math.max(len, right - left + 1);
        }

        return len;
    }

    /*
    https://leetcode.com/problems/number-of-ways-to-split-array/description/
    Note for prefix sum you might need long
     */



    // https://leetcode.com/problems/move-zeroes/
    public static void moveZeroes(int[] nums) {

        int i = 0;
        int j = 0;
        while(j < nums.length){
            while(j<nums.length && nums[j] == 0) j++;
            if (nums[i] == 0 && j<nums.length){
                int t = nums[j];
                nums[j] = nums[i];
                nums[i] = t;
                while(j<nums.length && nums[j] == 0) j++;
                i++;
            } else {
                i++;
                j++;
            }
        }

    }
}


