package ru.strelchm.yachallenge;

import java.util.*;

public class Second {
  private final static Scanner scanner = new Scanner(System.in);

  public static class Info {
    String name;
    int task;
    int penalty;

    public Info(String name, int task, int penalty) {
      this.name = name;
      this.task = task;
      this.penalty = penalty;
    }
  }

  public static void main(String[] args) {
    int n = scanner.nextInt();
    Map<String, Integer> limit = new HashMap<>();
    for (int i = 0; i < n; ++i) {
      String[] now = scanner.next().split(",");
      limit.put(now[0], Integer.parseInt(now[1]));
    }
    int m = scanner.nextInt();
    Map<String, ArrayList<Info>> all = new HashMap<>();
    for (int i = 0; i < m; ++i) {
      String candidate = scanner.next();
      String[] now = candidate.split(",");
      String title = now[1];
      int task = Integer.parseInt(now[2]);
      int penalty = 1000000000 - Integer.parseInt(now[3]);
      ArrayList<Info> current = all.getOrDefault(title, new ArrayList<>());
      current.add(new Info(now[0], task, penalty));
      all.put(title, current);
    }

    all.entrySet().stream().flatMap(v -> v.getValue().parallelStream().sorted((o1, o2) -> {
              if (o1.task != o2.task) {
                return o2.task - o1.task;
              }
              if (o1.penalty != o2.penalty) {
                return o2.penalty - o1.penalty;
              }
              return o2.name.compareTo(o1.name);
            })
            .limit(Math.min(limit.get(v.getKey()), v.getValue().size()))
            .map(i -> i.name))
        .sorted(Comparator.naturalOrder())
        .forEach(System.out::println);
  }
}