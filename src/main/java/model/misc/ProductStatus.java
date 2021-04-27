package model.misc;

/**
 * Enum-ul corespunzator statutului unei comenzi
 */
public enum ProductStatus {
    CONFIRMED,SHIPPED,DELIVERED;
    @Override
    public String toString() {
        if(this==CONFIRMED)
            return "CONFIRMED";
        if(this==SHIPPED)
            return "SHIPPED";
        if(this==DELIVERED)
            return "DELIVERED";
        return super.toString();
    }
    public static ProductStatus toObject(String source){
        if(source.trim().equalsIgnoreCase("CONFIRMED"))
            return CONFIRMED;
        if(source.trim().equalsIgnoreCase("SHIPPED"))
            return SHIPPED;
        if(source.trim().equalsIgnoreCase("DELIVERED"))
            return DELIVERED;
        return null;
    }
}

