package com.jsh.erp.utils;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @Author jishenghua
 * @Date 2024-01-08 23:03
 */
@Slf4j
public class PinYinUtil {

    public static String getFirstLettersLo(String ChineseLanguage) {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    public static String getFirstLetters(String chineseLanguage, HanyuPinyinCaseType caseType) {
        char[] cl_chars = chineseLanguage.trim().toCharArray();
        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出拼音全部大写
        defaultFormat.setCaseType(caseType);
        // 不带声调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            for (char cl_char : cl_chars) {
                String str = String.valueOf(cl_char);
                if (str.matches("[\u4e00-\u9fa5]+")) {
                    // 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    pinyin.append(PinyinHelper.toHanyuPinyinStringArray(cl_char, defaultFormat)[0].substring(0, 1));
                } else if (str.matches("[0-9]+")) {
                    // 如果字符是数字,取数字
                    pinyin.append(cl_char);
                } else if (str.matches("[a-zA-Z]+")) {
                    // 如果字符是字母,取字母
                    pinyin.append(cl_char);
                } else {
                    // 否则不转换
                    //如果是标点符号的话，带着
                    pinyin.append(cl_char);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            log.error(chineseLanguage + "转拼音失败！", e);
        }
        return pinyin.toString();
    }
}
