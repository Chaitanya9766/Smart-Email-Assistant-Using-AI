package com.email.writer.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
@CrossOrigin(origins  = "*")
public class EmailGeneratorController {
    @Autowired
	private EmailGeneratorServices emailGeneratorServices;
	@PostMapping("/generate")
	 public ResponseEntity<String> generateEmail(@RequestBody EmailRequest em ){
		String response =emailGeneratorServices.generatorEmailReply(em);
		 return ResponseEntity.ok(response);
	 }
	
}
