import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {UpdateMitarbeiterFormComponent} from './update-mitarbeiter-form.component';

describe('UpdateMitarbeiterFormComponent', () => {
  let component: UpdateMitarbeiterFormComponent;
  let fixture: ComponentFixture<UpdateMitarbeiterFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateMitarbeiterFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateMitarbeiterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
