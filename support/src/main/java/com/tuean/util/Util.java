package com.tuean.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
public class Util {


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
            log.info("getFileContent error: {}", var.getMessage());
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



    public static void main(String[] args) {
        System.out.println(generateRandomString(25));
//        File file = new File("D:\\IdeaProjects\\punch\\support\\src\\main\\resources\\build");
//        System.out.println(file);
    }



}
