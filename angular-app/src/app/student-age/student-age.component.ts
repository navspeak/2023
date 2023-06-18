import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-student-age',
  templateUrl: './student-age.component.html',
  styleUrls: ['./student-age.component.css']
})
export class StudentAgeComponent {
  @Input() studentAge: number;
  @Input() studentName: string;
  



}
