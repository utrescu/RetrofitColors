[Retrofit](http://square.github.io/retrofit/) és una llibreria de Java que es fa servir per consumir serveis REST de qualsevol tipus: JSON, XML, Protobuff, etc...

La idea de Retrofit és que no cal que ens preocupem de tot el que fa referència a les peticions per la xarxa, ni de construir els objectes, .. Només cal cridar els mètodes correctes i ell se n'encarregarà de la resta.

A més se n'encarregarà de convertir les dades rebudes en objectes Java. Per exemple si ens arriba un objecte com aquest:

```json
{"id":71,"nom":"Vermell","rgb":"#FF0000"}
```
Es pot mapejar en un objecte com aquest (he tret els getters i setters per fer la classe més senzilla)

```java
class Color {
    int id;
    String nom;
    String rgb;
}
```

Exemple d'ús
-----------------------
Volem consumir un servei REST que està a [http://colors-rgb.herokuapp.com]("http://colors-rgb.herokuapp.com") que proporciona dos mètodes a través de GET:

| mètode          | resultat   |
| --------------- | ---------- |
| /color/nom      | Retorna les dades del color especificat a *nom* en format JSON. Per exemple la URL "/color/verd" donarà les dades del color verd. |
| /colors        | Llista tots els colors del servei |

### Afegir les llibreries
Per poder funcionar caldrà tenir la llibreria Retrofit2 en el CLASSPATH. Per mi la forma més senzilla és definir-la en un gestor de projectes Maven o Gradle.

En Maven fem una cosa com aquesta:

```xml
<dependency>
    <groupId>com.squareup.retrofit2</groupId>
    <artifactId>retrofit</artifactId>
    <version>2.3.0</version>
</dependency>
```
I en Gradle:

```
compile 'com.squareup.retrofit2:retrofit:2.0.2'
```

Si el que consumim no és HTML també farà falta afegir el **conversor**. En Retrofit hi ha diferents conversors de sèrie (però també se'n poden crear de personalitzats):


* **Gson**: com.squareup.retrofit2:converter-gson
* **Jackson**: com.squareup.retrofit2:converter-jackson
* **Moshi**: com.squareup.retrofit2:converter-moshi
* **Protobuf**: com.squareup.retrofit2:converter-protobuf
* **Wire**: com.squareup.retrofit2:converter-wire
* **Simple XML**: com.squareup.retrofit2:converter-simplexml
* **Scalars (dades primitives i les seves classe, i Strings)**: com.squareup.retrofit2:converter-scalars

Per tant podem fer servir Gson o Jackson per consumir JSON. Trio Jackson en Maven:

```xml
<dependency>
   <groupId>com.squareup.retrofit2</groupId>
   <artifactId>converter-jackson</artifactId>
   <version>2.3.0</version>
</dependency>
```

### Crear els models
En aquest cas el model de dades bàsic és senzill. Només cal crear un objecte *Color* (Jackson necessita *getters* i *setters*):

```java
public class Color {
	int id;
	String nom;
	String rgb;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getRgb() {
		return rgb;
	}
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
```
### Definir el servei

Per consumir el servei cal definir quins són els mètodes que volem consumir en una interfície anotada:

```java
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
```
Com es pot veure amb Retrofit 2 sempre es retorna un objecte parametritzat **Call<T>**. Bàsicament això és així per poder cridar els mètodes tant de forma síncrona com asíncrona.

Hi ha els diferents mètodes HTTP: (GET, POST, DELETE, ...) i altres anotacions **@FormUrlEncoded**, **@Multipart**, **@Headers**.

### Usar-lo

Ja està tot definit, ara només cal fer servir el servei que hem definit.

Primer s'ha de crear un objecte Retrofit al que se li pot definir la URL i el conversor que es farà servir:

```java
Retrofit retrofit = new Retrofit.Builder()
  .baseUrl("http://colors-rgb.herokuapp.com")
  .addConverterFactory(JacksonConverterFactory.create())
  .build();
```
Després es crea el servei a partir de retrofit. L'objecte creat serà la interfície:

```java
ColorsRestService service = retrofit.create(ColorsRestService.class);
```

#### Crida síncrona

Ara ja es poden cridar els mètodes de la interfície normalment. Les crides síncrones es fan amb el mètode **execute**:

Es pot fer directament des de la crida:

```java
Call<Color> crida =  service.getColor("vermell");
Color color = response.execute().body();
```

Però també es pot fer servir un objecte *Response* es podran comprovar altres coses sobre la resposta:

```java
Response<Color> resposta = service.getColor("vermell").execute();
if (response.code == 200) {
    Color color = response.body();
}
```

#### Crida asíncrona

Per fer una petició asíncrona caldrà definir els mètodes *onResponse* i *onFailure* en un Callback i fer servir **enqueue** en comptes d'**execute**:

```java
Call<Color> call = apiService.getColor("vermell");
call.enqueue(new Callback<Color>() {
    @Override
    public void onResponse(Call<Color> call, Response<Color> response) {
        int statusCode = response.code();
        Color user = response.body();
    }

    @Override
    public void onFailure(Call<Color> call, Throwable t) {
        // error!
    }
});
```
