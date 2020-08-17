import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {MitarbeiterFormComponent} from './mitarbeiter-form.component';

describe('MitarbeiterFormComponent', () => {
  let component: MitarbeiterFormComponent;
  let fixture: ComponentFixture<MitarbeiterFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MitarbeiterFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MitarbeiterFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
