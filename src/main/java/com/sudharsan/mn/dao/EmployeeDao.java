package com.sudharsan.mn.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.sudharsan.mn.model.Employee;

@Repository
public class EmployeeDao {
	@Autowired
	JdbcTemplate temp;
	
	public List<Employee> getAll(){
		System.out.println("Reached Get method");
		List<Employee> li = new ArrayList<Employee>();
		temp.query("select id,name,designation from emp1", new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				li.add(new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("designation")));
			}
		});
		return li;
	}
	
	public Employee getIndividualItem(int itemId)
	{
		String query="select * from emp1 where id=?";
		Employee p=temp.queryForObject(query, new Object[]{itemId},new BeanPropertyRowMapper<>(Employee.class));
		return p;
	}
	
	public int addItem(String name, String designation) {
		String query="insert into emp1(name,designation)values(?,?)";
		return temp.update(query,name,designation);
	}
	
	public int deleteItem(int id)
	{
		String query="delete from emp1 where id=?";
		return temp.update(query,id);
	}

	public int updateItem(int id,String name,String designation){
		String query="update emp1 set name=?, designation=? where id=?";
		return temp.update(query,name,designation,id);
	}	
}
