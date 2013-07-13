package model;

import java.util.HashMap;
import java.util.Map;

public class TypeKI {

    Map<Integer,String> mapNamePolygonDistrict;
	
	private static TypeKI instance = null;
	
	/**
	 * Implementation du pattern "singleton"
	 * @return
	 */
	public static TypeKI getInstance(){
		if(instance == null) instance = new TypeKI();
		return instance;
	}
	
    
	public TypeKI()
	{
		mapNamePolygonDistrict  = new HashMap<Integer, String>();
		mapNamePolygonDistrict.put(1,"Sortie Nord");
		mapNamePolygonDistrict.put(2,"Sortie Est");
		mapNamePolygonDistrict.put(3,"Sortie Sud");
		mapNamePolygonDistrict.put(4,"Sortie Ouest");
		mapNamePolygonDistrict.put(100,"Herbe");
		mapNamePolygonDistrict.put(101,"Arbre");
		mapNamePolygonDistrict.put(102,"Sapin");
		mapNamePolygonDistrict.put(103,"Mer");
		mapNamePolygonDistrict.put(104,"Rivière");
		mapNamePolygonDistrict.put(105,"Montagne");
		mapNamePolygonDistrict.put(106,"Sable");
		mapNamePolygonDistrict.put(107,"Glace");
		mapNamePolygonDistrict.put(108,"Fleurs");
		mapNamePolygonDistrict.put(110,"Rue");
		mapNamePolygonDistrict.put(111,"Rue");
		mapNamePolygonDistrict.put(112,"Rue");
		mapNamePolygonDistrict.put(113,"Rue");
		mapNamePolygonDistrict.put(114,"Rue");
		mapNamePolygonDistrict.put(115,"Rue");
		mapNamePolygonDistrict.put(116,"Rue");
		mapNamePolygonDistrict.put(117,"Rue");
		mapNamePolygonDistrict.put(118,"Rue");
		mapNamePolygonDistrict.put(119,"Rue");
		mapNamePolygonDistrict.put(120,"Rue");
		mapNamePolygonDistrict.put(121,"Pont");
		mapNamePolygonDistrict.put(122,"Pont");
		mapNamePolygonDistrict.put(131,"Palmier");
		mapNamePolygonDistrict.put(132,"Arbre Mutant");
		mapNamePolygonDistrict.put(133,"Bambou");
		mapNamePolygonDistrict.put(141,"Agroglyphe");
		mapNamePolygonDistrict.put(142,"Agroglyphe");
		mapNamePolygonDistrict.put(143,"Agroglyphe");
		mapNamePolygonDistrict.put(144,"Agroglyphe");
		mapNamePolygonDistrict.put(145,"Menhir");
		mapNamePolygonDistrict.put(146,"Cratère");
		mapNamePolygonDistrict.put(147,"Cratère");
		mapNamePolygonDistrict.put(148,"Lave");
		mapNamePolygonDistrict.put(149,"Métal");
	}
	
	public String getNamePolygonDistrict(int i_typePolygon)
	{
		return mapNamePolygonDistrict.get(i_typePolygon);
	}
}
