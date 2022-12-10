package web;

import entities.Seat;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import repositories.SeatRepository;

import java.math.BigInteger;
import java.util.List;


@SessionAttributes({"a","e"})
@Controller
@AllArgsConstructor

public class SeatController {

    @Autowired
    private SeatRepository seatRepository;

    static int num = 0;


    @GetMapping(path = "/index")
    public String seats(Model model, @RequestParam(name = "seatcode", defaultValue = "") String seatcode) {

        List<Seat> seats;
        if (seatcode.isEmpty()) {
            //alert: "Seat or name cannot be blank"
            seats = seatRepository.findAll();
        } else {
            long key = Long.parseLong(seatcode);
            seats = seatRepository.findSeatbyId(key);
//            if (!key=seats)
//            {
//                //alert: "Please follow the seat code format"
//            }
//            else if (key=seats)
//            {
//                //alerr: "The seat is already taken"
//            }
        }
        model.addAttribute("listSeats", seats);
        return "transaction";


    }


    @GetMapping("/delete")
    public String delete(BigInteger id) {
//        studentRepository.deleteById(id);
            seatRepository.deleteById(id);

        return "redirect:/transaction";
    }

    @GetMapping("/editSeats")
    public String editStudents(Model model, Long id){

        num = 2;

        Seat seat = (Seat) seatRepository.findById(id).orElse(null);
        if(seat==null)
            throw new RuntimeException("Seat does not exist");
        model.addAttribute("seat", seat);

        return "redirect:/transaction";
    }

    @PostMapping(path = "/save")
    public String save(Model model, Seat seat, BindingResult bindingResult,
                       ModelMap mm, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "formStudents";
        } else {

            seatRepository.save(seat);


            if (num == 2) {
                mm.put("e", 2);
                mm.put("a", 0);
            } else {
                mm.put("a", 1);
                mm.put("e", 0);
            }
            return "redirect:index";
        }

    }
}
