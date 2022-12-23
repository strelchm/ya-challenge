package ru.strelchm.yachallenge;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Third {
  private final static Scanner scanner = new Scanner(System.in);
  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  private static Map<FilterType, Object> filters = new HashMap<>();

  enum FilterType {
    NAME_CONTAINS,
    PRICE_GREATER_THAN,
    PRICE_LESS_THAN,
    DATE_BEFORE,
    DATE_AFTER
  }

//  private static final String NAME_CONTAINS = "NAME_CONTAINS";
//  private static final String PRICE_GREATER_THAN = "PRICE_GREATER_THAN";
//  private static final String PRICE_LESS_THAN = "PRICE_LESS_THAN";
//  private static final String DATE_BEFORE = "DATE_BEFORE";
//  private static final String DATE_AFTER = "DATE_AFTER";

  static class Lot {
    private Long id;
    private String name;
    private Long price;
    private LocalDate date;

    public Lot(Long id, String name, Long price, LocalDate date) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.date = date;
    }



    @Override
    public String toString() {
      return "Lot{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", price=" + price +
          ", date=" + date +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Lot lot = (Lot) o;
      return Objects.equals(name, lot.name) && Objects.equals(price, lot.price) && Objects.equals(date, lot.date);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, price, date);
    }
  }


  public static void main(String[] args) throws ParseException {
    List<Lot> lots = new ArrayList<>();
    JSONParser parser = new JSONParser();
    JSONArray msg = (JSONArray) parser.parse(scanner.nextLine());

    for(int i = 0; i < 5; i++) {
      String[] v = scanner.nextLine().split(" ");
      FilterType key = FilterType.valueOf(v[0]);
      if (key == FilterType.DATE_AFTER || key == FilterType.DATE_BEFORE) {
      filters.put(key, LocalDate.parse(v[1], DATE_TIME_FORMATTER));
      } else if(key == FilterType.NAME_CONTAINS) {
        filters.put(key, v[1]);
      } else if(key == FilterType.PRICE_LESS_THAN || key == FilterType.PRICE_GREATER_THAN) {
        filters.put(key, Long.valueOf(v[1]));
      }
    }

    List<JSONObject> res = (List<JSONObject>) msg.stream().map(v -> {
      JSONObject jsonObject = (JSONObject) v;
      return new Lot((Long) jsonObject.get("id"), (String) jsonObject.get("name"), (Long) jsonObject.get("price"),
          LocalDate.parse((String) jsonObject.get("date"), DATE_TIME_FORMATTER));
    }).filter(v -> {
      Lot lot = (Lot) v;
      return (lot.date.isAfter(((LocalDate)filters.get(FilterType.DATE_AFTER))) || lot.date.isEqual(((LocalDate)filters.get(FilterType.DATE_AFTER))) ) &&
       (lot.date.isBefore(((LocalDate)filters.get(FilterType.DATE_BEFORE))) || lot.date.isEqual(((LocalDate)filters.get(FilterType.DATE_BEFORE))) ) &&
       (lot.name.toLowerCase().contains(filters.get(FilterType.NAME_CONTAINS).toString().toLowerCase())) &&
       (lot.price <= (Long)filters.get(FilterType.PRICE_LESS_THAN) && lot.price >= (Long)filters.get(FilterType.PRICE_GREATER_THAN));
    }).distinct().sorted(Comparator.comparingLong(o -> ((Lot) o).id)).map(v -> {
      Map obj = new LinkedHashMap();
      obj.put("id", ((Lot) v).id);
      obj.put("name", ((Lot) v).name);
      obj.put("price", ((Lot) v).price);
      obj.put("date", ((Lot) v).date);
      return new JSONObject(obj);
    }).collect(Collectors.toList());

    JSONArray list = new JSONArray();
    list.addAll(res);

    System.out.println(list.toJSONString());
  }
}