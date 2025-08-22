package com.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.entity.State;
import com.application.repository.StateRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Logic {
	@Autowired
	StateRepository staterepo;

	public List<State> gealldata() {

		return staterepo.findAll();
	}
}
