import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentAgeComponent } from './student-age.component';

describe('StudentAgeComponent', () => {
  let component: StudentAgeComponent;
  let fixture: ComponentFixture<StudentAgeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentAgeComponent]
    });
    fixture = TestBed.createComponent(StudentAgeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
