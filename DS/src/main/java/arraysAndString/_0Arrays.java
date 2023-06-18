package arraysAndString;

import java.util.*;
import java.util.stream.Collectors;

public class _0Arrays {
    public static void main(String[] args) {
        int[] nums = {2,3,1,2,4,3};
        System.out.println(minSubArrayLen(7, nums));
        System.out.println(pivotIndex(new int[]{1,7,3,6,5,6}));
        int[][] matches = {{2,3},{1,3},{5,4},{6,4}};
        System.out.println(Arrays.deepToString(findWinners(matches).toArray()));

    }
    // https://leetcode.com/problems/minimum-size-subarray-sum/description/
    public static int minSubArrayLen(int target, int[] nums) {
        int currSum = 0;
        int len = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            currSum +=nums[right];
            while (currSum >= target){
                len = Math.min(len, right - left + 1);
                currSum-=nums[left];
                left++;
            }
            right++;

        }
        return len == Integer.MAX_VALUE ? 0: len;
    }
    //https://leetcode.com/problems/get-equal-substrings-within-budget/description/
    public int equalSubstring(String s, String t, int maxCost) {
        int[] diff = new int[s.length()];
        for(int i = 0; i < s.length();i++){
            diff[i] = Math.abs(s.charAt(i) - t.charAt(i));
        }

        int curr = 0;
        int ans = 0;
        int left = 0;
        for(int right =0; right < s.length(); right++){
            curr +=diff[right];
            while(curr > maxCost){
                curr-=diff[left++];
            }
            ans = Math.max(curr, right - left + 1);
        }
        return ans;
    }

//https://leetcode.com/problems/find-pivot-index/description/
    public static int pivotIndex(int[] nums) {
        int[] prefixSum = new int[nums.length];
        prefixSum[0] = nums[0];
        for(int i=1; i<nums.length;i++){
            prefixSum[i]=prefixSum[i-1] + nums[i];
        }
        for(int i = 0; i< nums.length; i++){
            int sumLeft = 0;
            if (i > 0) sumLeft = prefixSum[i-1];
            int sumRight = 0;
            if (i < nums.length - 1)
                sumRight = prefixSum[nums.length - 1] -prefixSum[i];
            if (sumLeft == sumRight) return i;
        }
        return -1;
    }

    //https://leetcode.com/problems/find-players-with-zero-or-one-losses/description/
    public static List<List<Integer>> findWinners(int[][] matches) {
        Map<Integer, Integer> wins = new HashMap<>();
        Map<Integer, Integer> losses = new HashMap<>();
        final List<Integer> lostNone = new ArrayList<>();
        final List<Integer> lostOnlyOne = new ArrayList<>();
        Set<Integer> players = new HashSet<>();
        for(int[] match: matches){
            wins.put(match[0], wins.getOrDefault(match[0], 0) + 1);
            losses.put(match[1], losses.getOrDefault(match[1], 0) + 1);
            players.add(match[0]);
            players.add(match[1]);
        }

        for (Integer player: players){
            if (losses.containsKey(player) && losses.get(player) == 1){
                lostOnlyOne.add(player);
            } else if (!losses.containsKey(player) && wins.containsKey(player)){
                lostNone.add(player);
            }
        }
        Collections.sort(lostNone);
        Collections.sort(lostOnlyOne);
        List<List<Integer>> result = new ArrayList<>();
        result.add(lostNone);
        result.add(lostOnlyOne);
        //result.get(0).addAll(lostNone)
        return result;
    }
}
