import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { StudentComponent } from './student/student.component';
import { StudentAgeComponent } from './student-age/student-age.component';
import { StudentService } from './student.service';

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    StudentAgeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  //providers: [StudentService],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
