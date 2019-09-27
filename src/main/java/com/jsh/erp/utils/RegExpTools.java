package com.jsh.erp.utils;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adm on 2015/12/14.
 *
 * @author yubiao
 *         <p/>
 *         mysql匹配正则表达式
 */
public class RegExpTools {
    /**
     * @param search 模糊匹配字符串数组
     */
    public static String regexp(List<String> search) {
        if (search == null || search.isEmpty())
            return null;
        String regexp = "";
        for (String s : search) {
            if (!regexp.isEmpty()) {
                regexp = regexp + "|";
            }
            regexp = regexp + ".*";
            regexp = regexp + s.replaceAll("\\.", "\\\\.");
            regexp = regexp + ".*";
        }
        return regexp;
    }

    /**
     * @param key    json字段key
     * @param search 模糊匹配字符串数组
     *               json的mysql匹配正则表达式
     */
    public static String regexp(String key, List<String> search) {
        if (search == null || search.isEmpty())
            return null;
        StringBuilder sb = new StringBuilder();
        for (String s : search) {
            if (sb.length() == 0) {
                sb.append(".*\\\"").append(key).append("\\\":\\\"[a-zA-Z0-9]*(");
            } else {
                sb.append("|");
            }
            sb.append(s);
        }
        sb.append(")[a-zA-Z0-9]*\\\".*");
        return sb.toString();
    }

    public static class RegExp {
        public static final String ANY = ".*";
        public static final String QUOTE = "\\\"";
        public static final String LFT_PAREN = "(";
        public static final String RHT_PAREN = ")";
        public static final String COLON = ":";
        public static final String OR = "|";

        private final StringBuilder builder = new StringBuilder();

        public RegExp any() {
            builder.append(ANY);
            return this;
        }

        public RegExp lftParen() {
            builder.append(LFT_PAREN);
            return this;
        }

        public RegExp rhtParen() {
            builder.append(RHT_PAREN);
            return this;
        }

        public RegExp colon() {
            builder.append(COLON);
            return this;

        }

        public RegExp quote() {
            builder.append(QUOTE);
            return this;
        }

        public RegExp quote(String str) {
            Assert.notNull(str, "str为空");
            builder.append(QUOTE).append(str).append(QUOTE);
            return this;
        }

        public RegExp value(String str) {
            Assert.notNull(str, "str为空");
            builder.append(str);
            return this;
        }

        public RegExp or() {
            builder.append(OR);
            return this;
        }

        public RegExp or(List<String> values) {
            Assert.notEmpty(values, "values必须非空");
            lftParen();
            boolean first = true;
            for (String value : values) {
                if (first) {
                    builder.append(value);
                    first = false;
                } else {
                    builder.append(OR).append(value);
                }
            }
            rhtParen();
            return this;
        }

        @Override
        public String toString() {
            return builder.toString();
        }

        public static void main(String[] args) {
            List<String> values = new ArrayList<String>();

            values.add("310");
            values.add(String.valueOf(2));
            values.add(String.valueOf(3));

            RegExp exp = new RegExp();

            exp.any();
            exp.quote("fullKbNum").colon()
                    .quote()
                    .value("[a-zA-Z0-9]*").or(values).value("[a-zA-Z0-9]*")
                    .quote();
            exp.or();
            exp.quote("gbId[a-f0-9-]{36}").colon()
                    .quote()
                    .value("[0-9]*").or(values).value("[0-9]*")
                    .quote();
            exp.any();

            System.out.println(exp);
        }

    }
}
