package ru.bdoxhn.springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bdoxhn.springcourse.dao.PersonDAO;
import ru.bdoxhn.springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {


    //@Autowired
    //private PersonDAO personDAO;
    //ниже то же самое, но внедрение через конструктор
    private final PersonDAO personDAO;

    @Autowired //можно писать, можно не писать, спринг сам внедрит
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        //Получим всех людей из DAO
        //Передадим на отображение в представление
        model.addAttribute("people", personDAO.index());

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        //Получим одного человека по его id из DAO
        //Передадим этого человека на отображение в представление

        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
//    public String newPerson(Model model){
//        model.addAttribute("person", new Person());
//        return "people/new";
//    }
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
