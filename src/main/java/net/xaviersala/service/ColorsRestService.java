package net.xaviersala.service;

import java.util.List;

import net.xaviersala.model.Color;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ColorsRestService {
	@GET("color/{color}")
	Call<Color> getColor(@Path("color") String color);

	@GET("/colors")
	Call<List<Color>> getColors();

}
