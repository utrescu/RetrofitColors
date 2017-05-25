package net.xaviersala.service;

import java.io.IOException;
import java.util.List;

import net.xaviersala.model.Color;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ColorsService {
	Retrofit retrofit;
	ColorsRestService service;


	// Falta capturar les excepcions
	public void build() {
		retrofit = new Retrofit.Builder()
    		 .baseUrl("http://colors-rgb.herokuapp.com")
    		 .addConverterFactory(JacksonConverterFactory.create())
    		 .build();

    	service = retrofit.create(ColorsRestService.class);
	}

	public Color getColor(String quin) throws ServeiException {
		if (service == null) {
		   throw new ServeiException("Servei no creat!");
		}

		try {
			Response<Color> resposta = service.getColor(quin).execute();
			if (resposta.code() == 200) {
				return resposta.body();
			} else {
				throw new ServeiException("Color no trobat");
			}
		} catch (IOException e) {
			throw new ServeiException(e.getMessage());
		}
	}

	public List<Color> getColors() throws ServeiException {
		// Comprovar que el servei ha estat creat
		if (service == null) {
			throw new ServeiException("Servei no creat!");
		}

		Call<List<Color>> resultat = service.getColors();

		// Obtenir el resultat
		try {
			return resultat.execute().body();
		} catch (IOException e) {
			throw new ServeiException(e.getMessage());
		}
	}


}
