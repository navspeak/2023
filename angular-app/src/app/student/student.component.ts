import { Component, OnInit } from '@angular/core';
// import { Student, studentsArray } from '../student';
import {  Student, StudentService } from '../student.service';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  // student1 : Student = {
  //   id: 1,
  //   age: 10,
  //   name: 'xxx'
  // }

  // students = studentsArray
  students : Student[];
  selectedStudentAge: number;
  selectedStudentName: string;
  onSelect(s:Student): void{
    this.selectedStudentAge = s.age
    this.selectedStudentName = s.name
  }
  constructor(private studentService: StudentService){}
  ngOnInit(): void {
    this.getStudents();
  }

  getStudents(): void {
    //let obj = new StudentService();
    // this.students = obj.getStudents();
    this.students = this.studentService.getStudents();
  }
}
