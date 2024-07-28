package asint;
import java.util.HashMap;
import asint.SintaxisAbstractaTiny.Nodo;

public class SymbolTable {
    public HashMap<String, Nodo> hashMap;
    public SymbolTable parent;


    public SymbolTable(SymbolTable parent){
        this.hashMap = new HashMap<String, Nodo>();
        this.parent = parent;
    }
    
    public void inserta(String id, Nodo nodo){
        hashMap.put(id, nodo);
    }

    // only look at the most recent scope
    public boolean contiene(String id){
        return hashMap.containsKey(id);
    }

    // first itself, if doesn't contain it itself, look for the id in the hashmap of its parent
    public Nodo valorDe(String id){
        if (hashMap.containsKey(id)){
            return hashMap.get(id);
        }
        else{
            if(parent != null){
                return parent.valorDe(id);
            }
            else{
                return null;
            }
            
        }
    }


}


