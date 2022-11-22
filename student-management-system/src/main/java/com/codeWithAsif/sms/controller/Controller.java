package com.codeWithAsif.sms.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.codeWithAsif.sms.entity.Student;
import com.codeWithAsif.sms.service.StudentService;

@org.springframework.stereotype.Controller
public class Controller {

	private StudentService studentService;

	public Controller(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	// handler method to handle list students and return model and view

	@GetMapping("/students")
	public String listStudents(Model model) {

		model.addAttribute("students", studentService.getAllStudents());

		return "students";
	}

	@GetMapping("/students/new")
	public String createStudentForm(Model model) {

		// create new student object to hold student form data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";
	}

	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		studentService.saveStudent(student);

		return "redirect:/students";
	}

	@GetMapping("/students/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {

		model.addAttribute("student", studentService.getStudentById(id));
		return "edit_student";

	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable("id") Long id, @ModelAttribute("student") Student student, Model model) {
			//get student from database
		Student existingStudent=studentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstName(student.getFirstName());
		existingStudent.setLastName(student.getLastName());
		existingStudent.setEmail(student.getEmail());
		
		//update the student
		studentService.updateStudent(existingStudent);
		
		return "redirect:/students";
	}
	
	//Handler for delete the student detail
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable("id") Long id) {
		studentService.deleteStudentById(id);
		return "redirect:/students";
	}

}
