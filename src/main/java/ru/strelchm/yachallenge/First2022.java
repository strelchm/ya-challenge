package ru.strelchm.yachallenge;

import java.util.Scanner;

/**
 *
 A. Коля и датацентры
 Ограничение времени 	2.5 секунд
 Ограничение памяти 	512Mb
 Ввод 	стандартный ввод или input.txt
 Вывод 	стандартный вывод или output.txt
 У каждой крупной IT-компании рано или поздно возникает необходимость иметь свои датацентры.

 Коля недавно устроился в одну из таких компании стажёром. У компании есть N датацентров, каждый из которых имеет ровно по M серверов.

 Из-за наплыва большого трафика и спешки в постройке датацентров, некоторые из серверов в каком-то из них выключаются, помогает только перезапуск всего датацентра. При этом каждый из датацентров характеризуется двумя неотрицательными целыми числами:

 Ri - число перезапусков i-го датацентра и Ai - число рабочих (не выключенных) серверов на текущий момент в i-м датацентре.

 Руководитель поручил Коле задачу по сбору некоторых метрик, которые помогут компании в дальнейшем в улучшении датацентров. Для этого Коле дали список из Q событий, которые произошли за текущий день. Но, так как Коля ещё довольно неопытен в этом деле, он просит вас помочь с этим.

 Формат ввода
 В первой строке входных данных записано 3 положительных целых числа n, m, q (1≤q≤105,1≤n⋅m≤106) — число датацентров, число серверов в каждом из датацентров и число событий соответственно.

 В последующих q строках записаны события, которые могут иметь один из следующих видов:

 RESET i — был перезагружен i-й датацентр (1≤i≤n)

 DISABLE ij — в i-м датацентре был выключен j-й сервер (1≤i≤n,1≤j≤m)

 GETMAX — получить номер датацентра с наибольшим произведением Ri∗Ai

 GETMIN — получить номер датацентра с наименьшим произведением Ri∗Ai

 Формат вывода
 На каждый запрос вида GETMIN или GETMAX выведите единственное положительное целое число — номер датацентра, подходящий под условие. В случае неоднозначности ответа выведите номер наименьшего из датацентров.
 Пример 1
 Ввод
 Вывод

 3 3 12
 DISABLE 1 2
 DISABLE 2 1
 DISABLE 3 3
 GETMAX
 RESET 1
 RESET 2
 DISABLE 1 2
 DISABLE 1 3
 DISABLE 2 2
 GETMAX
 RESET 3
 GETMIN



 1
 2
 1

 Пример 2
 Ввод
 Вывод

 2 3 9
 DISABLE 1 1
 DISABLE 2 2
 RESET 2
 DISABLE 2 1
 DISABLE 2 3
 RESET 1
 GETMAX
 DISABLE 2 1
 GETMIN



 1
 2

 Примечания
 Обратите внимание на 2 пример. DISABLE приходится для уже выключенного сервера. В данном случае сервер по-прежнему остаётся выключенным.
 Скачать условие задачи
 */
public class First2022 {
  private final static Scanner scanner = new Scanner(System.in);

  private final static class DataCenter {
    private boolean[] serverDisables;
    private int resetCount;

    public DataCenter(int serverNumber) {
      this.serverDisables = new boolean[serverNumber];
    }
  }

  public static void main(String[] args) {
    String[] firstString = scanner.nextLine().split(" ");
    int n = Integer.parseInt(firstString[0]);
    int m = Integer.parseInt(firstString[1]);
    int eventCount = Integer.parseInt(firstString[2]);

    DataCenter[] dataCenters = new DataCenter[n];
    for (int i = 0; i < dataCenters.length; i++) {
      dataCenters[i] = new DataCenter(m);
    }

    for (int i = 0; i < eventCount; ++i) {
      String str = scanner.nextLine();
      if (str.equals("GETMIN")) {
        int ds = -1;
        int enability = Integer.MAX_VALUE;
        for (int j = 0; j < dataCenters.length; j++) {
          DataCenter dataCenter = dataCenters[j];
          int e = dataCenter.resetCount * getEnableServerCount(dataCenter.serverDisables);
          if (enability == -1 || enability > e) {
            enability = e;
            ds = j + 1;
          }
        }
        System.out.println(ds);
      } else if (str.equals("GETMAX")) {
        int ds = -1;
        int enability = -1;
        for (int j = 0; j < dataCenters.length; j++) {
          DataCenter dataCenter = dataCenters[j];
          int e = dataCenter.resetCount * getEnableServerCount(dataCenter.serverDisables);
          if (enability == -1 || enability < e) {
            enability = e;
            ds = j + 1;
          }
        }
        System.out.println(ds);
      } else if (str.startsWith("RESET")) {
        String[] splitStr = str.split(" ");
        int dataCenterNumber = Integer.parseInt(splitStr[1]);
        dataCenters[dataCenterNumber - 1].resetCount++;
        dataCenters[dataCenterNumber - 1].serverDisables = new boolean[m];
      } else if (str.startsWith("DISABLE")) {
        String[] splitStr = str.split(" ");
        int dsNumber = Integer.parseInt(splitStr[1]);
        int serverNumber = Integer.parseInt(splitStr[2]);
        dataCenters[dsNumber - 1].serverDisables[serverNumber - 1] = true;
      }
    }
  }

  private static int getEnableServerCount(boolean[] serverDisabeles) {
    int res = 0;
    for (boolean d : serverDisabeles) {
      if (!d) {
        ++res;
      }
    }
    return res;
  }
}
