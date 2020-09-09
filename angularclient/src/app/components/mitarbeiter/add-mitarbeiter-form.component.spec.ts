import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddMitarbeiterFormComponent} from './add-mitarbeiter-form.component';

describe('MitarbeiterFormComponent', () => {
  let component: AddMitarbeiterFormComponent;
  let fixture: ComponentFixture<AddMitarbeiterFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddMitarbeiterFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddMitarbeiterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
