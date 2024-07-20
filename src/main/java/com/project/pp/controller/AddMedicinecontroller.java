package com.project.pp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.pp.model.AddMedicine;
import com.project.pp.repository.AddMedicineRepository;

@RestController
@RequestMapping("/api/v1")
public class AddMedicinecontroller {

    private final AddMedicineRepository addMedicineRepository;

    @Autowired
    public AddMedicinecontroller(AddMedicineRepository addMedicineRepository) {
        this.addMedicineRepository = addMedicineRepository;
    }

    @PostMapping("/medicines")
    public ResponseEntity<String> addMedicine(@RequestBody AddMedicine addMedicine) {
        addMedicineRepository.save(addMedicine);
        return new ResponseEntity<>("Medicine added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/medicines")
    public ResponseEntity<List<AddMedicine>> getAllMedicines() {
        List<AddMedicine> medicines = addMedicineRepository.findAll();
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    @GetMapping("/medicines/{id}")
    public ResponseEntity<AddMedicine> getMedicineById(@PathVariable Long id) {
        Optional<AddMedicine> medicine = addMedicineRepository.findById(id);
        return medicine.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/medicines/{id}")
    public ResponseEntity<String> updateMedicine(@PathVariable Long id, @RequestBody AddMedicine addMedicineDetails) {
        Optional<AddMedicine> optionalMedicine = addMedicineRepository.findById(id);
        if (optionalMedicine.isPresent()) {
            AddMedicine medicine = optionalMedicine.get();
            medicine.setName(addMedicineDetails.getName());
            medicine.setQuantity(addMedicineDetails.getQuantity());
            medicine.setPrice(addMedicineDetails.getPrice());
            medicine.setType(addMedicineDetails.getType());
            medicine.setExpireDate(addMedicineDetails.getExpireDate());
            addMedicineRepository.save(medicine);
            return new ResponseEntity<>("Medicine updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Medicine not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable Long id) {
        if (addMedicineRepository.existsById(id)) {
            addMedicineRepository.deleteById(id);
            return new ResponseEntity<>("Medicine deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Medicine not found", HttpStatus.NOT_FOUND);
        }
    }
}
