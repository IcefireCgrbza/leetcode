package com.feimao.leecode;

import java.util.*;

public class Main {

    public static void main(String[] args) {
//        List<List<Integer>> arrs = new ArrayList<>();
//        add(arrs, new int[] {1, 3});
//        add(arrs, new int[] {2, 6});
//        add(arrs, new int[] {3, 19});
//        add(arrs, new int[] {8, 10});
//        add(arrs, new int[] {15, 18});
//        merge(arrs);
//        new Main().isValid("((())))()()");
        new Main().isValidV2("[{}]()");
    }

    public static List<List<Integer>> merge(List<List<Integer>> arrs) {
        if (arrs.size() == 0) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new ArrayList<>();
        int[] cur = new int[2];
        cur[0] = arrs.get(0).get(0);
        cur[1] = arrs.get(0).get(1);
        for (List<Integer> arr : arrs.subList(1, arrs.size())) {
            if (cur[1] >= arr.get(0)) {
                cur[1] = Math.max(arr.get(1), cur[1]);
            } else {
                add(res, cur);
                cur[0] = arr.get(0);
                cur[1] = arr.get(1);
            }
        }
        add(res, cur);
        return res;
    }

    private static void add(List<List<Integer>> res, int[] cur) {
        List<Integer> add = new ArrayList<>();
        add.add(cur[0]);
        add.add(cur[1]);
        res.add(add);
    }

    public boolean isValid(String str) {
        int count = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                if (count <= 0) {
                    return false;
                }
                count--;
            }
        }
        return count == 0;
    }

    public boolean isValidV2(String str) {
        Map<Character, Character> pair = new HashMap<>();
        pair.put('(', ')');
        pair.put('[', ']');
        pair.put('{', '}');
        LinkedList<Character> stack = new LinkedList<>();
        for (char ch : str.toCharArray()) {
            if (pair.containsKey(ch)) {
                stack.addLast(ch);
            } else if (!stack.isEmpty() && pair.get(stack.peekLast()).equals(ch)) {
                stack.pollLast();
            } else {
                return false;
            }
        }
        return stack.isEmpty();
    }

    class FileIndex implements Comparable<FileIndex> {
        int fileIdx;    //文件索引
        long idx;        //当前文件的位置
        long timestamp;  //时间戳
        String log;     //日志

        @Override
        public int compareTo(FileIndex o) {
            return Comparator.comparingLong(timestamp, o.timestamp);
        }
    }
}
