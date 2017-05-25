package net.xaviersala;

import java.util.List;
import java.util.Random;

import net.xaviersala.model.Color;
import net.xaviersala.service.ColorsService;
import net.xaviersala.service.ServeiException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
    	Random aleatori = new Random();

    	ColorsService servei = new ColorsService();
    	servei.build();

    	try {

    		List<Color> colors = servei.getColors();

    		// En buscaré 5 aleatòriament
    		for (int i=0; i<5; i++) {
    			String colorACercar = colors.get(aleatori.nextInt(colors.size())).getNom();
    			System.out.println(servei.getColor(colorACercar));
    		}

    		System.out.println(servei.getColor("Gos com fuig"));

    	} catch (ServeiException e) {

			System.out.println(e.getMessage());
		}


    }
}
