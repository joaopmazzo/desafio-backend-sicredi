package br.com.joaopmazzo.desafio_backend_sicredi.utils;

import java.util.stream.IntStream;

public class CpfUtils {

    public static String generateValidCpf() {
        int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        String baseCpf = IntStream.range(0, 9)
                .mapToObj(i -> String.valueOf((int) (Math.random() * 10)))
                .reduce("", String::concat);

        int firstDigit = calculateCpfDigit(baseCpf, weights);
        int secondDigit = calculateCpfDigit(baseCpf + firstDigit, new int[]{11, 10, 9, 8, 7, 6, 5, 4, 3, 2});

        return baseCpf + firstDigit + secondDigit;
    }

    private static int calculateCpfDigit(String baseCpf, int[] weights) {
        int sum = IntStream.range(0, baseCpf.length())
                .map(i -> Character.getNumericValue(baseCpf.charAt(i)) * weights[i])
                .sum();
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

}
