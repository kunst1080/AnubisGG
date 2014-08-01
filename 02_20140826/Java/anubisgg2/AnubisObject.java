package anubisgg2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class AnubisObject {

	public static class AreaMap extends HashMap<String, CityMap> {
		private static final long serialVersionUID = 1996027443015778986L;

		public CityMap put(String key) {
			if(this.containsKey(key)) {
				return this.get(key);
			} else {
				CityMap value = new CityMap();
				this.put(key, value);
				return value;
			}
		}

		public int sizeRecursive() {
			int count = 0;
			for (CityMap cityMap  : this.values()) {
				count = count + cityMap.sizeRecursive();
			}
			return count;
		}

		public TreeMap<String, CityMap> getTreeMap() {
			return new TreeMap<String, CityMap>(this);
		}
	}

	public static class CityMap extends HashMap<String, Items>{
		private static final long serialVersionUID = -5971558339169799607L;

		public Items put(String key) {
			if(this.containsKey(key)) {
				return this.get(key);
			} else {
				Items value = new Items();
				this.put(key, value);
				return value;
			}
		}

		public int sizeRecursive() {
			int count = 0;
			for (Items items  : this.values()) {
				count = count + items.size();
			}
			return count;
		}

		public TreeMap<String, Items> getTreeMap() {
			return new TreeMap<String, Items>(this);
		}
	}

	public static class Items extends ArrayList<Item> {
		private static final long serialVersionUID = -7210213459544408239L;
	}

	public static class Item {
		String model;
		String name;

		Item(String model, String name) {
			this.model = model;
			this.name = name;
		}
	}

	AreaMap data = new AreaMap();

	public static AnubisObject anubisize(Scanner scanner) {
		AnubisObject ano = new AnubisObject();

		while(scanner.hasNextLine()) {
			String[] a = scanner.nextLine().split(",");
			String area = a[0];
			String city = a[1];
			Item item = new Item(a[2], a[3]);

			AreaMap areaMap = ano.data;
			CityMap cityMap = areaMap.put(area);
			Items items = cityMap.put(city);
			items.add(item);
		}
		return ano;
	}
}
