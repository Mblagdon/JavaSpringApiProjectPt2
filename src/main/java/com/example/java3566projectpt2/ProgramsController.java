package com.example.java3566projectpt2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/program")
public class ProgramsController {
    @Autowired
    private ProgramsRepository programsRepository;

    // Create
    @PostMapping("/add")
    public @ResponseBody String addProgram(@RequestBody Programs program) {
        programsRepository.save(program);
        return "Program saved";
    }

    // Read all
    @GetMapping("/all")
    public @ResponseBody List<Programs> getAllPrograms() {
        return programsRepository.findAll();
    }

    // Read one
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Programs> getProgramById(@PathVariable Long id) {
        return programsRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Programs> updateProgram(@PathVariable Long id, @RequestBody Programs programDetails) {
        return programsRepository.findById(id)
                .map(program -> {
                    program.setProgramName(programDetails.getProgramName());
                    program.setCampus(programDetails.getCampus());
                    programsRepository.save(program);
                    return ResponseEntity.ok(program);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        if (programsRepository.existsById(id)) {
            programsRepository.deleteById(id);
            return ResponseEntity.ok("Program deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
