package com.alura.hotel;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

public class HotelAppResourcesLoader {

	private HotelAppResourcesLoader() {
	}

	public static URL loadURL(String path) {
		return HotelAppResourcesLoader.class.getResource(path);
	}

	public static URL getRandomHotelImage() {
		Random random = new Random();
		int randomIndex = random.nextInt(10) + 1;
		return HotelAppResourcesLoader.class.getResource("images/hotels/hotel" + randomIndex + ".jpg");
	}

	public static URL getRandomRoomImage() {
		Random random = new Random();
		int randomIndex = random.nextInt(10) + 1;
		return HotelAppResourcesLoader.class.getResource("images/rooms/room" + randomIndex + ".jpg");
	}

	public static String load(String path) {
		return loadURL(path).toString();
	}

	public static InputStream loadStream(String name) {
		return HotelAppResourcesLoader.class.getResourceAsStream(name);
	}

}
