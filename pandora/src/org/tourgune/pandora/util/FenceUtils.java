package org.tourgune.pandora.util;


 

public class FenceUtils {
	
	private static int numerOfPointsCircle = 50; 
 
	public static String getGeometryStringFromCircle(double latitude, double longitude, double radius) {

			 LatLng[] circlePoints = new LatLng[numerOfPointsCircle];

			 radius = radius * 1000;//el radio en la interfaz es en kilómetros
			 
			 double EARTH_RADIUS = 6371000;
			 double d = radius / EARTH_RADIUS;
			 double lat1 = Math.toRadians(latitude);
			 double lng1 = Math.toRadians(longitude);

			 double a = 0;
			 double step = 360.0 / (double) numerOfPointsCircle;
			 
			 for (int i = 0; i < numerOfPointsCircle; i++) {
			  double tc = Math.toRadians(a);
			  double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1)
			    * Math.sin(d) * Math.cos(tc));
			  double lng2 = lng1
			    + Math.atan2(Math.sin(tc) * Math.sin(d) * Math.cos(lat1),
			     Math.cos(d) - Math.sin(lat1) * Math.sin(lat2));
			  
			  LatLng point = new LatLng(Math.toDegrees(lat2), Math.toDegrees(lng2)); 
			  circlePoints[i] = point; 
			  a += step;
			 }
			 
			 //pasar a string los puntos 
			StringBuffer geometry = new StringBuffer();
			int i=0;
			while (i < circlePoints.length) {
				LatLng latlong = (LatLng) circlePoints[i];
				geometry.append(latlong.getLatitude()).append(" ").append(latlong.getLongitude());
				i++;
				if (i < circlePoints.length) {
					geometry.append(", ");
				}			
			} 
			geometry.append(", ");
			LatLng latlong = (LatLng)  circlePoints[0];
			geometry.append(latlong.getLatitude()).append(" ").append(latlong.getLongitude());
			
			return geometry.toString(); 
	}
	
 

}
