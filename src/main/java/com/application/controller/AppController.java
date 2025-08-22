package com.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.entity.State;
import com.application.service.Logic;

@RestController
@RequestMapping
public class AppController {
	@Autowired
	Logic sobj;

	@GetMapping("/get")
	public List<State> gealldata() {

		return sobj.gealldata();
	}
}
