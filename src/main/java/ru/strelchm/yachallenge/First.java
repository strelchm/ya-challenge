package ru.strelchm.yachallenge;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class First {
  private static final Scanner scanner = new Scanner(System.in);
  public static final String CORRECT_OUTPUT_NAME = "correct";
  public static final String PRESENT_OUTPUT_NAME = "present";
  public static final String ABSENT_OUTPUT_NAME = "absent";
  public static final char CORRECT_OUTPUT_CODE = 'c';
  public static final char PRESENT_OUTPUT_CODE = 'p';
  public static final char ABSENT_OUTPUT_CODE = 'a';

  static class Foo {

    public Foo(Character c, Integer idx) {
      this.c = c;
      this.idx = idx;
    }

    private Character c;
    private Integer idx;
  }

  public static void main(String[] args) {
    String neededWord = scanner.nextLine();
    String factualWord = scanner.nextLine();
    Set<Character> correct = new HashSet<>();
    Map<Character, List<Integer>> factualIndexes = new HashMap<>();
    Map<Character, List<Integer>> candidates = new HashMap<>();
    int n = neededWord.length();
    char[] result = new char[n];

    AtomicInteger counter = new AtomicInteger();
    Stream.generate(counter::getAndIncrement).parallel().forEach(i -> {
          char needed = neededWord.charAt(i);
          char factual = factualWord.charAt(i);
          if (needed == factual) {
            result[i] = CORRECT_OUTPUT_CODE;
            correct.add(factual);
          } else {
            List<Integer> l = factualIndexes.getOrDefault(needed, new ArrayList<>());
            l.add(i);
            factualIndexes.put(needed, l);
            List<Integer> l1 = candidates.getOrDefault(factual, new ArrayList<>());
            l1.add(i);
            candidates.put(factual, l1);
          }
        });

        candidates.entrySet().parallelStream().forEach(v -> {
          List<Integer> neededArr = factualIndexes.get(v.getKey());
          v.getValue().forEach(integer -> {
            if (neededArr != null && !neededArr.isEmpty()) {
              result[integer] = PRESENT_OUTPUT_CODE;
              neededArr.remove(neededArr.size() - 1);
            } else {
              result[integer] = ABSENT_OUTPUT_CODE;
            }
          });
        });

    for (char c : result) {
      if (c == CORRECT_OUTPUT_CODE) {
        System.out.println(CORRECT_OUTPUT_NAME);
      } else if (c == PRESENT_OUTPUT_CODE) {
        System.out.println(PRESENT_OUTPUT_NAME);
      } else if (c == ABSENT_OUTPUT_CODE) {
        System.out.println(ABSENT_OUTPUT_NAME);
      } else {
        throw new UnsupportedOperationException();
      }
    }
  }
}