package pl.pzprojects.thymeleaftrainingpawel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pzprojects.thymeleaftrainingpawel.Glass;
import pl.pzprojects.thymeleaftrainingpawel.GlassPourDto;
import pl.pzprojects.thymeleaftrainingpawel.exceptions.GlassFormExceptionManager;
import pl.pzprojects.thymeleaftrainingpawel.exceptions.GlassPourExceptionManager;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    List<Glass> glasses;

    public DashboardController() {
        glasses = new ArrayList<>();
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("glasses",glasses);
        model.addAttribute("glass",new Glass());
        model.addAttribute("glassPour",new GlassPourDto());
        return "styledPage";
    }

    @PostMapping("/add")
    public String addGlass(@ModelAttribute Glass glass) {
        if(!isError(glass))
        {
            glasses.add(glass);
        }
        return "redirect:/";
    }


    @PostMapping("/put-water")
    public String pourOutWater(@ModelAttribute GlassPourDto glassPour) {
        if(!isError(glassPour))
        {
            Integer glassId = Integer.valueOf(glassPour.getGlassId()) - 1;
            Integer targetId = Integer.valueOf(glassPour.getTargetId()) - 1;
            glasses.get(glassId).pourOut(glasses.get(targetId),glassPour.getPourQuantity());
        }
        return "redirect:/";
    }

    public boolean isError(Glass g)
    {
        try{
            Double capacity = Double.valueOf(g.getCapacity());
            Double quantity = Double.valueOf(g.getQuantity());
            if(quantity <= 0 || capacity <= 0)
            {
                GlassFormExceptionManager.STATUS = "Values must be higher then 0";
                return true;
            }
            if(quantity > capacity)
            {
                GlassFormExceptionManager.STATUS = "You can not create overfilled glass";
                return true;
            }
        }
        catch(NumberFormatException e){
            GlassFormExceptionManager.STATUS = "Please enter a valid numbers in fields";
            return true;
        }
        catch(NullPointerException e){
            GlassFormExceptionManager.STATUS = "NULL";
            return true;
        }
        catch(IllegalArgumentException e){
            GlassFormExceptionManager.STATUS = "One or more values has illegal characters";
            return true;
        }
        GlassFormExceptionManager.STATUS = "Glass Created!";
        return false;
    }

    public boolean isError(GlassPourDto g){
        try{
            Double quantity = Double.valueOf(g.getPourQuantity());
            Integer gid = Integer.valueOf(g.getGlassId());
            Integer tid = Integer.valueOf(g.getTargetId());
            if(gid == null || tid == null)
            {
                GlassPourExceptionManager.STATUS = "One or more glass is NULL";
                return true;
            }
            if((gid > glasses.size() || gid <= 0) || (tid > glasses.size() || tid <= 0))
            {
                GlassPourExceptionManager.STATUS = "One or more glass doesn't exists";
                return true;
            }
            if(quantity == null || quantity.isNaN())
            {
                GlassPourExceptionManager.STATUS = "Quantity must be a number";
                return true;
            }
            if(quantity <= 0)
            {
                GlassPourExceptionManager.STATUS = "Quantity can not be 0 and lower";
                return true;
            }
        }
        catch (NumberFormatException e)
        {
            GlassPourExceptionManager.STATUS = "Please enter valid numbers in all fields";
            return true;
        }
        catch (NullPointerException e)
        {
            GlassPourExceptionManager.STATUS = "One or more objects are null";
            return true;
        }
        GlassPourExceptionManager.STATUS = "Success!";
        return false;
    }
}
