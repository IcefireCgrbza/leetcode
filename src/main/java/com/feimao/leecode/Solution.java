package com.feimao.leecode;

import groovy.util.IFileNameFinder;
import groovy.util.Node;

import javax.swing.plaf.IconUIResource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: feimao
 * @Date: 21-3-19 下午5:21
 */
public class Solution {

    public static void main(String[] args) {
        new Solution().solution301(")()(");
    }

    public boolean solution10(String s, String p) {
        boolean[][] reg = new boolean[p.length() + 1][s.length() + 1];
        for (int i = 0; i <= p.length(); i++) {
            for (int j = 0; j <= s.length(); j++) {
                if (i == 0) {
                    reg[i][j] = j == 0;
                    continue;
                } else if (i == 1) {
                    if (p.charAt(i - 1) == '*') {
                        throw new IllegalArgumentException("p首位不允许为*");
                    } else {
                        reg[i][j] = j == 1 && (p.charAt(i - 1) == '.' || p.charAt(i - 1) == s.charAt(j - 1));
                    }
                } else if (j == 0) {
                    reg[i][j] = p.charAt(i - 1) == '*' && reg[i - 2][j];
                } else {
                    if (p.charAt(i - 1) == '.') {
                        reg[i][j] = reg[i - 1][j - 1];
                    } else if (p.charAt(i - 1) == '*') {
                        reg[i][j] =
                            ((p.charAt(i - 2) == '.' || p.charAt(i - 2) == s.charAt(j - 1)) && reg[i][j - 1]) || reg[i
                                - 2][j];
                    } else {
                        reg[i][j] = s.charAt(j - 1) == p.charAt(i - 1) && reg[i - 1][j - 1];
                    }
                }
            }
        }
        return reg[p.length()][s.length()];
    }

    public int sulotion11(int[] height) {
        if (height.length < 2) {
            return 0;
        }

        int i = 0, j = height.length - 1, max = 0;
        while (i < j) {
            int area = (j - i) * Math.min(height[i], height[j]);
            if (max < area) {
                max = area;
            }
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return max;
    }

    public List<List<Integer>> sulotion15(int[] nums) {
        Set<List<Integer>> groups = new HashSet<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1, k = nums.length - 1, sum = 0 - nums[i];
            while (j < k) {
                if (sum == nums[j] + nums[k]) {
                    groups.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    k--;
                    j++;
                } else if (nums[j] + nums[k] > sum) {
                    k--;
                } else {
                    j++;
                }
            }
        }
        return new ArrayList<>(groups);
    }

    public List<String> sulotion17(String digits) {
        if (digits.length() == 0) {
            return Collections.emptyList();
        }
        Map<String, List<String>> digit2Letters = new HashMap<>();
        digit2Letters.put("2", Arrays.asList("a", "b", "c"));
        digit2Letters.put("3", Arrays.asList("d", "e", "f"));
        digit2Letters.put("4", Arrays.asList("g", "h", "i"));
        digit2Letters.put("5", Arrays.asList("j", "k", "l"));
        digit2Letters.put("6", Arrays.asList("m", "n", "o"));
        digit2Letters.put("7", Arrays.asList("p", "q", "r", "s"));
        digit2Letters.put("8", Arrays.asList("t", "u", "v"));
        digit2Letters.put("9", Arrays.asList("w", "x", "y", "z"));
        return letterCombinations(digits, "", digit2Letters);
    }

    private List<String> letterCombinations(String digits, String combination, Map<String, List<String>> digit2Letters) {
        if (digits.length() == 0) {
            return Arrays.asList(combination);
        }
        if (!digit2Letters.containsKey(digits.substring(0, 1))) {
            return letterCombinations(digits.substring(1), combination, digit2Letters);
        }
        List<String> combinations = new ArrayList<>();
        for (String c : digit2Letters.get(digits.substring(0, 1))) {
            combinations.addAll(letterCombinations(digits.substring(1), combination + c, digit2Letters));
        }
        return combinations;
    }

    public class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode solution19(ListNode head, int n) {
        ListNode a = head, b = head, prev = a, next = a.next;
        while (n-- != 0) {
            if (b == null) {
                return head;
            }
            b = b.next;
        }
        while (b != null) {
            b = b.next;
            prev = a;
            a = a.next;
            next = a.next;
        }
        if (a == head) {
            return head.next;
        } else {
            prev.next = next;
            return head;
        }
    }

    public boolean solution20(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (ch.equals('(') || ch.equals('[') || ch.equals('{')) {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                Character pop = stack.pop();
                if (!(ch.equals(')') && pop.equals('(')) && !(ch.equals(']') && pop.equals('[')) && !(ch.equals('}') && pop
                    .equals('{'))) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public ListNode solution21(ListNode l1, ListNode l2) {
        return merge(l1, l2);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode head = null, cur = null;
        while (l1 != null || l2 != null) {
            ListNode min = null;
            if (l2 == null) {
                min = l1;
                l1 = l1.next;
            } else if (l1 == null) {
                min = l2;
                l2 = l2.next;
            } else if (l1.val < l2.val) {
                min = l1;
                l1 = l1.next;
            } else {
                min = l2;
                l2 = l2.next;
            }
            if (head == null) {
                head = min;
                cur = head;
            } else {
                cur.next = min;
                cur = min;
            }
        }
        return head;
    }

    public List<String> solution22(int n) {
        return backtraceParenthesis("", 0, 0, n);
    }

    private List<String> backtraceParenthesis(String str, int leftCount, int leftUsed, int n) {
        if (str.length() == 2 * n) {
            return new ArrayList<>(Collections.singletonList(str));
        }
        if (leftCount == 0) {
            return backtraceParenthesis(str + "(", leftCount + 1, leftUsed + 1, n);
        } else if (leftUsed < n) {
            List<String> res = backtraceParenthesis(str + "(", leftCount + 1, leftUsed + 1, n);
            res.addAll(backtraceParenthesis(str + ")", leftCount - 1, leftUsed, n));
            return res;
        } else {
            return backtraceParenthesis(str + ")", leftCount - 1, leftUsed, n);
        }
    }

    public ListNode solution23(ListNode[] lists) {
        int length = lists.length;
        while (length > 1) {
            for (int i = 0; i < length; i += 2) {
                lists[i / 2] = i + 1 >= length ? lists[i] : merge(lists[i], lists[i + 1]);
            }
            length = (length + 1) / 2;
        }
        return lists.length == 0 ? null : lists[0];
    }

    public void solution31(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0) {
            if (nums[i] < nums[i + 1]) {
                break;
            }
            i--;
        }
        if (i < 0) {
            Arrays.sort(nums);
            return;
        }
        int j = nums.length - 1;
        while (j > i) {
            if (nums[j] > nums[i]) {
                break;
            }
            j--;
        }
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
        Arrays.sort(nums, i + 1, nums.length);
    }

    public int solution32(String s) {
        int[] dp = new int[s.length()];
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                dp[i] = 0;
            } else if (i == 1) {
                dp[i] = s.charAt(0) == '(' && s.charAt(1) == ')' ? 2 : 0;
            } else if (s.charAt(i) == '(') {
                dp[i] = 0;
            } else if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = dp[i - 2] + 2;
                } else if (i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] - 2 >= 0 ? dp[i - dp[i - 1] - 2] : 0);
                } else {
                    dp[i] = 0;
                }
            } else {
                throw new IllegalArgumentException("非法字符");
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int solution33(int[] nums, int target) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int medium = (i + j) / 2;
            if (nums[medium] == target) {
                return medium;
            } else if (nums[medium] > nums[i]) {
                if (nums[i] == target) {
                    return i;
                } else if (nums[medium] > target && nums[i] < target) {
                    i = i + 1;
                    j = medium - 1;
                } else {
                    i = medium + 1;
                }
            } else {
                if (nums[j] == target) {
                    return j;
                } else if (nums[medium] < target && nums[j] > target) {
                    i = medium + 1;
                    j = j - 1;
                } else {
                    j = medium - 1;
                }
            }
        }
        return -1;
    }

    public int[] solution34(int[] nums, int target) {
        int i = 0, j = nums.length - 1, pos = -1;
        while (i <= j) {
            int medium = (i + j) / 2;
            if (nums[medium] == target) {
                pos = medium;
                break;
            } else if (nums[medium] > target) {
                j = medium - 1;
            } else {
                i = medium + 1;
            }
        }
        if (pos == -1) {
            return new int[]{-1, -1};
        }
        i = pos;
        j = pos;
        while (i >= 0 && nums[i] == target) {
            i--;
        }
        while (j <= nums.length - 1 && nums[j] == target) {
            j++;
        }
        return new int[]{i + 1, j - 1};
    }

    public List<List<Integer>> solution39(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        backtraceSumGroup(candidates, 0, target, new Stack<>(), res);
        return res;
    }

    private void backtraceSumGroup(int[] candidates, int from, int target, Stack<Integer> group, List<List<Integer>> res) {
        for (int i = from; i < candidates.length; i++) {
            int num = candidates[i];
            if (num > target) {
                return;
            } else if (num == target) {
                List<Integer> sumGroup = new ArrayList<>(group);
                sumGroup.add(num);
                res.add(sumGroup);
                return;
            } else {
                group.push(num);
                backtraceSumGroup(candidates, i, target - num, group, res);
                group.pop();
            }
        }
    }

    public int sulotion42(int[] height) {
        int[] leftMax = new int[height.length];
        int[] rightMax = new int[height.length];
        for (int i = 0; i < height.length; i++) {
            if (i == 0) {
                leftMax[i] = 0;
            } else {
                leftMax[i] = height[i - 1] > leftMax[i - 1] ? height[i - 1] : leftMax[i - 1];
            }
        }
        for (int i = height.length - 1; i >= 0; i--) {
            if (i == height.length - 1) {
                rightMax[i] = 0;
            } else {
                rightMax[i] = height[i + 1] > rightMax[i + 1] ? height[i + 1] : rightMax[i + 1];
            }
        }
        int sum = 0;
        for (int i = 1; i < height.length - 1; i++) {
            if (leftMax[i] > height[i] && rightMax[i] > height[i]) {
                sum += Math.min(leftMax[i], rightMax[i]) - height[i];
            }
        }
        return sum;
    }

    public List<List<Integer>> sulotion46(int[] nums) {
        List<List<Integer>> groups = new ArrayList<>();
        backtraceNumGroups(nums, new Stack<>(), groups);
        return groups;
    }

    private void backtraceNumGroups(int[] nums, Stack<Integer> stack, List<List<Integer>> groups) {
        if (stack.size() == nums.length) {
            List<Integer> group = new ArrayList<>(stack);
            groups.add(group);
            return;
        }

        for (int num : nums) {
            if (!stack.contains(num)) {
                stack.push(num);
                backtraceNumGroups(nums, stack, groups);
                stack.pop();
            }
        }
    }

    public void sulotion48(int[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len / 2; i++) {
            for (int j = i; j < len - i - 1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[len - j - 1][i];
                matrix[len - j - 1][i] = matrix[len - i - 1][len - j - 1];
                matrix[len - i - 1][len - j - 1] = matrix[j][len - i - 1];
                matrix[j][len - i - 1] = tmp;
            }
        }
    }

    public List<List<String>> sulotion49(String[] strs) {
        Map<String, List<String>> res = new HashMap<>();
        int[] count = new int[26];
        for (String str : strs) {
            for (int i = 0; i < 26; i++) {
                count[i] = 0;
            }
            for (char ch : str.toCharArray()) {
                count[ch - 'a']++;
            }
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                key.append('a' + i).append(count[i]);
            }
            List<String> list = res.computeIfAbsent(key.toString(), k -> new ArrayList<>());
            list.add(str);
        }
        return new ArrayList<>(res.values());
    }

    public int sulotion53(int[] nums) {
        int[] dp = new int[nums.length];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = dp[i - 1] > 0 ? nums[i] + dp[i - 1] : nums[i];
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public boolean sulotion55(int[] nums) {
        for (int i = 0, j = 0; i < nums.length && i <= j; i++) {
            j = Math.max(nums[i] + i, j);
            if (j >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }

    public int[][] sulotion56(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));

        int i = 0, j = i + 1;
        int len = 0;
        while (i < intervals.length) {
            int[] merge = intervals[i];
            while (j < intervals.length && merge[1] >= intervals[j][0]) {
                merge[1] = Math.max(merge[1], intervals[j][1]);
                j++;
            }
            intervals[len++] = merge;
            i = j;
        }

        int[][] res = new int[len][2];
        while (len > 0) {
            res[len] = intervals[--len];
        }
        return res;
    }

    public int sulotion62(int m, int n) {
        int dp[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public int sulotion64(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int dp[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] + grid[i][j];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] + grid[i][j];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public int sulotion70(int n) {
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                dp[i] = 1;
            } else if (i == 1) {
                dp[i] = 2;
            } else {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
        }
        return dp[n - 1];
    }

    public int sulotion72(String word1, String word2) {
        int m = word1.length() + 1, n = word2.length() + 1;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int min = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                    dp[i][j] = Math.min(min, word1.charAt(i - 1) == word2.charAt(j - 1) ? dp[i - 1][j - 1] :
                        dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public void sulotion75(int[] nums) {
        int p = 0, q = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                swap(nums, p, i);
                if (p < q) {
                    swap(nums, q, i);
                }
                p++;
                q++;
            } else if (nums[i] == 1) {
                swap(nums, q, i);
                q++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public String sulotion76(String s, String t) {
        int len = t.length();
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : t.toCharArray()) {
            int count = counts.getOrDefault(c, 0);
            counts.put(c, ++count);
        }
        Map<Character, Integer> cur = new HashMap<>();
        int i = 0, j = -1, min = Integer.MAX_VALUE;
        String minStr = "";
        while (i < s.length()) {
            if (len > 0) {
                if (j == s.length() - 1) {
                    break;
                }
                j++;
                char ch = s.charAt(j);
                int count = cur.getOrDefault(ch, 0);
                cur.put(ch, ++count);
                if (counts.containsKey(ch) && count <= counts.get(ch)) {
                    len--;
                }
            } else {
                char ch = s.charAt(i);
                i++;
                int count = cur.get(ch);
                cur.put(ch, --count);
                if (counts.containsKey(ch) && count < counts.get(ch)) {
                    len++;
                }
            }

            if (len == 0 && min > j - i + 1) {
                min = j - i + 1;
                minStr = s.substring(i, j + 1);
            }
        }
        return minStr;
    }

    public List<List<Integer>> sulotion78(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        subset(0, nums, res, new ArrayList<>());
        return res;
    }

    public void subset(int cur, int[] nums, List<List<Integer>> res, List<Integer> t) {
        if (cur == nums.length) {
            res.add(new ArrayList<Integer>(t));
            return;
        }
        t.add(nums[cur]);
        subset(cur + 1, nums, res, t);
        t.remove(t.size() - 1);
        subset(cur + 1, nums, res, t);
    }

    public boolean sulotion79(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        Stack<Character> stack = new Stack<>();
        for (int i = word.length() - 1; i >= 0; i--) {
            stack.push(word.charAt(i));
        }
        boolean[][] used = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                used[i][j] = false;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfsExist(board, stack, used, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsExist(char[][] board, Stack<Character> stack, boolean[][] used, int i, int j) {
        char ch = stack.pop();
        if (ch != board[i][j]) {
            stack.push(ch);
            return false;
        }
        if (stack.isEmpty()) {
            return true;
        }
        used[i][j] = true;
        if (i > 0 && !used[i - 1][j] && dfsExist(board, stack, used, i - 1, j)) {
            return true;
        }
        if (i < board.length - 1 && !used[i + 1][j] && dfsExist(board, stack, used, i + 1, j)) {
            return true;
        }
        if (j > 0 && !used[i][j - 1] && dfsExist(board, stack, used, i, j - 1)) {
            return true;
        }
        if (j < board[0].length - 1 && !used[i][j + 1] && dfsExist(board, stack, used, i, j + 1)) {
            return true;
        }
        stack.push(ch);
        used[i][j] = false;
        return false;
    }

    public int sulotion84(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int len = heights.length;
        int[] leftMax = new int[len];
        int[] rightMax = new int[len];
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                leftMax[i] = -1;
            } else {
                leftMax[i] = stack.peek();
            }
            stack.push(i);
        }
        stack.clear();
        for (int i = len - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                rightMax[i] = len;
            } else {
                rightMax[i] = stack.peek();
            }
            stack.push(i);
        }
        int max = 0;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, heights[i] * (rightMax[i] - leftMax[i] - 1));
        }
        return max;
    }

    public int sulotion85(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }
        int m = matrix.length, n = matrix[0].length;
        int[][] width = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    width[i][j] = 0;
                } else if (j == 0) {
                    width[i][j] = 1;
                } else {
                    width[i][j] = width[i][j - 1] + 1;
                }
            }
        }
        int[][] upMax = new int[m][n];
        for (int j = 0; j < n; j++) {
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < m; i++) {
                while (!stack.isEmpty() && width[stack.peek()][j] >= width[i][j]) {
                    stack.pop();
                }
                if (stack.isEmpty()) {
                    upMax[i][j] = -1;
                } else {
                    upMax[i][j] = stack.peek();
                }
                stack.push(i);
            }
        }
        int[][] downMax = new int[m][n];
        for (int j = 0; j < n; j++) {
            Stack<Integer> stack = new Stack<>();
            for (int i = m - 1; i >= 0; i--) {
                while (!stack.isEmpty() && width[stack.peek()][j] >= width[i][j]) {
                    stack.pop();
                }
                if (stack.isEmpty()) {
                    downMax[i][j] = m;
                } else {
                    downMax[i][j] = stack.peek();
                }
                stack.push(i);
            }
        }
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                max = Math.max(max, width[i][j] * (downMax[i][j] - upMax[i][j] - 1));
            }
        }
        return max;
    }

    public static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> sulotion94(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        midTrace(root, res);
        return res;
    }

    private void midTrace(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        midTrace(root.left, res);
        res.add(root.val);
        midTrace(root.right, res);
    }

    public int sulotion96(int n) {
        int[] nums = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            if (i == 0 || i == 1) {
                nums[i] = 1;
                continue;
            }
            nums[i] = 0;
            for (int j = 0; j < i; j++) {
                nums[i] += nums[j] * nums[i - j - 1];
            }
        }
        return nums[n];
    }

    public boolean sulotion98(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        Integer min = null;
        while (!stack.isEmpty()) {
            TreeNode treeNode = stack.pop();
            if (treeNode.left == null && treeNode.right == null) {
                if (min != null && treeNode.val <= min) {
                    return false;
                }
                min = treeNode.val;
            } else {
                if (treeNode.right != null) {
                    stack.push(treeNode.right);
                }
                stack.push(treeNode);
                if (treeNode.left != null) {
                    stack.push(treeNode.left);
                }
                treeNode.left = null;
                treeNode.right = null;
            }
        }
        return true;
    }

    public boolean sulotion101(TreeNode root) {
        return isSymmetric(root, root);
    }

    public boolean isSymmetric(TreeNode p, TreeNode q) {
        if (p.val != q.val) {
            return false;
        }
        return ((p.left == null && q.right == null) || (p.left != null && q.right != null && isSymmetric(p.left, q.right))) && (
            (p.right == null && q.left == null) || (p.right != null && q.left != null && isSymmetric(p.right, q.left)));
    }

    public List<List<Integer>> sulotion102(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Queue<TreeNode> newQueue = new LinkedList<>();
            List<Integer> level = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                if (treeNode.left != null) {
                    newQueue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    newQueue.offer(treeNode.right);
                }
                level.add(treeNode.val);
            }
            queue = newQueue;
            res.add(level);
        }
        return res;
    }

    public int sulotion104(TreeNode root) {
        return maxDepth(root);
    }

    private int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public TreeNode sulotion105(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inPos = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inPos.put(inorder[i], i);
        }
        return buildTree(preorder, inorder, 0, 0, preorder.length, inPos);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int inStart, int len, Map<Integer, Integer> inPos) {
        if (len == 0) {
            return null;
        }
        int val = preorder[preStart];
        int pos = inPos.get(val);
        TreeNode treeNode = new TreeNode(val);
        treeNode.left = buildTree(preorder, inorder, preStart + 1, inStart, pos - inStart, inPos);
        treeNode.right = buildTree(preorder, inorder, preStart + 1 + pos - inStart, pos + 1, len - 1 - pos + inStart, inPos);
        return treeNode;
    }

    public void sulotion114(TreeNode root) {
        List<TreeNode> preOrder = new ArrayList<>();
        preOrder(root, preOrder);
        for (int i = 1; i < preOrder.size(); i++) {
            TreeNode prev = preOrder.get(i - 1), curr = preOrder.get(i);
            prev.left = null;
            prev.right = curr;
        }
    }

    private void preOrder(TreeNode root, List<TreeNode> preOrder) {
        if (root == null) {
            return;
        }
        preOrder.add(root);
        preOrder(root.left, preOrder);
        preOrder(root.right, preOrder);
    }

    public int sulotion121(int[] prices) {
        int min = Integer.MAX_VALUE, profit = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            profit = Math.max(profit, prices[i] - min);
        }
        return profit;
    }

    int maxPathDepth = 0;

    public int sulotion124(TreeNode root) {
        maxPath(root);
        return maxPathDepth;
    }

    public int maxPath(TreeNode root) {
        if (root.left == null && root.right == null) {
            maxPathDepth = Math.max(maxPathDepth, root.val);
            return root.val;
        }
        int left = root.left == null ? 0 : Math.max(maxPath(root.left), 0);
        int right = root.right == null ? 0 : Math.max(maxPath(root.right), 0);
        maxPathDepth = Math.max(maxPathDepth, root.val);
        maxPathDepth = Math.max(maxPathDepth, root.val + left);
        maxPathDepth = Math.max(maxPathDepth, root.val + right);
        maxPathDepth = Math.max(maxPathDepth, root.val + left + right);
        return root.val + Math.max(left, right);
    }

    public int sulotion128(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        for (int num : nums) {
            if (!set.contains(num - 1)) {
                int curr = 1;
                while (set.contains(++num)) {
                    curr++;
                }
                max = Math.max(max, curr);
            }
        }
        return max;
    }

    public int sulotion136(int[] nums) {
        int one = 0;
        for (int num : nums) {
            one ^= num;
        }
        return one;
    }

    public boolean sulotion139(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        for (int i = 0; i < s.length() + 1; i++) {
            if (i == 0) {
                dp[i] = true;
            } else {
                for (String word : wordDict) {
                    if (i - word.length() >= 0 && dp[i - word.length()] && word.equals(s.substring(i - word.length(), i))) {
                        dp[i] = true;
                        break;
                    }
                }
            }
        }
        return dp[s.length()];
    }

    public boolean sulotion141(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode p = head, q = head;
        while (p != null && q != null) {
            q = q.next;
            if (q != null) {
                q = q.next;
            }
            p = p.next;
            if (p == q) {
                return true;
            }
        }
        return false;
    }

    public ListNode sulotion142(ListNode head) {
        ListNode p = head, q = head;
        while (p != null && q != null) {
            q = q.next;
            if (q != null) {
                q = q.next;
            }
            p = p.next;
            if (p == q) {
                break;
            }
        }
        if (p == null || q == null) {
            return null;
        }
        p = head;
        while (p != q) {
            p = p.next;
            q = q.next;
        }
        return p;
    }

    /**
     * sulotion146
     */
    class LRUCache {

        class LRUNode {
            int key;
            int val;
            LRUNode prev;
            LRUNode next;

            LRUNode(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private Map<Integer, LRUNode> map;

        private LRUNode head;

        private LRUNode tail;

        private int used;

        private int capacity;

        public LRUCache(int capacity) {
            this.map = new HashMap<>();
            this.head = new LRUNode(-1, -1);
            this.tail = new LRUNode(-1, -1);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.used = 0;
            this.capacity = capacity;
        }

        public int get(int key) {
            LRUNode node = map.get(key);
            if (node == null) {
                return -1;
            }
            moveToHead(node);
            return node.val;
        }

        private void moveToHead(LRUNode node) {
            if (node.prev != null) {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void evict() {
            while (used > capacity) {
                LRUNode evictNode = tail.prev;
                evictNode.prev.next = tail;
                tail.prev = evictNode.prev;
                map.remove(evictNode.key);
                used--;
            }
        }

        public void put(int key, int value) {
            LRUNode node = map.get(key);
            if (node == null) {
                node = new LRUNode(key, value);
                moveToHead(node);
                used++;
                evict();
                map.put(key, node);
            } else {
                moveToHead(node);
                node.val = value;
            }
        }
    }

    public ListNode sulotion148(ListNode head) {
        if (head == null) {
            return null;
        }
        return mergeSort(head);
    }

    private ListNode mergeSort(ListNode head) {
        if (head.next == null) {
            return head;
        }
        ListNode p = head, q = head, prev = null;
        while (p != null && q != null) {
            prev = p;
            p = p.next;
            q = q.next;
            if (q != null) {
                q = q.next;
            }
        }
        prev.next = null;
        return merge148(mergeSort(head), mergeSort(p));
    }

    private ListNode merge148(ListNode p, ListNode q) {
        ListNode head = new ListNode();
        ListNode curr = head;
        while (p != null || q != null) {
            if (p == null) {
                curr.next = q;
                q = q.next;
            } else if (q == null) {
                curr.next = p;
                p = p.next;
            } else if (p.val < q.val) {
                curr.next = p;
                p = p.next;
            } else {
                curr.next = q;
                q = q.next;
            }
            curr = curr.next;
        }
        return head.next;
    }

    public int sulotion152(int[] nums) {
        int[] max = new int[nums.length + 1];
        int[] min = new int[nums.length + 1];
        int maxx = Integer.MIN_VALUE;
        max[0] = 0;
        min[0] = 0;
        for (int i = 0; i < nums.length; i++) {
            if (max[i] == 0 || nums[i] == 0) {
                max[i + 1] = nums[i];
                min[i + 1] = nums[i];
            } else {
                max[i + 1] = max[i] * nums[i];
                max[i + 1] = Math.max(max[i + 1], min[i] * nums[i]);
                max[i + 1] = Math.max(max[i + 1], nums[i]);
                min[i + 1] = max[i] * nums[i];
                min[i + 1] = Math.min(min[i + 1], min[i] * nums[i]);
                min[i + 1] = Math.min(min[i + 1], nums[i]);
            }
            maxx = Math.max(maxx, max[i + 1]);
        }
        return maxx;
    }

    class MinStack {

        Stack<Integer> stack;

        Stack<Integer> minStack;

        /** initialize your data structure here. */
        public MinStack() {
            minStack = new Stack<>();
            stack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val < getMin()) {
                minStack.push(stack.size() - 1);
            }
        }

        public void pop() {
            stack.pop();
            if (minStack.peek() >= stack.size()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return stack.get(minStack.peek());
        }
    }

    public ListNode sulotion160(ListNode headA, ListNode headB) {
        ListNode p = headA, q = headB;
        int lenA = 0, lenB = 0;
        while (p != null) {
            p = p.next;
            lenA++;
        }
        while (q != null) {
            q = q.next;
            lenB++;
        }
        int diff = lenA - lenB;
        p = headA;
        q = headB;
        if (diff > 0) {
            while (diff-- > 0) {
                p = p.next;
            }
        } else {
            while (diff++ < 0) {
                q = q.next;
            }
        }
        while (p != q && p != null && q != null) {
            p = p.next;
            q = q.next;
        }
        return p;
    }

    public int sulotion169(int[] nums) {
        int count = 0;
        int candidate = 0;
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (candidate == num) {
                count++;
            } else {
                count--;
            }
        }
        return candidate;
    }

    public int sulotion198(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                dp[i] = nums[i];
            } else if (i == 1) {
                dp[i] = Math.max(nums[i], nums[i - 1]);
            } else {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
            }
        }
        return dp[nums.length - 1];
    }

    public int[] solution239(int[] nums, int k) {
        Deque<Integer> queue = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0, j = 0; i < res.length; i++) {
            while (!queue.isEmpty() && queue.getFirst() < i) {
                queue.pollFirst();
            }
            while (j < i + k) {
                while (!queue.isEmpty() && nums[queue.getLast()] < nums[j]) {
                    queue.pollLast();
                }
                queue.offerLast(j);
                j++;
            }
            res[i] = nums[queue.getFirst()];
        }
        return res;
    }

    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "null";
            }
            Deque<TreeNode> deque = new LinkedList<>();
            deque.offerLast(root);
            List<String> list = new LinkedList<>();
            list.add(String.valueOf(root.val));
            while (!deque.isEmpty()) {
                TreeNode node = deque.pollFirst();
                if (node.left != null) {
                    deque.offerLast(node.left);
                    list.add(String.valueOf(node.left.val));
                } else {
                    list.add("null");
                }
                if (node.right != null) {
                    deque.offerLast(node.right);
                    list.add(String.valueOf(node.right.val));
                } else {
                    list.add("null");
                }
            }
            StringBuilder sb = new StringBuilder();
            int size = list.size();
            while (list.get(size - 1).equals("null")) {
                size--;
            }
            for (int i = 0; i < size; i++) {
                sb.append(list.get(i));
                if (i != size - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if (data.equals("null")) {
                return null;
            }
            List<String> list = Arrays.asList(data.split(","));
            Deque<TreeNode> deque = new LinkedList();
            TreeNode root = new TreeNode(Integer.parseInt(list.get(0)));
            deque.offerLast(root);
            int i = 1;
            while (!deque.isEmpty()) {
                TreeNode parent = deque.pollFirst();
                String leftVal = i >= list.size() ? "null" : list.get(i);
                String rightVal = i + 1 >= list.size() ? "null" : list.get(i + 1);
                if (!leftVal.equals("null")) {
                    parent.left = new TreeNode(Integer.parseInt(leftVal));
                    deque.offerLast(parent.left);
                }
                if (!rightVal.equals("null")) {
                    parent.right = new TreeNode(Integer.parseInt(rightVal));
                    deque.offerLast(parent.right);
                }
                i += 2;
            }
            return root;
        }
    }

    public List<String> solution301(String s) {
        int left = 0, leftStart = -1, rightEnd = -1, right = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                if (leftStart == -1) {
                    rightEnd = i;
                }
                if (left == 0) {
                    leftStart = i;
                }
                left++;
            } else if (ch == ')') {
                if (left > 0) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        Set<String> res = new HashSet<>();
        dfsCollect(s, 0, left, right, leftStart, rightEnd, new StringBuilder(), res);
        return new ArrayList<>(res);
    }

    private void dfsCollect(String s, int idx, int left, int right, int leftStart, int rightEnd, StringBuilder sb, Set<String> res) {
        if (idx >= s.length()) {
            if (left == 0 && right == 0) {
                res.add(sb.toString());
            }
            return;
        }

        if (leftStart >= 0 && idx >= leftStart && right > 0) {
            return;
        }

        char ch = s.charAt(idx);

        if ((rightEnd < 0 || idx < rightEnd) && ch == ')') {
            dfsCollect(s, idx + 1, left, right - 1, leftStart, rightEnd, sb, res);
            return;
        }

        if ((leftStart < 0 || idx < leftStart) && ch == ')' && right > 0) {
            dfsCollect(s, idx + 1, left, right - 1, leftStart, rightEnd, sb, res);
        } else if (idx >= leftStart && ch == '(' && left > 0) {
            dfsCollect(s, idx + 1, left - 1, right, leftStart, rightEnd, sb, res);
        }

        sb.append(ch);
        dfsCollect(s, idx + 1, left, right, leftStart, rightEnd, sb, res);
        sb.deleteCharAt(sb.length() - 1);
    }

    public int solution312(int[] nums) {
        int n = nums.length + 2;
        int[][] maxCoins = new int[n][n];
        int[] vals = new int[n];
        vals[0] = vals[n - 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            vals[i + 1] = nums[i];
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                for (int k = i + 1; k < j; k++) {
                    int sum = vals[k] * vals[i] * vals[j];
                    sum += maxCoins[i][k];
                    sum += maxCoins[k][j];
                    maxCoins[i][j] = Math.max(sum, maxCoins[i][j]);
                }
            }
        }
        return maxCoins[0][maxCoins.length - 1];
    }

    class LFUCache {

        private int minFre;

        private int capacity;

        private Map<Integer, LFUNode> cache;

        private Map<Integer, LFUNode> freMap;

        class LFUNode {
            int key;
            int value;
            int fre;
            LFUNode prev;
            LFUNode next;
        }

        public LFUCache(int capacity) {
            this.minFre = 0;
            this.capacity = capacity;
            this.cache = new HashMap<>();
            this.freMap = new HashMap<>();
        }

        public int get(int key) {
            LFUNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            addFre(node);
            return node.value;
        }

        private void addFre(LFUNode node) {
            if (node.prev == null) {
                freMap.put(node.fre, node.next);
            } else {
                node.prev.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (minFre == node.fre && freMap.get(node.fre) == null) {
                minFre++;
            }
            node.fre++;
            node.next = freMap.get(node.fre);
            node.prev = null;
            if (node.next != null) {
                node.next.prev = node;
            }
            freMap.put(node.fre, node);
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            LFUNode node = cache.get(key);
            if (node != null) {
                node.value = value;
                addFre(node);
                return;
            }
            node = new LFUNode();
            node.key = key;
            node.value = value;
            node.fre = 1;
            cache.put(key, node);
            if (cache.size() > capacity) {
                //驱逐
                LFUNode exict = freMap.get(minFre);
                while (exict.next != null) {
                    exict = exict.next;
                }
                cache.remove(exict.key);
                if (exict.prev == null) {
                    freMap.remove(minFre);
                } else {
                    exict.prev.next = null;
                }
            }
            minFre = 1;
            node.next = freMap.get(minFre);
            if (node.next != null) {
                node.next.prev = node;
            }
            freMap.put(minFre, node);
        }
    }
}
