[Exemple de l'article del Blog] (http://blog.utrescu.cat/Usar_Retrofit_per_consumir_serveis_REST/)

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

