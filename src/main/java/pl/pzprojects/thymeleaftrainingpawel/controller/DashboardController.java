package pl.pzprojects.thymeleaftrainingpawel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("/delete-all")
    public String deleteAll(){
        glasses.clear();
        GlassFormExceptionManager.STATUS = "";
        GlassPourExceptionManager.STATUS = "";
        return "redirect:/";
    }


    public boolean isError(Glass g)
    {
        Boolean error = true;
        try{
            Double capacity = Double.valueOf(g.getCapacity());
            Double quantity = Double.valueOf(g.getQuantity());
            if(quantity <= 0 || capacity <= 0)
            {
                throw new IllegalArgumentException("Values must be higher then 0");
            }
            if(quantity > capacity)
            {
                throw new IllegalArgumentException("You can not create overfilled glass");
            }
        }
        catch(NumberFormatException e){
            GlassFormExceptionManager.STATUS = "Please enter a valid numbers in fields";
            return true;
        }
        catch(NullPointerException e){
            GlassFormExceptionManager.STATUS = "Something went wrong! Object doesnt exists";
            return true;
        }
        catch(IllegalArgumentException e){
            GlassFormExceptionManager.STATUS = e.getMessage();
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
                throw new NullPointerException();
            }
            if((gid > glasses.size() || gid <= 0) || (tid > glasses.size() || tid <= 0))
            {
                throw new IllegalArgumentException("One or more glass doesn't exists");
            }
            if(quantity == null || quantity.isNaN())
            {
                throw new IllegalArgumentException("Quantity must be a number");
            }
            if(quantity <= 0)
            {
                throw new IllegalArgumentException("Quantity can not be 0 and lower");
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
        catch (IllegalArgumentException e)
        {
            GlassPourExceptionManager.STATUS = e.getMessage();
            return true;
        }
        GlassPourExceptionManager.STATUS = "Success!";
        return false;
    }
}
