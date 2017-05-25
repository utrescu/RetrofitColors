package net.xaviersala.model;

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
	@Override
	public String toString() {
		return "Color [id=" + id + ", nom=" + nom + ", rgb=" + rgb + "]";
	}


}
