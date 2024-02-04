package com.tuean.util;

import com.tuean.consts.Empty;
import com.tuean.entity.MarkdownFile;
import com.tuean.entity.blog.Post;
import com.tuean.entity.blog.PostItem;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tuean.consts.Const.URL_PATH_STR;

public class Util {

    private static Logger logger = LoggerFactory.getLogger(Util.class);


    public static String pureUrl(String uri) {
        if (uri == null) return null;
        int firstQuestionMark = uri.indexOf("?");
        return firstQuestionMark > 0 ? uri.substring(0, firstQuestionMark) : uri;
    }

//    public static String pureUrl(String url) {
//        if (url == null) return null;
//        int index = 0, length = url.length();
//        while (index < length) {
//            if (url.charAt(index) == '?') {
//                break;
//            }
//        }
//        return index == 0 ? "" : url.substring(0, index);
//    }

    public static String transferFileId(String fileName) {
        return String.valueOf(fileName.hashCode());
    }


//    public Map<String, Integer> fill() {
//        Map<String, Integer> result = new HashMap<>();
//        List<String> toAllocate = List.of("client1", "client2");
//        List<Integer> employees = List.of(1, 2, 3, 4, 5, 6);
//        int average = employees.stream().mapToInt(Integer::intValue).sum();
//        List<Integer> toAllocateEmployee = employees.stream().map(n -> n - average).filter(n -> n < 0).toList();
//        while (!toAllocateEmployee.isEmpty()) {
//            toAllocateEmployee = toAllocateEmployee.stream().filter(n -> n < 0).sorted().toList();
//            result.put(toAllocate.get(0), toAllocateEmployee.get(0));
//            toAllocate.remove(0);
//            toAllocateEmployee.set(0, toAllocateEmployee.get(0) - 1);
//        }
//
//        if (!toAllocate.isEmpty()) {
//            int e = toAllocate.size() / employees.size();
//            List<List<String>> each = Lists.partition(toAllocate, e);
//            for (int i = 0; i < each.size(); i++) {
//                each.get(i).forEach(n -> result.put(n, employees.get(i)));
//            }
//        }
//
//        return result;
//    }

    private static final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int size) {
        StringBuilder sb = new StringBuilder(size);
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(ALPHA_NUMERIC.length());
            char randomChar = ALPHA_NUMERIC.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static String md5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytes);

            // Convert the byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileContent(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (Exception var) {
//            logger.info("getFileContent error: {}", var.getMessage());
            return null;
        }
    }

    public static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static String md5(String input) {
        return md5(input.getBytes());
    }

    public static long fileSize(Path path) throws IOException {
        return Files.size(path);
    }

    public static boolean isBlank(String input) {
        if (input == null) return true;
        if (input.trim().length() == 0) return true;
        return false;
    }

    public static Post convertFile2Post(MarkdownFile file) {
        Map<String, Object> properties = getStringObjectMap(file);
        String title = obj2Str(properties.getOrDefault("title", file.getFileName()));
        String content = getMarkdownContent(file);
        return new Post(title, content);
    }

    /**
     *
     * ---
     * title: HttpMessageConverter
     * date: "2021-05-20"
     * tags:
     *   - spring
     * image: https://i.loli.net/2021/05/20/FpWJ9MoRzDeKr7N.jpg
     * author: tuean
     * summary: "spring中http消息体转化工具"
     * ---
     *
     * @param file
     * @return
     */
    public static PostItem convertFile2PostItem(MarkdownFile file) {
        Map<String, Object> properties = getStringObjectMap(file);

        PostItem postItem = new PostItem();
        postItem.setAuthor(obj2Str(properties.getOrDefault("author", file.getAuthor())));
        postItem.setDescription(obj2Str(properties.getOrDefault("summary", "")));
        postItem.setTitle(obj2Str(properties.getOrDefault("title", file.getFileName())));
        postItem.setName(file.getFileName());
        postItem.setTags((List) properties.getOrDefault("tags", new ArrayList<>()));
        String dateStr = null;
        switch (properties.get("date")) {
            case Date d -> dateStr = Util.date2String(d);
            case String s -> dateStr = s;
            default -> dateStr = "";
        };
        postItem.setPublishDate(StringUtil.isNullOrEmpty(dateStr) ? unix2String(file.getLastModified()) : dateStr);
        return postItem;
    }

    private static Map<String, Object> getStringObjectMap(MarkdownFile file) {
        String[] contents = file.getContent().split("\r\n");
        if  (contents.length < 2) {
            contents = file.getContent().split("\n");
        }
        int start = 0;
        StringBuilder sb = new StringBuilder();
        for (String line : contents) {
            if (line.startsWith("---")) {
                start++; continue;
            }
            if (start > 1) break;
            sb.append(line); sb.append("\r\n");
        }

        Yaml yaml = new Yaml();
        Map<String, Object> properties = yaml.load(sb.toString().replace("\\t(TAB)", ""));
        if (properties == null) properties = new HashMap<>();
        return properties;
    }

    private static String getMarkdownContent(MarkdownFile file) {
        String[] contents = file.getContent().split("\r\n");
        if  (contents.length < 2) {
            contents = file.getContent().split("\n");
        }
        int start = 0;
        StringBuilder sb = new StringBuilder();
        for (String line : contents) {
            if (line.startsWith("---")) {
                start++; continue;
            }
            if (start > 1) sb.append(line).append("\r\n");
        }
        return sb.toString();
    }

    public static String unix2String(Long unixTime) {
        if (unixTime == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Instant.ofEpochMilli(unixTime).atZone(ZoneId.systemDefault()).format(formatter);
    }

    public static String date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String obj2Str(Object c) {
        return c == null ? "" : String.valueOf(c);
    }

    public static String convert2path(List<String> prefixList, String fileName) {
        if (CollectionUtils.isEmpty(prefixList)) {
            return String.join(URL_PATH_STR, prefixList) + URL_PATH_STR + fileName;
        } else {
            return URL_PATH_STR + String.join(URL_PATH_STR, prefixList) + URL_PATH_STR + fileName;
        }
    }


    public static String encodeChinese(String source) {
//        boolean containsChinese = source.matches(".*[\\u4E00-\\u9FA5]+.*");
//        if (!containsChinese) return source;
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcher = pattern.matcher(source);

        while (matcher.find()) {
            String chineseChar = matcher.group();
            String encodedChar = null;
            try {
                encodedChar = URLEncoder.encode(chineseChar, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            matcher.appendReplacement(sb, encodedChar);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


    public static boolean isEmpty(Class[] dependencies) {
        if (dependencies == null) return true;
        if (dependencies.length == 0) return true;
        if (dependencies.length == 1 && Empty.class.equals(dependencies[0])) return true;
        return false;
    }



    public static void main(String[] args) {
        System.out.println(generateRandomString(25));
//        File file = new File("D:\\IdeaProjects\\punch\\support\\src\\main\\resources\\build");
//        System.out.println(file);
    }



}
