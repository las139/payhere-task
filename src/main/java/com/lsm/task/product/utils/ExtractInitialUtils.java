package com.lsm.task.product.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractInitialUtils {

    private static final char[] INITIAL_LIST = {
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    public static List<Map<String, String>> extractInitial(String input) {
        String[] words = input.split(" ");
        List<Map<String, String>> initials = new ArrayList<>();
        for (String word : words) {
            Map<String, String> map = new HashMap<>();
            map.put("word", word);
            map.put("initial", extractWord(word));
            initials.add(map);
        }

        return initials;
    }

    public static String extractWord(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            // 한글 문자인 경우 처리
            if (ch >= '가' && ch <= '힣') {
                // 유니코드 표준에 따라 '가'에서 글자를 뺀 값으로 초성을 구함
                int chosungIndex = (ch - '가') / (21 * 28);
                result.append(INITIAL_LIST[chosungIndex]);
            } else {
                // 한글이 아닌 경우 그대로 추가
                result.append(ch);
            }
        }

        return result.toString();
    }
}
