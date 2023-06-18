import HashMap.Hashmap;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        String s = "Navneet";
        char[] chars = s.toCharArray();
        int[] nums = new int[]{1,3,4};
        Arrays.stream(nums).sum();
        Arrays.stream(nums).average();
        Map<Integer, Integer> map1 = new LinkedHashMap(5,0.75f,false);
        String a = "abx";
        int[][] grid = {{3,1,2,2},{1,4,4,5},{2,4,2,2},{2,4,2,2}};
        System.out.println(Arrays.toString(grid[0]));
        System.out.println(equalPairs(grid));
        System.out.println(canConstruct("a", "b"));

    }
   //https://leetcode.com/problems/equal-row-and-column-pairs/description/
    public static int equalPairs(int[][] grid) {
        Map<String, Integer> map = new HashMap<>();
        List<String> col_words = new ArrayList<>();
        for(int i = 0; i< grid.length; i++){
            StringBuilder sb_row = new StringBuilder();
            StringBuilder sb_col = new StringBuilder();
            for(int j = 0; j< grid.length; j++){
                sb_row.append(grid[i][j]);
                sb_col.append(grid[j][i]);
                sb_row.append(",");
                sb_col.append(",");
            }
            map.put(sb_row.toString(), map.getOrDefault(sb_row.toString(), 0)+1);
            col_words.add(sb_col.toString());
        }

        int ans = 0;
        for(int i = 0; i < col_words.size(); i++){
            if (map.containsKey(col_words.get(i))) {
                ans += map.get(col_words.get(i));
            }
        }

        return ans;
    }

    public static boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) return false;
        int[] alphabets = new int[26];

        for(char c: ransomNote.toCharArray()){
            alphabets[c - 'a'] += 1;
        }

        for(char c: magazine.toCharArray()){
            alphabets[c - 'a'] -= 1;
        }

        for (int i = 0; i < alphabets.length; i++) {
            if (alphabets[i] > 0) return false;
        }
        return true;

    }

    public boolean checkIfPangram(String sentence) {

        int[] chars = new int[26];
        int count = 0;
        for (int i = 0; i < sentence.length(); i++){
            char c = sentence.charAt(i);
            if (chars['z'-c] == 0){
                chars['z'-c] = 1;
                count++;
            }
        }
        return count == 26;

    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (final String str: strs){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            map.compute(key, (k, v)-> {
                if (v == null){
                    List<String> val = new ArrayList();
                    val.add(str);
                    return val;
                }

                v.add(str);
                return v;
            });
        }

        List<List<String>> ans = new ArrayList<>();
        for(List<String> entry: map.values()){
            ans.add(entry);
        }
        return ans;
    }
}


