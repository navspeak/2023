import { Injectable } from '@angular/core';
import { Student, studentsArray } from './student';

@Injectable({
  providedIn: "root"
})
class StudentService {

  constructor() { }
  getStudents(): Student[]{
    return studentsArray;
  }
}

export {
  StudentService,
  Student
}
