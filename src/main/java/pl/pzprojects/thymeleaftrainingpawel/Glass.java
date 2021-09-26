package pl.pzprojects.thymeleaftrainingpawel;

import pl.pzprojects.thymeleaftrainingpawel.exceptions.GlassFormExceptionManager;
import pl.pzprojects.thymeleaftrainingpawel.exceptions.GlassPourExceptionManager;

public class Glass {

    private String capacity;
    private String quantity;

    public Glass(String capacity, String quantity) {
         this.capacity = capacity;
         this.quantity = quantity;
    }

    public Glass(){

    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean pourOut(Glass target, String quantityString)
    {
        Double glassQuantity;
        Double quantity;
        Double targetCapacity;
        Double targetQuantity;
        try {
            glassQuantity = Double.valueOf(this.quantity);
            quantity = Double.valueOf(quantityString);
            targetCapacity = Double.valueOf(target.getCapacity());
            targetQuantity = Double.valueOf(target.getQuantity());
            if(target == this) // If the glass are the same
            {
                GlassPourExceptionManager.STATUS = "Can not fill the same glass";
                throw new IllegalArgumentException();
            }
            if(target == null || this == null) // If one of glasses is null
            {
                GlassPourExceptionManager.STATUS = "Target glass doesn't exist";
                throw new NullPointerException();
            }
            if(quantity == null || quantity.isNaN())
            {
                GlassPourExceptionManager.STATUS = "Quantity number is not valid";
                throw new IllegalArgumentException();
            }
            if(quantity<=0)
            {
                GlassPourExceptionManager.STATUS = "Quantity must be higher then 0";
                throw new IllegalArgumentException();
            }
            if(glassQuantity<quantity)
            {
                GlassPourExceptionManager.STATUS = "There is not enought water in the glass";
                throw new IllegalArgumentException();
            }
            if(targetCapacity < targetQuantity + quantity)
            {
                GlassPourExceptionManager.STATUS = "There is not enought space in target glass";
                throw new IllegalArgumentException();
            }
        }
        catch(NumberFormatException e){
            GlassPourExceptionManager.STATUS = "Please enter valid numbers in all fields";
            return false;
        }
        catch(IllegalArgumentException e)
        {
            GlassPourExceptionManager.STATUS = "Please enter a valid quantity of water";
            return false;
        }
        catch(NullPointerException e)
        {
            GlassPourExceptionManager.STATUS = "Glass can not be found";
            return false;
        }
        target.setQuantity(String.valueOf(targetQuantity+quantity));
        setQuantity(String.valueOf(glassQuantity-quantity));
        return true;
    }

    public Integer fillPixels(){
        try{
            Double capacity = Double.valueOf(getCapacity());
            Double quantity = Double.valueOf(getQuantity());
            Double fill = quantity / capacity * 100.0;
            fill = 170 - (fill * 1.70); // This math trick is counting glass fill in pixels :)
            return (int) Math.round(fill);
        }
        catch(NumberFormatException e){
            GlassFormExceptionManager.STATUS = "Values are not a valid numbers";
            return -1;
        }
    }
}
