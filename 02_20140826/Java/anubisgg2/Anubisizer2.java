package anubisgg2;

import java.util.Comparator;
import java.util.Collections;
import java.util.Map;

import org.w3c.dom.Element;

public class Anubisizer2 implements Anubisizable {

	@Override
	public void write(AnubisObject ano) {
		HtmlUtility html = HtmlUtility.create("表2");
		Element root = html.getRoot();

		AnubisObject.AreaMap areaMap = ano.data;
		for (Map.Entry<String, AnubisObject.CityMap> cityMapEntry : areaMap.entrySet()){
			String area = cityMapEntry.getKey();
			AnubisObject.CityMap cityMap = cityMapEntry.getValue();

			// title
			Element h1 = html.createTextElement("h1", area);
			root.appendChild(h1);

			// table
			Element table = html.createElement("table");
			table.setAttribute("border", "1");
			root.appendChild(table);

			// header
			Element headerTR = html.createTRHeader("市", "内訳", "ルーター", "ホスト名");
			table.appendChild(headerTR);

			// data
			for (Map.Entry<String, AnubisObject.Items> itemsEntry : cityMap.getTreeMap().entrySet()){
				String city = itemsEntry.getKey();
				AnubisObject.Items items = itemsEntry.getValue();
				Element td1 = html.createTD(city, items.size());

				Collections.sort(items, new Comparator<AnubisObject.Item>() {
					@Override
					public int compare(AnubisObject.Item o1, AnubisObject.Item o2) {
						int c1 = o1.model.compareTo(o2.model);
						int c2 = o1.name.compareTo(o2.name);
						return c1 == 0 ? c2 : c1;
					}
				});
				for (AnubisObject.Item item : items){
					Element tr = html.createElement("tr");
					Element td2 = html.createTD(item.name + ":" + item.model, 0);
					Element td3 = html.createTD(item.model, 0);
					Element td4 = html.createTD(item.name, 0);
					table.appendChild(tr);
					if (td1 != null) {
						tr.appendChild(td1);
						td1 = null;
					}
					tr.appendChild(td2);
					tr.appendChild(td3);
					tr.appendChild(td4);
				}
			}
		}
		System.out.println(html.toHtmlString());
	}
}
