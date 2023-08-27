package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Gender;
import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.AnimalValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Integer, Koala> koalas;
    @PostConstruct
    public void init(){
        koalas = new HashMap<>();
        koalas.put(1,new Koala(1,"George",4, Gender.MALE,20));
    }

    @GetMapping("/")
    public List<Koala> getAll(){
        return koalas.values().stream().toList();
    }
    @GetMapping("/{id}")
    public Koala get(@PathVariable int id) {
        AnimalValidator.isIdValid(id);
        AnimalValidator.isIdNotExist(koalas,id);
        return koalas.get(id);
    }

    @PostMapping("/")
    public Koala save(@RequestBody Koala koala) {
        AnimalValidator.isIdValid(koala.getId());
        AnimalValidator.isIdExist(koalas, koala.getId());
        AnimalValidator.isAnimalValid(koala);
        AnimalValidator.isKangarooValid(koala);

        koalas.put(koala.getId(),koala);

        return koalas.get(koala.getId());
    }
    @PutMapping("/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala) {
        AnimalValidator.isIdValid(id);
        AnimalValidator.isIdNotExist(koalas,id);
        AnimalValidator.isAnimalValid(koala);
        AnimalValidator.isKangarooValid(koala);

        koala.setId(id);
        koalas.put(id,koala);
        return koala;
    }
    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable int id) {
        AnimalValidator.isIdValid(id);
        AnimalValidator.isIdNotExist(koalas,id);

        Koala koala = koalas.get(id);
        koalas.remove(id);
        return koala;
    }
}
