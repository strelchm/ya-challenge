package ru.strelchm.yachallenge;

import java.util.Scanner;

public class Fourth {
  private final static Scanner scanner = new Scanner(System.in);

  public static boolean isValid(int n, int m, short[][] type, int x, int y) {
    return x >= 0 && x < n && y >= 0 && y < m && type[x][y] == 2;
  }

  public static void dfs(int n, int m, short[][] type, int x, int y) {
    if (isValid(n, m, type, x + 1, y)) {
      type[x + 1][y] = 4;
      dfs(n, m, type, x + 1, y);
    }
    if (isValid(n, m, type, x, y - 1)) {
      type[x][y - 1] = 5;
      dfs(n, m, type, x, y - 1);
    }
    if (isValid(n, m, type, x - 1, y)) {
      type[x -1 ][y] = 6;
      dfs(n, m, type, x - 1, y);
    }
    if (isValid(n, m, type, x, y + 1)) {
      type[x][y + 1] = 7;
      dfs(n, m, type, x, y + 1);
    }
  }

  public static void main(String[] args) {
    int n = scanner.nextInt(), m = scanner.nextInt();
    String[] s = new String[n];
    short[][] type = new short[n][m];
    int x = -1, y = -1;
    for (int i = 0; i < n; ++i) {
      s[i] = scanner.next();
      for (int j = 0; j < m; ++j) {
        type[i][j] = (short) (s[i].charAt(j) == '#' ? 1 : s[i].charAt(j) == '.' ? 2 : 3);
        if (s[i].charAt(j) == 'S') {
          x = i; y = j;
        }
      }
    }
    dfs(n, m, type, x, y);
    char[] value = {' ', '#', '.', 'S', 'D', 'L', 'U', 'R'};
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < m; ++j) {
        System.out.print(value[type[i][j]]);
      }
      System.out.println();
    }
  }
}
