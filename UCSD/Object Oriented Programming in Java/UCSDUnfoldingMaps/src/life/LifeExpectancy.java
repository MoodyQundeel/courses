package life;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.data.GeoJSONReader;

import processing.core.PApplet;

public class LifeExpectancy extends PApplet{
	
	private static final long serialVersionUID = 1L;
	
	UnfoldingMap map;
	Map<String, Float> lifeExpByCountry;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	public void setup() {
		lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBank.csv");
		size(1280, 720, OPENGL);
		background(0);
		map = new UnfoldingMap(this ,0, 0, 1280, 720, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		countries = GeoJSONReader.loadData(this,
					"countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		shadeCountries();
		map.addMarkers(countryMarkers);
	}
	
	public void draw() {
		map.draw();
	}
	
	private Map<String, Float> 
		loadLifeExpectancyFromCSV(String fileName) {
		Map<String, Float> lifeExpMap = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName);
		
		for (String row : rows) {
			String[] columns = row.split(",");
			if (! columns[6].equals("..")) {
				float value = Float.parseFloat(columns[6]);
				lifeExpMap.put(columns[4], value);
			}
		}
		return lifeExpMap;
	}
	
	private void shadeCountries() {
		for (Marker marker : countryMarkers) {
			String countryId = marker.getId();
			
			if (lifeExpByCountry.containsKey(countryId)) {
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150, 150, 150));
			}
		}
	}
}
