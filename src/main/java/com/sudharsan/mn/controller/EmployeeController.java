package com.sudharsan.mn.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sudharsan.mn.dao.EmployeeDao;
import com.sudharsan.mn.model.Employee;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeDao ed;
	
	@Autowired
	JdbcTemplate jdbc;
	
	@RequestMapping(value="/")
	public String Show(){
		return "index";
	}
	
	@RequestMapping(value="/all")
	public ResponseEntity<List<Employee>> getAllItems(){
		System.out.println("GetAll Method");
		List<Employee> all = new ArrayList<>();
		String query = "select * from emp1";
		jdbc.query(query, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				all.add(new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("designation")));
			}
		});
		return new ResponseEntity<>(all, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getone",method=RequestMethod.GET)
	public Employee get(@RequestParam("itemId") int id)
	{
		return ed.getIndividualItem(id);
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public ResponseEntity<Object> addDetails(@RequestBody Employee p)
	{
		String message = null;
		System.out.println("inside insert method");
		if(ed.addItem(p.getName(),p.getDesignation())>=1)
		{
			message = "Data Saved Successfully";
		}
		else
		{
			message = "Please Check";
		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.DELETE)
	public String DeleteDetails(@RequestParam("itemId") int id)
	{
		if(ed.deleteItem(id)>=1)
		{
			return "Data removed Successfully";
		}
		else
		{
			return "Please Check";
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public String updateDetails(@RequestParam("itemId") int id, @RequestBody Employee p)
	{
		if(ed.updateItem(id, p.getName(), p.getDesignation())>=1)
		{
			return "Data updated Successfully";
		}
		else
		{
			return "Please Check";
		}
	}
}
